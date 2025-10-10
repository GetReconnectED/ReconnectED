package com.getreconnected.reconnected.core
import java.util.concurrent.TimeUnit

/**
 * Formats the given time in milliseconds to a human-readable string.
 *
 * @param millis The time in milliseconds to format.
 */
fun formatTime(millis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    return "${hours}h ${minutes}m"
}
