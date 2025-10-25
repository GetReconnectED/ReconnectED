package com.getreconnected.reconnected.core

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for utility functions in Utils.kt
 */
class UtilsTest {

    @Test
    fun formatTime_withHoursMinutesSeconds_returnsCorrectFormat() {
        // Test: 2 hours, 15 minutes, 30 seconds (in milliseconds)
        val timeInMillis = (2 * 60 * 60 * 1000) + (15 * 60 * 1000) + (30 * 1000)
        val result = formatTime(timeInMillis.toLong())
        assertEquals("2h 15m 30s", result)
    }

    @Test
    fun formatTime_withOnlyMinutesAndSeconds_returnsCorrectFormat() {
        // Test: 45 minutes, 20 seconds (in milliseconds)
        val timeInMillis = (45 * 60 * 1000) + (20 * 1000)
        val result = formatTime(timeInMillis.toLong())
        assertEquals("45m 20s", result)
    }

    @Test
    fun formatTime_withOnlySeconds_returnsCorrectFormat() {
        // Test: 42 seconds (in milliseconds)
        val timeInMillis = 42 * 1000
        val result = formatTime(timeInMillis.toLong())
        assertEquals("42s", result)
    }

    @Test
    fun formatTime_withZeroTime_returnsZeroSeconds() {
        val result = formatTime(0L)
        assertEquals("0s", result)
    }

    @Test
    fun formatTime_withOneHour_returnsCorrectFormat() {
        // Test: Exactly 1 hour (in milliseconds)
        val timeInMillis = 60 * 60 * 1000
        val result = formatTime(timeInMillis.toLong())
        assertEquals("1h 0m 0s", result)
    }

    @Test
    fun formatTime_withOneMinute_returnsCorrectFormat() {
        // Test: Exactly 1 minute (in milliseconds)
        val timeInMillis = 60 * 1000
        val result = formatTime(timeInMillis.toLong())
        assertEquals("1m 0s", result)
    }

    @Test
    fun formatTime_withMaxValues_returnsCorrectFormat() {
        // Test: 23 hours, 59 minutes, 59 seconds (just under 24 hours)
        val timeInMillis = (23 * 60 * 60 * 1000) + (59 * 60 * 1000) + (59 * 1000)
        val result = formatTime(timeInMillis.toLong())
        assertEquals("23h 59m 59s", result)
    }

    @Test
    fun formatTime_withLessThanOneSecond_returnsZeroSeconds() {
        // Test: 500 milliseconds (less than 1 second)
        val result = formatTime(500L)
        assertEquals("0s", result)
    }
}
