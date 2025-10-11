package com.getreconnected.reconnected.legacy.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeeklyScreenTimeDao {
    @Query("SELECT * FROM weekly_screen_time ORDER BY weekNumber ASC")
    fun getAll(): Flow<List<WeeklyScreenTime>>

    @Query("SELECT * FROM weekly_screen_time WHERE weekNumber = :weekNumber LIMIT 1")
    suspend fun getWeek(weekNumber: Int): WeeklyScreenTime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(weeklyScreenTime: WeeklyScreenTime)
}
