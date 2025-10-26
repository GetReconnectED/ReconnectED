package com.getreconnected.reconnected.core

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Repository for fetching application usage statistics from the device.
 *
 * This class provides methods to retrieve usage data about applications
 * over a specific period of time. It interacts with the system's UsageStatsManager
 * to gather detailed information about app usage such as app name, package name,
 * usage duration, and app icon.
 *
 * @constructor Initializes the repository with the given application context.
 */
class AppUsageRepository(
    private val context: Context,
) {
    /**
     * Retrieves usage statistics for the last 24 hours.
     *
     * @return A list of [AppUsageInfo] objects representing the usage statistics.
     */
    suspend fun getDailyUsageStats(): List<AppUsageInfo> =
        withContext(Dispatchers.IO) {
            // NOTE: This is the actual service that provides the app usage information.
            val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val packageManager = context.packageManager

            val calendar = Calendar.getInstance()
            val endTime = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val startTime = calendar.timeInMillis

            val usageStatsList =
                usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY,
                    startTime,
                    endTime,
                )

            usageStatsList
                .filter { it.totalTimeInForeground > 0 }
                .mapNotNull { usageStats ->
                    try {
                        val applicationInfo = packageManager.getApplicationInfo(usageStats.packageName, 0)
                        val appName = packageManager.getApplicationLabel(applicationInfo).toString()
                        val appIcon = packageManager.getApplicationIcon(applicationInfo)
                        AppUsageInfo(
                            appName = appName,
                            packageName = usageStats.packageName,
                            usageTime = usageStats.totalTimeInForeground,
                            appIcon = appIcon,
                        )
                    } catch (e: PackageManager.NameNotFoundException) {
                        null
                    }
                }.sortedByDescending { it.usageTime }
        }

    /**
     * Retrieves the weekly application usage statistics. Uses the `UsageStatsManager` to
     * collect usage data over the last 7 days and returns a list of `UsageStats` objects
     * containing information about app usage.
     *
     * @param context The context used to access system services such as `UsageStatsManager`.
     * @return A list of `UsageStats`, where each object represents the usage statistics
     *         for an app during the past week. Returns an empty list if no data is available.
     */
    fun getWeeklyUsageStats(context: Context): List<UsageStats> {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val endTime = System.currentTimeMillis()
        val startTime = endTime - TimeUnit.DAYS.toMillis(7) // last 7 days

        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime,
        ) ?: emptyList()
    }
}
