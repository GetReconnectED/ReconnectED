package com.getreconnected.reconnected.data

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.Calendar
import java.util.concurrent.TimeUnit

fun getDaysActive(context: Context): Long {
    return try {
        val firstInstallTime = context.packageManager
            .getPackageInfo(context.packageName, 0).firstInstallTime
        val currentTime = System.currentTimeMillis()
        val diff = currentTime - firstInstallTime
        TimeUnit.MILLISECONDS.toDays(diff) + 1
    } catch (e: Exception) {
        1L
    }
}

fun getScreenTimeInMillis(context: Context): Long {
    if (!hasUsageStatsPermission(context)) return 0L

    val usageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    // Start of today (midnight)
    val calendar = Calendar.getInstance().apply {
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


// Formats the millis into h/m
fun formatScreenTime(millis: Long): String {
    if (millis <= 0L) return "0m"

    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60

    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        minutes > 0 -> "${minutes}m"
        else -> "< 1m"
    }
}