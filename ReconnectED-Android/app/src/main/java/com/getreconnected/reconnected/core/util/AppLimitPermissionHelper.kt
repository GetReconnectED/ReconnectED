package com.getreconnected.reconnected.core.util

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

/**
 * Utility functions for managing app limit permissions.
 */
object AppLimitPermissionHelper {
    /**
     * Check if the app can draw overlays (SYSTEM_ALERT_WINDOW permission).
     * This is required to show blocking overlay over apps.
     */
    fun canDrawOverlays(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else {
            true // Older Android versions don't need explicit permission
        }
    }

    /**
     * Open settings to allow drawing overlays.
     */
    fun openOverlaySettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent =
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${context.packageName}"),
                )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    /**
     * Check if the app can show full-screen intents (needed for blocking popup).
     * This is required on Android 14+ for automatic popup display.
     */
    fun canShowFullScreenIntent(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.canUseFullScreenIntent()
        } else {
            true // Older Android versions don't need this permission
        }
    }

    /**
     * Open settings to allow full-screen intents.
     */
    fun openFullScreenIntentSettings(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val intent =
                Intent(
                    Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT,
                    Uri.parse("package:${context.packageName}"),
                )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    /**
     * Check if all required permissions for app limiting are granted.
     */
    fun hasAllRequiredPermissions(context: Context): Boolean {
        val hasUsageAccess = hasUsageStatsPermission(context)
        val canDrawOverlay = canDrawOverlays(context)
        return hasUsageAccess && canDrawOverlay
    }

    private fun hasUsageStatsPermission(context: Context): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
        val mode =
            appOps.unsafeCheckOpNoThrow(
                android.app.AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName,
            )
        return mode == android.app.AppOpsManager.MODE_ALLOWED
    }
}
