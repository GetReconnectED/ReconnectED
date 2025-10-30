package com.getreconnected.reconnected.core

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Repository for fetching application usage statistics from the device.
 *
 * This class provides methods to retrieve usage data about applications
 * over a specific period of time. It interacts with the system's UsageStatsManager
 * to gather detailed information about app usage such as app name, package name,
 * usage duration, and app icon.
 *
 * @constructor Initializes the repository with the given application context.
 */
class AppUsageRepository(
    private val context: Context,
) {
    /**
     * Retrieves usage statistics for the last 24 hours.
     *
     * @return A list of [AppUsageInfo] objects representing the usage statistics.
     */
    suspend fun getDailyUsageStats(): List<AppUsageInfo> =
        withContext(Dispatchers.IO) {
            // NOTE: This is the actual service that provides the app usage information.
            val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val packageManager = context.packageManager

            val calendar = Calendar.getInstance()
            val endTime = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val startTime = calendar.timeInMillis

            val usageStatsList =
                usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY,
                    startTime,
                    endTime,
                )

            usageStatsList
                .filter { it.totalTimeInForeground > 0 }
                .mapNotNull { usageStats ->
                    try {
                        val applicationInfo = packageManager.getApplicationInfo(usageStats.packageName, 0)
                        val appName = packageManager.getApplicationLabel(applicationInfo).toString()
                        val appIcon = packageManager.getApplicationIcon(applicationInfo)
                        AppUsageInfo(
                            appName = appName,
                            packageName = usageStats.packageName,
                            usageTime = usageStats.totalTimeInForeground,
                            appIcon = appIcon,
                        )
                    } catch (e: PackageManager.NameNotFoundException) {
                        null
                    }
                }.sortedByDescending { it.usageTime }
        }

    /**
     * Retrieves the weekly application usage statistics. Uses the `UsageStatsManager` to
     * collect usage data over the last 7 days and returns a map of day to usage time in minutes.
     *
     * @return A map where the key is the day of the week (e.g., "Mon") and the value is the
     *         total screen time in minutes.
     */
    suspend fun getWeeklyUsageStats(): Map<String, Long> =
        withContext(Dispatchers.IO) {
            val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val weeklyUsage = linkedMapOf<String, Long>()
            val cal = Calendar.getInstance()
            val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())

            for (i in 6 downTo 0) {
                cal.time = java.util.Date()
                cal.add(Calendar.DAY_OF_YEAR, -i)
                val dayKey = dayFormat.format(cal.time)

                cal.set(Calendar.HOUR_OF_DAY, 0)
                cal.set(Calendar.MINUTE, 0)
                cal.set(Calendar.SECOND, 0)
                cal.set(Calendar.MILLISECOND, 0)
                val startTime = cal.timeInMillis

                cal.add(Calendar.DAY_OF_YEAR, 1)
                cal.add(Calendar.MILLISECOND, -1)
                val endTime = cal.timeInMillis

                val usageStats =
                    usageStatsManager.queryUsageStats(
                        UsageStatsManager.INTERVAL_DAILY,
                        startTime,
                        endTime,
                    )

                val totalTime = usageStats.sumOf { it.totalTimeInForeground }

                Log.d("WeeklyUsageStats", "Start Time: ${java.util.Date(startTime)}")
                Log.d("WeeklyUsageStats", "End Time: ${java.util.Date(endTime)}")
                Log.d("WeeklyUsageStats", "Day: $dayKey")
                Log.d("WeeklyUsageStats", "Total Usage: $totalTime")

                weeklyUsage[dayKey] = totalTime / (1000 * 60) // Convert to minutes
            }
            weeklyUsage
        }
}
