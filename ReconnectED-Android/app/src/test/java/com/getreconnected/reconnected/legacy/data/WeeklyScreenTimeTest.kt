package com.getreconnected.reconnected.legacy.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Unit tests for WeeklyScreenTime entity
 */
class WeeklyScreenTimeTest {

    @Test
    fun weeklyScreenTime_createsInstanceWithAllFields() {
        val id = 1
        val weekNumber = 42
        val totalTimeMillis = 360000L
        val topApps = listOf("App1", "App2", "App3")

        val weeklyScreenTime = WeeklyScreenTime(
            id = id,
            weekNumber = weekNumber,
            totalTimeMillis = totalTimeMillis,
            topApps = topApps
        )

        assertEquals(id, weeklyScreenTime.id)
        assertEquals(weekNumber, weeklyScreenTime.weekNumber)
        assertEquals(totalTimeMillis, weeklyScreenTime.totalTimeMillis)
        assertEquals(topApps, weeklyScreenTime.topApps)
    }

    @Test
    fun weeklyScreenTime_createsInstanceWithDefaultId() {
        val weekNumber = 10
        val totalTimeMillis = 500000L
        val topApps = listOf("App1")

        val weeklyScreenTime = WeeklyScreenTime(
            weekNumber = weekNumber,
            totalTimeMillis = totalTimeMillis,
            topApps = topApps
        )

        assertEquals(0, weeklyScreenTime.id)
        assertEquals(weekNumber, weeklyScreenTime.weekNumber)
        assertEquals(totalTimeMillis, weeklyScreenTime.totalTimeMillis)
    }

    @Test
    fun weeklyScreenTime_withEmptyTopAppsList_createsValidInstance() {
        val weeklyScreenTime = WeeklyScreenTime(
            weekNumber = 5,
            totalTimeMillis = 1000L,
            topApps = emptyList()
        )

        assertEquals(0, weeklyScreenTime.topApps.size)
    }

    @Test
    fun weeklyScreenTime_withZeroTime_createsValidInstance() {
        val weeklyScreenTime = WeeklyScreenTime(
            weekNumber = 1,
            totalTimeMillis = 0L,
            topApps = listOf()
        )

        assertEquals(0L, weeklyScreenTime.totalTimeMillis)
    }

    @Test
    fun weeklyScreenTime_withLargeWeekNumber_createsValidInstance() {
        val weekNumber = 52
        val weeklyScreenTime = WeeklyScreenTime(
            weekNumber = weekNumber,
            totalTimeMillis = 1000000L,
            topApps = listOf("App1", "App2")
        )

        assertEquals(weekNumber, weeklyScreenTime.weekNumber)
    }

    @Test
    fun weeklyScreenTime_withLargeTimeValue_createsValidInstance() {
        val largeTime = Long.MAX_VALUE
        val weeklyScreenTime = WeeklyScreenTime(
            weekNumber = 1,
            totalTimeMillis = largeTime,
            topApps = listOf("App1")
        )

        assertEquals(largeTime, weeklyScreenTime.totalTimeMillis)
    }

    @Test
    fun weeklyScreenTime_equalityWorks_forIdenticalInstances() {
        val topApps = listOf("App1", "App2")
        val time1 = WeeklyScreenTime(
            id = 1,
            weekNumber = 10,
            totalTimeMillis = 5000L,
            topApps = topApps
        )
        val time2 = WeeklyScreenTime(
            id = 1,
            weekNumber = 10,
            totalTimeMillis = 5000L,
            topApps = topApps
        )

        assertEquals(time1, time2)
    }

    @Test
    fun weeklyScreenTime_notEqual_withDifferentWeekNumber() {
        val topApps = listOf("App1")
        val time1 = WeeklyScreenTime(
            weekNumber = 10,
            totalTimeMillis = 5000L,
            topApps = topApps
        )
        val time2 = WeeklyScreenTime(
            weekNumber = 11,
            totalTimeMillis = 5000L,
            topApps = topApps
        )

        assertNotEquals(time1, time2)
    }

    @Test
    fun weeklyScreenTime_notEqual_withDifferentTotalTime() {
        val topApps = listOf("App1")
        val time1 = WeeklyScreenTime(
            weekNumber = 10,
            totalTimeMillis = 5000L,
            topApps = topApps
        )
        val time2 = WeeklyScreenTime(
            weekNumber = 10,
            totalTimeMillis = 6000L,
            topApps = topApps
        )

        assertNotEquals(time1, time2)
    }

    @Test
    fun weeklyScreenTime_copy_createsNewInstanceWithSameValues() {
        val original = WeeklyScreenTime(
            id = 1,
            weekNumber = 15,
            totalTimeMillis = 12000L,
            topApps = listOf("App1", "App2", "App3")
        )

        val copy = original.copy()

        assertEquals(original, copy)
        assertEquals(original.id, copy.id)
        assertEquals(original.weekNumber, copy.weekNumber)
        assertEquals(original.totalTimeMillis, copy.totalTimeMillis)
        assertEquals(original.topApps, copy.topApps)
    }

    @Test
    fun weeklyScreenTime_copy_withModifiedFields_createsNewInstance() {
        val original = WeeklyScreenTime(
            id = 1,
            weekNumber = 10,
            totalTimeMillis = 5000L,
            topApps = listOf("App1")
        )

        val modified = original.copy(
            weekNumber = 11,
            totalTimeMillis = 6000L
        )

        assertEquals(1, modified.id)
        assertEquals(11, modified.weekNumber)
        assertEquals(6000L, modified.totalTimeMillis)
        assertEquals(listOf("App1"), modified.topApps)
    }

    @Test
    fun weeklyScreenTime_hashCodeWorks_forIdenticalInstances() {
        val topApps = listOf("App1", "App2")
        val time1 = WeeklyScreenTime(
            id = 1,
            weekNumber = 20,
            totalTimeMillis = 8000L,
            topApps = topApps
        )
        val time2 = WeeklyScreenTime(
            id = 1,
            weekNumber = 20,
            totalTimeMillis = 8000L,
            topApps = topApps
        )

        assertEquals(time1.hashCode(), time2.hashCode())
    }

    @Test
    fun weeklyScreenTime_withMultipleTopApps_preservesListOrder() {
        val topApps = listOf("App3", "App1", "App2")
        val weeklyScreenTime = WeeklyScreenTime(
            weekNumber = 1,
            totalTimeMillis = 1000L,
            topApps = topApps
        )

        assertEquals(topApps, weeklyScreenTime.topApps)
        assertEquals("App3", weeklyScreenTime.topApps[0])
        assertEquals("App1", weeklyScreenTime.topApps[1])
        assertEquals("App2", weeklyScreenTime.topApps[2])
    }
}
