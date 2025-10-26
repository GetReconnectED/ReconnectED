package com.getreconnected.reconnected.core.util

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * Calculates the number of days the application has been active since its first installation.
 *
 * @param context The application context used to retrieve package information.
 * @return The number of days the application has been active (installation date inclusive).
 */
fun getDaysActive(context: Context): Long {
    val firstInstallTime = context.packageManager.getPackageInfo(context.packageName, 0).firstInstallTime
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - firstInstallTime
    return TimeUnit.MILLISECONDS.toDays(diff) + 1
}

/**
 * Retrieves the total screen time in milliseconds for the current day.
 *
 * @param context The context used to access system services like `UsageStatsManager`.
 * @return The total screen time in milliseconds for the current day. If the app does not
 *         have the required usage stats permission, it returns 0L.
 */
fun getScreenTimeInMillis(context: Context): Long {
    if (!hasUsageStatsPermission(context)) return 0L

    val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Start of today (midnight)
    val calendar =
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    val startTime = calendar.timeInMillis
    val endTime = System.currentTimeMillis()

    val usageEvents = usageStatsManager.queryEvents(startTime, endTime)
    var totalScreenTime = 0L
    var lastResumeTimestamp: Long? = null

    val event = UsageEvents.Event()
    while (usageEvents.hasNextEvent()) {
        usageEvents.getNextEvent(event)
        when (event.eventType) {
            UsageEvents.Event.ACTIVITY_RESUMED -> {
                lastResumeTimestamp = event.timeStamp
            }

            UsageEvents.Event.ACTIVITY_PAUSED -> {
                if (lastResumeTimestamp != null) {
                    totalScreenTime += (event.timeStamp - lastResumeTimestamp!!)
                    lastResumeTimestamp = null
                }
            }
        }
    }

    // Handle ongoing session (user still in foreground at query time)
    if (lastResumeTimestamp != null) {
        totalScreenTime += (endTime - lastResumeTimestamp!!)
    }

    return totalScreenTime
}
