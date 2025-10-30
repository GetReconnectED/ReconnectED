package com.getreconnected.reconnected.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing an installed application on the device.
 * This table stores information about all apps that can potentially have limits set.
 *
 * @property packageName The unique package name of the application
 * @property appName The display name of the application
 * @property isSystemApp Whether this is a system application
 * @property isInstalled Whether the app is currently installed
 * @property lastUpdated Timestamp when this record was last updated
 */
@Entity(tableName = "installed_apps")
data class InstalledApp(
    @PrimaryKey
    val packageName: String,
    val appName: String,
    val isSystemApp: Boolean = false,
    val isInstalled: Boolean = true,
    val lastUpdated: Long = System.currentTimeMillis(),
)
