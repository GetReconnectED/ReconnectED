package com.getreconnected.reconnected.core.models

import android.graphics.drawable.Drawable

/**
 * Represents information about an application's usage statistics.
 *
 * @property appName The name of the application.
 * @property packageName The package name of the application.
 * @property usageTime The total usage time of the application in milliseconds.
 * @property appIcon The icon drawable of the application.
 */
data class AppUsageInfo(
    val appName: String,
    val packageName: String,
    val usageTime: Long,
    val appIcon: Drawable,
)
