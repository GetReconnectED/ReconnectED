package com.getreconnected.reconnected.core.models

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for Menus enum and getMenuRoute function
 */
class MenusTest {

    @Test
    fun menus_hasCorrectTitle_forDashboard() {
        assertEquals("Dashboard", Menus.Dashboard.title)
    }

    @Test
    fun menus_hasCorrectTitle_forScreenTimeTracker() {
        assertEquals("Screen Time Tracker", Menus.ScreenTimeTracker.title)
    }

    @Test
    fun menus_hasCorrectTitle_forScreenTimeLimit() {
        assertEquals("Screen Time Limit", Menus.ScreenTimeLimit.title)
    }

    @Test
    fun menus_hasCorrectTitle_forCalendar() {
        assertEquals("Calendar", Menus.Calendar.title)
    }

    @Test
    fun menus_hasCorrectTitle_forAIAssistant() {
        assertEquals("AI Assistant", Menus.AIAssistant.title)
    }

    @Test
    fun getMenuRoute_withDashboard_returnsDashboardMenu() {
        val result = getMenuRoute("Dashboard")
        assertEquals(Menus.Dashboard, result)
    }

    @Test
    fun getMenuRoute_withScreenTimeTracker_returnsScreenTimeTrackerMenu() {
        val result = getMenuRoute("ScreenTimeTracker")
        assertEquals(Menus.ScreenTimeTracker, result)
    }

    @Test
    fun getMenuRoute_withScreenTimeLimit_returnsScreenTimeLimitMenu() {
        val result = getMenuRoute("ScreenTimeLimit")
        assertEquals(Menus.ScreenTimeLimit, result)
    }

    @Test
    fun getMenuRoute_withCalendar_returnsCalendarMenu() {
        val result = getMenuRoute("Calendar")
        assertEquals(Menus.Calendar, result)
    }

    @Test
    fun getMenuRoute_withAIAssistant_returnsAIAssistantMenu() {
        val result = getMenuRoute("AIAssistant")
        assertEquals(Menus.AIAssistant, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getMenuRoute_withInvalidRoute_throwsException() {
        getMenuRoute("InvalidMenu")
    }

    @Test(expected = IllegalArgumentException::class)
    fun getMenuRoute_withEmptyString_throwsException() {
        getMenuRoute("")
    }

    @Test
    fun menus_allEntriesHaveUniqueNames() {
        val names = Menus.entries.map { it.name }
        val uniqueNames = names.toSet()
        assertEquals(names.size, uniqueNames.size)
    }

    @Test
    fun menus_allEntriesHaveNonEmptyTitles() {
        Menus.entries.forEach { menu ->
            assert(menu.title.isNotEmpty()) { "${menu.name} has an empty title" }
        }
    }
}
