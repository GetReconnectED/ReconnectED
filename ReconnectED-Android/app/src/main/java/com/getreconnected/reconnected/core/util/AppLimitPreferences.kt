package com.getreconnected.reconnected.core.util

import android.content.Context
import android.content.SharedPreferences

/**
 * Utility object for managing app limit monitoring preferences.
 */
object AppLimitPreferences {
    private const val PREFS_NAME = "app_limit_prefs"
    private const val KEY_MONITORING_ENABLED = "monitoring_enabled"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Check if app limit monitoring is enabled.
     */
    fun isMonitoringEnabled(context: Context): Boolean {
        return getPreferences(context).getBoolean(KEY_MONITORING_ENABLED, true)
    }

    /**
     * Set monitoring enabled state.
     */
    fun setMonitoringEnabled(
        context: Context,
        enabled: Boolean,
    ) {
        getPreferences(context).edit().putBoolean(KEY_MONITORING_ENABLED, enabled).apply()
    }
}
