package com.getreconnected.reconnected.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.getreconnected.reconnected.core.database.entities.InstalledApp
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for InstalledApp entities.
 * Provides methods to interact with the installed_apps table.
 */
@Dao
interface InstalledAppDao {
    /**
     * Get all installed apps as a Flow.
     */
    @Query("SELECT * FROM installed_apps WHERE isInstalled = 1 ORDER BY appName ASC")
    fun getAllInstalledApps(): Flow<List<InstalledApp>>

    /**
     * Get all user apps (non-system apps).
     */
    @Query("SELECT * FROM installed_apps WHERE isInstalled = 1 AND isSystemApp = 0 ORDER BY appName ASC")
    fun getUserApps(): Flow<List<InstalledApp>>

    /**
     * Get a specific app by package name.
     */
    @Query("SELECT * FROM installed_apps WHERE packageName = :packageName")
    suspend fun getAppByPackage(packageName: String): InstalledApp?

    /**
     * Insert or update an installed app.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApp(app: InstalledApp)

    /**
     * Insert multiple apps.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApps(apps: List<InstalledApp>)

    /**
     * Update an app.
     */
    @Update
    suspend fun updateApp(app: InstalledApp)

    /**
     * Mark an app as uninstalled.
     */
    @Query("UPDATE installed_apps SET isInstalled = 0, lastUpdated = :timestamp WHERE packageName = :packageName")
    suspend fun markAsUninstalled(
        packageName: String,
        timestamp: Long = System.currentTimeMillis(),
    )

    /**
     * Delete all apps marked as uninstalled.
     */
    @Query("DELETE FROM installed_apps WHERE isInstalled = 0")
    suspend fun deleteUninstalledApps()

    /**
     * Search apps by name.
     */
    @Query("SELECT * FROM installed_apps WHERE appName LIKE '%' || :query || '%' AND isInstalled = 1 ORDER BY appName ASC")
    fun searchApps(query: String): Flow<List<InstalledApp>>

    /**
     * Get count of installed apps.
     */
    @Query("SELECT COUNT(*) FROM installed_apps WHERE isInstalled = 1")
    suspend fun getInstalledAppCount(): Int

    /**
     * Delete all apps.
     */
    @Query("DELETE FROM installed_apps")
    suspend fun deleteAllApps()
}
