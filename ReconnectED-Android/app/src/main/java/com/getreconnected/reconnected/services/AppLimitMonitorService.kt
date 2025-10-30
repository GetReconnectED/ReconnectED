package com.getreconnected.reconnected.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.activities.AppBlockedActivity
import com.getreconnected.reconnected.core.database.AppLimitDatabase
import com.getreconnected.reconnected.core.database.entities.AppLimit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Background service that monitors app usage and enforces time limits.
 * Runs as a foreground service to ensure it isn't killed by the system.
 */
class AppLimitMonitorService : Service() {
    private val serviceScope = CoroutineScope(Dispatchers.Default + Job())
    private lateinit var database: AppLimitDatabase
    private lateinit var usageStatsManager: UsageStatsManager
    private val blockedApps = mutableSetOf<String>()
    private val currentlyBlockingApps = mutableMapOf<String, Long>() // packageName -> block timestamp

    companion object {
        private const val TAG = "AppLimitMonitor"
        private const val NOTIFICATION_CHANNEL_ID = "app_limit_monitor"
        private const val BLOCK_NOTIFICATION_CHANNEL_ID = "app_limit_block"
        private const val NOTIFICATION_ID = 1001
        private const val BLOCK_NOTIFICATION_ID = 1002
        private const val CHECK_INTERVAL_MS = 2000L // Check every 2 seconds

        fun start(context: Context) {
            val intent = Intent(context, AppLimitMonitorService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, AppLimitMonitorService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        database = AppLimitDatabase.getDatabase(applicationContext)
        usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

        // Start monitoring
        startMonitoring()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.coroutineContext[Job]?.cancel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val monitorChannel =
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "App Limit Monitor",
                    NotificationManager.IMPORTANCE_LOW,
                ).apply {
                    description = "Monitors app usage and enforces time limits"
                }

            val blockChannel =
                NotificationChannel(
                    BLOCK_NOTIFICATION_CHANNEL_ID,
                    "App Limit Blocked",
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply {
                    description = "Alerts when app limits are reached"
                    enableLights(true)
                    enableVibration(true)
                    setShowBadge(true)
                    lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
                }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(monitorChannel)
            notificationManager.createNotificationChannel(blockChannel)
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat
            .Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("ReconnectED Active")
            .setContentText("Monitoring app usage limits")
            .setSmallIcon(R.drawable.screen_time_limit_icon)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    private fun startMonitoring() {
        serviceScope.launch {
            while (isActive) {
                try {
                    checkAppLimits()
                } catch (e: Exception) {
                    Log.e(TAG, "Error checking app limits", e)
                }
                delay(CHECK_INTERVAL_MS)
            }
        }
    }

    private suspend fun checkAppLimits() {
        // Clean up old blocking entries (older than 30 seconds)
        val now = System.currentTimeMillis()
        currentlyBlockingApps.entries.removeIf { now - it.value > 30000 }

        // Get all enabled limits
        val limits = database.appLimitDao().getEnabledLimits().first()
        if (limits.isEmpty()) return

        // Get current foreground app
        val foregroundApp = getForegroundApp()

        // If no foreground app detected (home screen, etc.)
        if (foregroundApp == null) {
            // Keep currently blocking apps in the map for a while
            // Remove entries older than 30 seconds
            val now = System.currentTimeMillis()
            val iterator = currentlyBlockingApps.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (now - entry.value > 30_000) { // 30 seconds
                    Log.d(TAG, "Removing ${entry.key} from blocking list (timeout)")
                    iterator.remove()
                }
            }
            return
        }

        // Check if we're currently blocking an app and it's been reopened
        if (currentlyBlockingApps.containsKey(foregroundApp)) {
            Log.d(TAG, "User tried to reopen blocked app: $foregroundApp")
            val limit = limits.find { it.packageName == foregroundApp }
            if (limit != null) {
                // Update timestamp to keep it in the blocking list
                currentlyBlockingApps[foregroundApp] = System.currentTimeMillis()
                // Re-block immediately
                blockAppImmediate(limit)
            }
            return
        }

        // Check if the foreground app has a limit and if it's exceeded
        val limit = limits.find { it.packageName == foregroundApp }
        if (limit != null) {
            val usageToday = getAppUsageToday(foregroundApp)
            val limitMinutes = limit.limitMillis / (1000 * 60)
            val usageMinutes = usageToday / (1000 * 60)

            Log.d(TAG, "Checking ${limit.appName}: Usage=${usageMinutes}min/${limitMinutes}min (${usageToday}ms/${limit.limitMillis}ms)")

            if (usageToday >= limit.limitMillis) {
                Log.w(TAG, "LIMIT EXCEEDED for ${limit.appName}!")
                blockApp(limit)
            }
        }
    }

    private fun getForegroundApp(): String? {
        val endTime = System.currentTimeMillis()
        val beginTime = endTime - 1000 * 60 // Look back 60 seconds to find current app

        try {
            // Use UsageEvents for more accurate foreground detection
            val usageEvents = usageStatsManager.queryEvents(beginTime, endTime)
            var currentApp: String? = null

            // Process events to find the most recent ACTIVITY_RESUMED without a corresponding ACTIVITY_PAUSED
            val eventsList = mutableListOf<Pair<String, Int>>()

            while (usageEvents.hasNextEvent()) {
                val event = android.app.usage.UsageEvents.Event()
                usageEvents.getNextEvent(event)

                when (event.eventType) {
                    android.app.usage.UsageEvents.Event.ACTIVITY_RESUMED -> {
                        if (event.packageName != packageName) {
                            eventsList.add(event.packageName to 1) // 1 = resumed
                        }
                    }
                    android.app.usage.UsageEvents.Event.ACTIVITY_PAUSED -> {
                        if (event.packageName != packageName) {
                            eventsList.add(event.packageName to 0) // 0 = paused
                        }
                    }
                }
            }

            // Find the last app that was resumed but not paused
            for (i in eventsList.size - 1 downTo 0) {
                val (pkg, type) = eventsList[i]
                if (type == 1) { // RESUMED
                    // Check if it was paused later
                    val isPausedLater = eventsList.subList(i + 1, eventsList.size).any {
                        it.first == pkg && it.second == 0
                    }
                    if (!isPausedLater) {
                        currentApp = pkg
                        break
                    }
                }
            }

            if (currentApp != null) {
                Log.v(TAG, "Foreground app: $currentApp")
            } else {
                Log.v(TAG, "No foreground app detected")
            }

            return currentApp
        } catch (e: Exception) {
            Log.e(TAG, "Error detecting foreground app", e)
            return null
        }
    }

    private fun getAppUsageToday(packageName: String): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startTime = calendar.timeInMillis
        val endTime = System.currentTimeMillis()

        // Use INTERVAL_BEST to get the most accurate data
        val usageStatsList =
            usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST,
                startTime,
                endTime,
            )

        if (usageStatsList.isNullOrEmpty()) {
            Log.w(TAG, "No usage stats available for $packageName")
            return 0L
        }

        // Sum up all usage for this package (in case there are multiple entries)
        val totalUsage = usageStatsList
            .filter { it.packageName == packageName }
            .sumOf { it.totalTimeInForeground }

        Log.d(TAG, "Usage for $packageName: ${totalUsage}ms (${totalUsage / 1000}s)")

        return totalUsage
    }

    private fun blockApp(limit: AppLimit) {
        // Prevent repeated blocking of the same app within cooldown
        if (blockedApps.contains(limit.packageName)) return
        blockedApps.add(limit.packageName)

        // Mark as currently blocking
        currentlyBlockingApps[limit.packageName] = System.currentTimeMillis()

        Log.d(TAG, "Blocking app: ${limit.appName} (${limit.packageName})")

        blockAppImmediate(limit)

        // Remove from blocked set after a delay
        serviceScope.launch {
            delay(5000) // 5 seconds cooldown before allowing re-blocking
            blockedApps.remove(limit.packageName)
        }
    }

    private fun blockAppImmediate(limit: AppLimit) {
        serviceScope.launch {
            // Show overlay window immediately - this works even from background
            try {
                AppBlockOverlayService.show(
                    this@AppLimitMonitorService,
                    limit.appName,
                    limit.limitMillis
                )
                Log.d(TAG, "Overlay displayed for ${limit.appName}")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to show overlay", e)
            }

            // Wait briefly to ensure overlay is visible
            delay(500)

            // Show a simple notification for info
            val notification =
                NotificationCompat
                    .Builder(this@AppLimitMonitorService, BLOCK_NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("⏰ ${limit.appName} Blocked")
                    .setContentText("You've reached your ${limit.limitMillis / 60000}min limit")
                    .setSmallIcon(R.drawable.screen_time_limit_icon)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setAutoCancel(true)
                    .build()

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.notify(BLOCK_NOTIFICATION_ID + limit.packageName.hashCode(), notification)

            Log.d(TAG, "${limit.appName} blocked with overlay")
        }
    }
}
