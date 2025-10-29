package com.getreconnected.reconnected.core.util

import android.app.AppOpsManager
import android.content.Context
import android.os.Process

/**
 * Checks whether the app has permission to access usage stats.
 *
 * @param context The context of the calling component used to access system services and app information.
 * @return True if the app has been granted the usage stats permission, false otherwise.
 */
fun hasUsageStatsPermission(context: Context): Boolean {
    val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
    val mode =
        appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName,
        )
    return mode == AppOpsManager.MODE_ALLOWED
}
