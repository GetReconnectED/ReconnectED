package com.getreconnected.reconnected.core.repositories

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.getreconnected.reconnected.core.database.AppLimitDatabase
import com.getreconnected.reconnected.core.database.entities.AppLimit
import com.getreconnected.reconnected.core.database.entities.InstalledApp
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing app limits.
 * Handles business logic for setting, retrieving, and enforcing app usage limits.
 */
class AppLimitRepository(
    private val application: Application,
) {
    private val database = AppLimitDatabase.getDatabase(application)
    private val appLimitDao = database.appLimitDao()
    private val installedAppDao = database.installedAppDao()
    private val packageManager = application.packageManager

    // Flow for observing all limits
    val allLimits: Flow<List<AppLimit>> = appLimitDao.getAllLimits()

    // Flow for observing enabled limits
    val enabledLimits: Flow<List<AppLimit>> = appLimitDao.getEnabledLimits()

    // Flow for observing installed apps
    val installedApps: Flow<List<InstalledApp>> = installedAppDao.getAllInstalledApps()

    // Flow for observing user apps only
    val userApps: Flow<List<InstalledApp>> = installedAppDao.getUserApps()

    /**
     * Sync installed apps from the device to the database.
     * This should be called periodically to keep the database up to date.
     */
    suspend fun syncInstalledApps() {
        val intent =
            android.content.Intent(
                android.content.Intent.ACTION_MAIN,
                null,
            )
        intent.addCategory(android.content.Intent.CATEGORY_LAUNCHER)

        val packages = packageManager.queryIntentActivities(intent, 0)
        val apps =
            packages.mapNotNull { resolveInfo ->
                try {
                    val packageName = resolveInfo.activityInfo.packageName
                    // Skip our own app
                    if (packageName == application.packageName) return@mapNotNull null

                    val appInfo = packageManager.getApplicationInfo(packageName, 0)
                    val appName = packageManager.getApplicationLabel(appInfo).toString()
                    val isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

                    InstalledApp(
                        packageName = packageName,
                        appName = appName,
                        isSystemApp = isSystemApp,
                        isInstalled = true,
                        lastUpdated = System.currentTimeMillis(),
                    )
                } catch (e: PackageManager.NameNotFoundException) {
                    null
                }
            }

        installedAppDao.insertApps(apps)
    }

    /**
     * Set or update a limit for an app.
     *
     * @param packageName The package name of the app
     * @param appName The display name of the app
     * @param limitMillis The limit in milliseconds
     * @param isEnabled Whether the limit should be enabled
     */
    suspend fun setLimit(
        packageName: String,
        appName: String,
        limitMillis: Long,
        isEnabled: Boolean = true,
    ) {
        val limit =
            AppLimit(
                packageName = packageName,
                appName = appName,
                limitMillis = limitMillis,
                isEnabled = isEnabled,
                updatedAt = System.currentTimeMillis(),
            )
        appLimitDao.insertLimit(limit)
    }

    /**
     * Get a limit for a specific app.
     */
    suspend fun getLimit(packageName: String): AppLimit? {
        return appLimitDao.getLimitByPackage(packageName)
    }

    /**
     * Get a limit as Flow for reactive updates.
     */
    fun getLimitFlow(packageName: String): Flow<AppLimit?> {
        return appLimitDao.getLimitByPackageFlow(packageName)
    }

    /**
     * Update the limit time for an app.
     */
    suspend fun updateLimitTime(
        packageName: String,
        limitMillis: Long,
    ) {
        appLimitDao.updateLimitTime(packageName, limitMillis)
    }

    /**
     * Enable or disable a limit.
     */
    suspend fun setLimitEnabled(
        packageName: String,
        isEnabled: Boolean,
    ) {
        appLimitDao.setLimitEnabled(packageName, isEnabled)
    }

    /**
     * Delete a limit.
     */
    suspend fun deleteLimit(packageName: String) {
        appLimitDao.deleteLimitByPackage(packageName)
    }

    /**
     * Check if an app has a limit set.
     */
    suspend fun hasLimit(packageName: String): Boolean {
        return appLimitDao.hasLimit(packageName)
    }

    /**
     * Get an installed app by package name.
     */
    suspend fun getInstalledApp(packageName: String): InstalledApp? {
        return installedAppDao.getAppByPackage(packageName)
    }

    /**
     * Search installed apps by name.
     */
    fun searchApps(query: String): Flow<List<InstalledApp>> {
        return installedAppDao.searchApps(query)
    }
}
