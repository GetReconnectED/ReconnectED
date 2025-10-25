package com.getreconnected.reconnected.core.models

import android.graphics.drawable.ColorDrawable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

/**
 * Unit tests for AppUsageInfo data class
 */
class AppUsageInfoTest {

    @Test
    fun appUsageInfo_createsInstanceWithCorrectValues() {
        val appName = "TestApp"
        val packageName = "com.test.app"
        val usageTime = 5000L
        val appIcon = ColorDrawable()

        val appUsageInfo = AppUsageInfo(appName, packageName, usageTime, appIcon)

        assertEquals(appName, appUsageInfo.appName)
        assertEquals(packageName, appUsageInfo.packageName)
        assertEquals(usageTime, appUsageInfo.usageTime)
        assertEquals(appIcon, appUsageInfo.appIcon)
    }

    @Test
    fun appUsageInfo_withZeroUsageTime_createsValidInstance() {
        val appUsageInfo = AppUsageInfo(
            appName = "IdleApp",
            packageName = "com.idle.app",
            usageTime = 0L,
            appIcon = ColorDrawable()
        )

        assertEquals(0L, appUsageInfo.usageTime)
    }

    @Test
    fun appUsageInfo_withLargeUsageTime_createsValidInstance() {
        val largeUsageTime = Long.MAX_VALUE
        val appUsageInfo = AppUsageInfo(
            appName = "HeavyApp",
            packageName = "com.heavy.app",
            usageTime = largeUsageTime,
            appIcon = ColorDrawable()
        )

        assertEquals(largeUsageTime, appUsageInfo.usageTime)
    }

    @Test
    fun appUsageInfo_equalityWorks_forIdenticalInstances() {
        val icon = ColorDrawable()
        val info1 = AppUsageInfo("App", "com.app", 1000L, icon)
        val info2 = AppUsageInfo("App", "com.app", 1000L, icon)

        assertEquals(info1, info2)
    }

    @Test
    fun appUsageInfo_hashCodeWorks_forIdenticalInstances() {
        val icon = ColorDrawable()
        val info1 = AppUsageInfo("App", "com.app", 1000L, icon)
        val info2 = AppUsageInfo("App", "com.app", 1000L, icon)

        assertEquals(info1.hashCode(), info2.hashCode())
    }

    @Test
    fun appUsageInfo_notEqual_withDifferentAppName() {
        val icon = ColorDrawable()
        val info1 = AppUsageInfo("App1", "com.app", 1000L, icon)
        val info2 = AppUsageInfo("App2", "com.app", 1000L, icon)

        assertNotEquals(info1, info2)
    }

    @Test
    fun appUsageInfo_notEqual_withDifferentPackageName() {
        val icon = ColorDrawable()
        val info1 = AppUsageInfo("App", "com.app1", 1000L, icon)
        val info2 = AppUsageInfo("App", "com.app2", 1000L, icon)

        assertNotEquals(info1, info2)
    }

    @Test
    fun appUsageInfo_notEqual_withDifferentUsageTime() {
        val icon = ColorDrawable()
        val info1 = AppUsageInfo("App", "com.app", 1000L, icon)
        val info2 = AppUsageInfo("App", "com.app", 2000L, icon)

        assertNotEquals(info1, info2)
    }

    @Test
    fun appUsageInfo_copy_createsNewInstanceWithSameValues() {
        val original = AppUsageInfo(
            appName = "OriginalApp",
            packageName = "com.original.app",
            usageTime = 3000L,
            appIcon = ColorDrawable()
        )

        val copy = original.copy()

        assertEquals(original, copy)
        assertEquals(original.appName, copy.appName)
        assertEquals(original.packageName, copy.packageName)
        assertEquals(original.usageTime, copy.usageTime)
    }

    @Test
    fun appUsageInfo_copy_withModifiedUsageTime_createsNewInstance() {
        val original = AppUsageInfo(
            appName = "App",
            packageName = "com.app",
            usageTime = 1000L,
            appIcon = ColorDrawable()
        )

        val modified = original.copy(usageTime = 2000L)

        assertEquals("App", modified.appName)
        assertEquals("com.app", modified.packageName)
        assertEquals(2000L, modified.usageTime)
        assertNotEquals(original.usageTime, modified.usageTime)
    }
}
