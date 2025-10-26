package com.getreconnected.reconnected.core.util

/**
 * Formats the given time in milliseconds to a human-readable string.
 *
 * @param t The time in milliseconds to format.
 * @return A string representing the formatted time in the format "<HH>h <MM>m <SS>s".
 */
fun formatTime(t: Long): String {
    val seconds = (t / 1000) % 60
    val minutes = (t / (1000 * 60)) % 60
    val hours = (t / (1000 * 60 * 60)) % 24
    return when {
        hours > 0 -> "${hours}h ${minutes}m ${seconds}s"
        minutes > 0 -> "${minutes}m ${seconds}s"
        else -> "${seconds}s"
    }
}
