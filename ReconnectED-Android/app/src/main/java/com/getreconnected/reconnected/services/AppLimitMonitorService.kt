package com.getreconnected.reconnected.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.database.AppLimitDatabase
import com.getreconnected.reconnected.core.database.entities.AppLimit
import com.getreconnected.reconnected.core.repository.FirebaseUsageSyncRepository
import java.util.Calendar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Background service that monitors app usage and enforces time limits. Runs as a foreground service
 * to ensure it isn't killed by the system.
 */
class AppLimitMonitorService : Service() {
    private val serviceScope = CoroutineScope(Dispatchers.Default + Job())
    private lateinit var database: AppLimitDatabase
    private lateinit var usageStatsManager: UsageStatsManager
    private lateinit var firebaseSyncRepository: FirebaseUsageSyncRepository
    private val blockedApps = mutableSetOf<String>()
    private val currentlyBlockingApps =
            mutableMapOf<String, Long>() // packageName -> block timestamp
    private val lastOverlayShown =
            mutableMapOf<String, Long>() // packageName -> last time overlay was shown

    // Track active session usage
    private var currentForegroundApp: String? = null
    private var sessionStartTime: Long = 0L
    private val sessionUsageCache =
            mutableMapOf<String, Long>() // packageName -> session usage in ms

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
        usageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        firebaseSyncRepository = FirebaseUsageSyncRepository(database)

        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

        // Start monitoring
        startMonitoring()
    }

    override fun onStartCommand(
            intent: Intent?,
            flags: Int,
            startId: Int,
    ): Int = START_STICKY

    override fun onBind(intent: Intent?): IBinder? = null

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
                            )
                            .apply { description = "Monitors app usage and enforces time limits" }

            val blockChannel =
                    NotificationChannel(
                                    BLOCK_NOTIFICATION_CHANNEL_ID,
                                    "App Limit Blocked",
                                    NotificationManager.IMPORTANCE_HIGH,
                            )
                            .apply {
                                description = "Alerts when app limits are reached"
                                enableLights(true)
                                enableVibration(true)
                                setShowBadge(true)
                                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(monitorChannel)
            notificationManager.createNotificationChannel(blockChannel)
        }
    }

    private fun createNotification(): Notification =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle("ReconnectED Active")
                    .setContentText("Monitoring app usage limits")
                    .setSmallIcon(R.drawable.screen_time_limit_icon)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setOngoing(true)
                    .build()

    private fun startMonitoring() {
        serviceScope.launch {
            var lastMidnightCheck = System.currentTimeMillis()
            var lastSyncTime = System.currentTimeMillis()
            val syncIntervalMs = 5 * 60 * 1000L // Sync every 5 minutes

            while (isActive) {
                try {
                    // Check if we've passed midnight - reset session cache and sync previous day
                    val now = System.currentTimeMillis()
                    val todayMidnight =
                            Calendar.getInstance()
                                    .apply {
                                        set(Calendar.HOUR_OF_DAY, 0)
                                        set(Calendar.MINUTE, 0)
                                        set(Calendar.SECOND, 0)
                                        set(Calendar.MILLISECOND, 0)
                                    }
                                    .timeInMillis

                    if (lastMidnightCheck < todayMidnight && now >= todayMidnight) {
                        Log.i(TAG, "New day detected! Resetting session usage cache")
                        // Sync yesterday's data before resetting
                        syncCurrentUsageToFirebase()
                        sessionUsageCache.clear()
                        currentForegroundApp = null
                        sessionStartTime = 0L
                        lastMidnightCheck = now
                    }

                    // Periodic sync every 5 minutes
                    if (now - lastSyncTime >= syncIntervalMs) {
                        Log.d(TAG, "Periodic sync triggered")
                        syncCurrentUsageToFirebase()
                        lastSyncTime = now
                    }

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
            val limit = limits.find { it.packageName == foregroundApp }
            if (limit != null) {
                // Update timestamp to keep it in the blocking list
                currentlyBlockingApps[foregroundApp] = System.currentTimeMillis()

                // Only re-show overlay if it's been more than 3 seconds since we last showed it
                // This means user dismissed it (went home) and came back
                val lastShown = lastOverlayShown[foregroundApp] ?: 0L
                val timeSinceLastShown = System.currentTimeMillis() - lastShown
                if (timeSinceLastShown > 3000) { // 3 seconds threshold
                    Log.d(TAG, "User reopened blocked app: $foregroundApp")
                    blockAppImmediate(limit)
                    lastOverlayShown[foregroundApp] = System.currentTimeMillis()
                } else {
                    Log.v(TAG, "App still blocked: $foregroundApp, overlay already showing")
                }
            }
            return
        }

        // Track session time
        if (currentForegroundApp != foregroundApp) {
            // App changed - save previous session
            if (currentForegroundApp != null && sessionStartTime > 0) {
                val sessionDuration = System.currentTimeMillis() - sessionStartTime
                sessionUsageCache[currentForegroundApp!!] =
                        (sessionUsageCache[currentForegroundApp!!] ?: 0L) + sessionDuration
                Log.d(TAG, "Session ended for $currentForegroundApp: ${sessionDuration}ms added")
            }
            // Start new session
            currentForegroundApp = foregroundApp
            sessionStartTime = System.currentTimeMillis()
            Log.d(TAG, "Session started for $foregroundApp")
        }

        // Check if the foreground app has a limit and if it's exceeded
        val limit = limits.find { it.packageName == foregroundApp }
        if (limit != null) {
            // Get base usage from UsageStatsManager
            val baseUsage = getAppUsageToday(foregroundApp)

            // Add current session time
            val currentSessionTime =
                    if (sessionStartTime > 0) {
                        System.currentTimeMillis() - sessionStartTime
                    } else {
                        0L
                    }

            // Add cached session usage
            val cachedSessionUsage = sessionUsageCache[foregroundApp] ?: 0L

            // Total usage = base + current session + cached sessions
            val totalUsage = baseUsage + currentSessionTime + cachedSessionUsage

            val limitMinutes = limit.limitMillis / (1000 * 60)
            val usageMinutes = totalUsage / (1000 * 60)

            Log.d(
                    TAG,
                    "Checking ${limit.appName}: Usage=${usageMinutes}min/${limitMinutes}min " +
                            "(base=${baseUsage}ms + session=${currentSessionTime}ms + cached=${cachedSessionUsage}ms = ${totalUsage}ms/${limit.limitMillis}ms)",
            )

            if (totalUsage >= limit.limitMillis) {
                // Check if we're already blocking this app - don't re-block to prevent blipping
                if (currentlyBlockingApps.containsKey(foregroundApp)) {
                    Log.v(TAG, "Already blocking ${limit.appName}, skipping re-block")
                    return
                }

                Log.w(TAG, "LIMIT EXCEEDED for ${limit.appName}!")
                Log.w(TAG, "Total usage: ${totalUsage}ms >= Limit: ${limit.limitMillis}ms")
                Log.w(TAG, "Attempting to block app now...")
                blockApp(limit)
            } else {
                val remaining = limit.limitMillis - totalUsage
                Log.v(TAG, "Time remaining: ${remaining / 1000}s (${remaining / 60000}min)")
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

            // Process events to find the most recent ACTIVITY_RESUMED without a corresponding
            // ACTIVITY_PAUSED
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
                    val isPausedLater =
                            eventsList.subList(i + 1, eventsList.size).any {
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
        val totalUsage =
                usageStatsList.filter { it.packageName == packageName }.sumOf {
                    it.totalTimeInForeground
                }

        Log.d(TAG, "Usage for $packageName: ${totalUsage}ms (${totalUsage / 1000}s)")

        return totalUsage
    }

    private fun blockApp(limit: AppLimit) {
        // Prevent repeated blocking of the same app within cooldown
        if (blockedApps.contains(limit.packageName)) {
            Log.d(TAG, "Cooldown active for ${limit.appName}, skipping block")
            return
        }
        blockedApps.add(limit.packageName)

        // Mark as currently blocking
        currentlyBlockingApps[limit.packageName] = System.currentTimeMillis()
        lastOverlayShown[limit.packageName] = System.currentTimeMillis()

        Log.w(TAG, "BLOCKING APP: ${limit.appName} (${limit.packageName})")
        Log.w(TAG, "Overlay service will be shown now")

        blockAppImmediate(limit)

        // Remove from blocked set after a delay
        serviceScope.launch {
            delay(5000) // 5 seconds cooldown before allowing re-blocking
            blockedApps.remove(limit.packageName)
        }
    }

    private fun blockAppImmediate(limit: AppLimit) {
        serviceScope.launch {
            Log.w(TAG, "blockAppImmediate called for ${limit.appName}")

            // Show overlay window immediately - this works even from background
            try {
                Log.w(TAG, "Calling AppBlockOverlayService.show()...")
                AppBlockOverlayService.show(
                        this@AppLimitMonitorService,
                        limit.appName,
                        limit.limitMillis,
                )
                Log.w(TAG, "Overlay service called successfully for ${limit.appName}")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to show overlay: ${e.message}", e)
            }

            // Wait briefly to ensure overlay is visible
            delay(500)

            // Show a simple notification for info
            val notification =
                    NotificationCompat.Builder(
                                    this@AppLimitMonitorService,
                                    BLOCK_NOTIFICATION_CHANNEL_ID,
                            )
                            .setContentTitle("${limit.appName} Blocked")
                            .setContentText(
                                    "You've reached your ${limit.limitMillis / 60000}min limit",
                            )
                            .setSmallIcon(R.drawable.screen_time_limit_icon)
                            .setPriority(NotificationCompat.PRIORITY_LOW)
                            .setAutoCancel(true)
                            .build()

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.notify(
                    BLOCK_NOTIFICATION_ID + limit.packageName.hashCode(),
                    notification,
            )

            Log.d(TAG, "${limit.appName} blocked with overlay")
        }
    }

    private suspend fun syncCurrentUsageToFirebase() {
        try {
            // Get all apps with limits to sync their usage
            val limits = database.appLimitDao().getAllLimits().first()
            val usageData = mutableMapOf<String, Pair<String, Long>>()

            limits.forEach { limit ->
                val baseUsage = getAppUsageToday(limit.packageName)
                val cachedSession = sessionUsageCache[limit.packageName] ?: 0L
                val currentSession =
                        if (currentForegroundApp == limit.packageName && sessionStartTime > 0) {
                            System.currentTimeMillis() - sessionStartTime
                        } else {
                            0L
                        }

                val totalUsage = baseUsage + cachedSession + currentSession
                if (totalUsage > 0) {
                    usageData[limit.packageName] = Pair(limit.appName, totalUsage)
                }
            }

            if (usageData.isNotEmpty()) {
                firebaseSyncRepository.syncTodayUsage(usageData)
                Log.d(TAG, "Synced ${usageData.size} apps to Firebase")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing usage to Firebase", e)
        }
    }
}
