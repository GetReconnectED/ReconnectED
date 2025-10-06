package com.getreconnected.reconnected.data

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.graphics.drawable.Drawable
import java.util.concurrent.TimeUnit

fun getWeeklyUsageStats(context: Context): List<UsageStats> {
    val usageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    val endTime = System.currentTimeMillis()
    val startTime = endTime - TimeUnit.DAYS.toMillis(7) // last 7 days

    return usageStatsManager.queryUsageStats(
        UsageStatsManager.INTERVAL_DAILY,
        startTime,
        endTime
    ) ?: emptyList()
}

fun getAppInfo(context: Context, packageName: String): Pair<String, Drawable>? {
    return try {
        val pm = context.packageManager
        val appInfo = pm.getApplicationInfo(packageName, 0)
        val name = pm.getApplicationLabel(appInfo).toString()
        val icon = pm.getApplicationIcon(packageName)
        name to icon
    } catch (e: Exception) {
        null
    }
}

fun getWeekNumber(installTime: Long): Int {
    val diff = System.currentTimeMillis() - installTime
    return (diff / TimeUnit.DAYS.toMillis(7)).toInt() + 1
}

suspend fun saveWeeklyUsage(context: Context, dao: WeeklyScreenTimeDao, installTime: Long) {
    val stats = getWeeklyUsageStats(context)
    val totalTime = stats.sumOf { it.totalTimeInForeground }

    val topApps = stats.sortedByDescending { it.totalTimeInForeground }
        .take(5)
        .map { it.packageName }

    val weekNumber = getWeekNumber(installTime)

    dao.insertOrUpdate(
        WeeklyScreenTime(
            weekNumber = weekNumber,
            totalTimeMillis = totalTime,
            topApps = topApps
        )
    )
}


