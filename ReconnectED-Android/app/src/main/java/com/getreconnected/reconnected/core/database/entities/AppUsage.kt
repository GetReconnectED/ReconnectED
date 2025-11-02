package com.getreconnected.reconnected.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing daily app usage statistics.
 *
 * @property id Unique identifier (combination of date and packageName)
 * @property packageName The unique package name of the application
 * @property appName The display name of the application
 * @property date The date in format "yyyy-MM-dd"
 * @property usageMillis Total usage time in milliseconds for this day
 * @property timestamp When this record was created/updated
 */
@Entity(tableName = "app_usage")
data class AppUsage(
        @PrimaryKey val id: String, // format: "yyyy-MM-dd_packageName"
        val packageName: String,
        val appName: String,
        val date: String, // yyyy-MM-dd
        val usageMillis: Long,
        val timestamp: Long = System.currentTimeMillis(),
)
