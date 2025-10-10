package com.getreconnected.reconnected.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_screen_time")
data class WeeklyScreenTime(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val weekNumber: Int,
    val totalTimeMillis: Long,
    val topApps: List<String>,
)
