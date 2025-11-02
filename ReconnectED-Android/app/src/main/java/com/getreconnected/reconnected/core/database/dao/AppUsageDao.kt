package com.getreconnected.reconnected.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.getreconnected.reconnected.core.database.entities.AppUsage
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(appUsage: AppUsage)

    @Query("SELECT * FROM app_usage WHERE date = :date ORDER BY usageMillis DESC")
    fun getUsageForDate(date: String): Flow<List<AppUsage>>

    @Query("SELECT * FROM app_usage WHERE packageName = :packageName ORDER BY date DESC LIMIT 30")
    fun getUsageForApp(packageName: String): Flow<List<AppUsage>>

    @Query("SELECT * FROM app_usage WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getUsageForDateRange(startDate: String, endDate: String): Flow<List<AppUsage>>

    @Query("DELETE FROM app_usage WHERE date < :beforeDate")
    suspend fun deleteOldRecords(beforeDate: String)
}
