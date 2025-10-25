package com.getreconnected.reconnected.core.models

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit tests for Screens enum and getScreenName function
 */
class ScreensTest {

    @Test
    fun screens_containsSplash() {
        val screen = Screens.entries.find { it.name == "Splash" }
        assertEquals(Screens.Splash, screen)
    }

    @Test
    fun screens_containsLogin() {
        val screen = Screens.entries.find { it.name == "Login" }
        assertEquals(Screens.Login, screen)
    }

    @Test
    fun screens_containsDashboard() {
        val screen = Screens.entries.find { it.name == "Dashboard" }
        assertEquals(Screens.Dashboard, screen)
    }

    @Test
    fun getScreenName_withSplash_returnsSplashScreen() {
        val result = getScreenName("Splash")
        assertEquals(Screens.Splash, result)
    }

    @Test
    fun getScreenName_withLogin_returnsLoginScreen() {
        val result = getScreenName("Login")
        assertEquals(Screens.Login, result)
    }

    @Test
    fun getScreenName_withDashboard_returnsDashboardScreen() {
        val result = getScreenName("Dashboard")
        assertEquals(Screens.Dashboard, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getScreenName_withInvalidScreen_throwsException() {
        getScreenName("InvalidScreen")
    }

    @Test(expected = IllegalArgumentException::class)
    fun getScreenName_withEmptyString_throwsException() {
        getScreenName("")
    }

    @Test
    fun screens_hasExactlyThreeEntries() {
        assertEquals(3, Screens.entries.size)
    }

    @Test
    fun screens_allEntriesHaveUniqueNames() {
        val names = Screens.entries.map { it.name }
        val uniqueNames = names.toSet()
        assertEquals(names.size, uniqueNames.size)
    }

    @Test
    fun screens_enumOrderIsCorrect() {
        val expectedOrder = listOf(Screens.Splash, Screens.Login, Screens.Dashboard)
        assertEquals(expectedOrder, Screens.entries)
    }
}
