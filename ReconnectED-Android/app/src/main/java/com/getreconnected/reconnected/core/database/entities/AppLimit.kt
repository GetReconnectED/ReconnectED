package com.getreconnected.reconnected.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing a time limit set by the user for a specific application.
 *
 * @property packageName The unique package name of the application (e.g., "com.example.app")
 * @property appName The display name of the application
 * @property limitMillis The time limit in milliseconds
 * @property isEnabled Whether the limit is currently active
 * @property createdAt Timestamp when the limit was created
 * @property updatedAt Timestamp when the limit was last updated
 */
@Entity(tableName = "app_limits")
data class AppLimit(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val limitMillis: Long,
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)
