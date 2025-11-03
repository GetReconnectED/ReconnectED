package com.getreconnected.reconnected.core.util

/**
 * Formats the given time in milliseconds to a human-readable string.
 *
 * @param t The time in milliseconds to format.
 * @return A string representing the formatted time in the format "<HH>h <MM>m <SS>s".
 */
fun formatTime(
    t: Long,
    strip: Boolean = false,
): String {
    val seconds = (t / 1000) % 60
    val minutes = (t / (1000 * 60)) % 60
    val hours = (t / (1000 * 60 * 60)) % 24
    val s = "${seconds}s"
    return when {
        hours > 0 ->
            if (strip) {
                "${hours}h ${minutes}m"
            } else {
                "${hours}h ${minutes}m $s"
            }
        minutes > 0 ->
            if (strip) {
                "${minutes}m"
            } else {
                "${minutes}m $s"
            }
        else -> s
    }
}
