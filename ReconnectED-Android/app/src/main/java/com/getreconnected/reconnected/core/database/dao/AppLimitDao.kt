package com.getreconnected.reconnected.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.getreconnected.reconnected.core.database.entities.AppLimit
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for AppLimit entities.
 * Provides methods to interact with the app_limits table.
 */
@Dao
interface AppLimitDao {
    /**
     * Get all app limits as a Flow for reactive updates.
     */
    @Query("SELECT * FROM app_limits ORDER BY appName ASC")
    fun getAllLimits(): Flow<List<AppLimit>>

    /**
     * Get all enabled app limits.
     */
    @Query("SELECT * FROM app_limits WHERE isEnabled = 1 ORDER BY appName ASC")
    fun getEnabledLimits(): Flow<List<AppLimit>>

    /**
     * Get a specific app limit by package name.
     */
    @Query("SELECT * FROM app_limits WHERE packageName = :packageName")
    suspend fun getLimitByPackage(packageName: String): AppLimit?

    /**
     * Get a specific app limit by package name as Flow.
     */
    @Query("SELECT * FROM app_limits WHERE packageName = :packageName")
    fun getLimitByPackageFlow(packageName: String): Flow<AppLimit?>

    /**
     * Insert or update an app limit.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLimit(appLimit: AppLimit)

    /**
     * Insert multiple app limits.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLimits(appLimits: List<AppLimit>)

    /**
     * Update an existing app limit.
     */
    @Update
    suspend fun updateLimit(appLimit: AppLimit)

    /**
     * Delete an app limit.
     */
    @Delete
    suspend fun deleteLimit(appLimit: AppLimit)

    /**
     * Delete app limit by package name.
     */
    @Query("DELETE FROM app_limits WHERE packageName = :packageName")
    suspend fun deleteLimitByPackage(packageName: String)

    /**
     * Enable or disable a limit.
     */
    @Query("UPDATE app_limits SET isEnabled = :isEnabled, updatedAt = :timestamp WHERE packageName = :packageName")
    suspend fun setLimitEnabled(
        packageName: String,
        isEnabled: Boolean,
        timestamp: Long = System.currentTimeMillis(),
    )

    /**
     * Update the limit time for an app.
     */
    @Query("UPDATE app_limits SET limitMillis = :limitMillis, updatedAt = :timestamp WHERE packageName = :packageName")
    suspend fun updateLimitTime(
        packageName: String,
        limitMillis: Long,
        timestamp: Long = System.currentTimeMillis(),
    )

    /**
     * Check if a limit exists for a package.
     */
    @Query("SELECT EXISTS(SELECT 1 FROM app_limits WHERE packageName = :packageName)")
    suspend fun hasLimit(packageName: String): Boolean

    /**
     * Delete all app limits.
     */
    @Query("DELETE FROM app_limits")
    suspend fun deleteAllLimits()
}
