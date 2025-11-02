package com.getreconnected.reconnected.core.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.getreconnected.reconnected.core.AppUsageRepository
import com.getreconnected.reconnected.core.database.entities.AppLimit
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import com.getreconnected.reconnected.core.repositories.AppLimitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** Data class combining app usage info with limit information. */
data class AppUsageWithLimit(
        val usageInfo: AppUsageInfo,
        val limit: AppLimit?,
)

/**
 * ViewModel class for tracking and managing app screen time usage and limits.
 *
 * This ViewModel interacts with the AppUsageRepository and AppLimitRepository to fetch and provide
 * the app usage statistics and limits in the form of StateFlows. It is responsible for managing the
 * lifecycle-conscious asynchronous data updates to the associated UI components.
 *
 * @constructor Creates an instance of ScreenTimeLimitViewModel.
 * @param application The Application context required for the repositories.
 */
class ScreenTimeLimitViewModel(
        application: Application,
) : AndroidViewModel(application) {
    private val appUsageRepository = AppUsageRepository(application)
    private val appLimitRepository = AppLimitRepository(application)

    private val _appUsageStats = MutableStateFlow<List<AppUsageInfo>>(emptyList())
    val appUsageStats: StateFlow<List<AppUsageInfo>> = _appUsageStats

    // Observe all installed apps
    val installedApps = appLimitRepository.installedApps

    // Observe all app limits
    val appLimits: StateFlow<List<AppLimit>> =
            appLimitRepository.allLimits.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = emptyList(),
            )

    // Combine usage stats with limits
    val appUsageWithLimits: StateFlow<List<AppUsageWithLimit>> =
            combine(_appUsageStats, appLimits) { usageList, limitList ->
                        usageList.map { usage ->
                            val limit = limitList.find { it.packageName == usage.packageName }
                            AppUsageWithLimit(usage, limit)
                        }
                    }
                    .stateIn(
                            scope = viewModelScope,
                            started = SharingStarted.WhileSubscribed(5000),
                            initialValue = emptyList(),
                    )

    init {
        // Sync installed apps on initialization
        viewModelScope.launch { appLimitRepository.syncInstalledApps() }
    }

    fun loadUsageStats() {
        viewModelScope.launch { _appUsageStats.value = appUsageRepository.getDailyUsageStats() }
    }

    /**
     * Set a time limit for an app.
     *
     * @param packageName The package name of the app
     * @param appName The display name of the app
     * @param hours Number of hours
     * @param minutes Number of minutes
     */
    fun setLimit(
            packageName: String,
            appName: String,
            hours: Int,
            minutes: Int,
    ) {
        viewModelScope.launch {
            val limitMillis = (hours * 60 * 60 * 1000L) + (minutes * 60 * 1000L)
            appLimitRepository.setLimit(packageName, appName, limitMillis, isEnabled = true)
        }
    }

    /** Update an existing limit. */
    fun updateLimit(
            packageName: String,
            hours: Int,
            minutes: Int,
    ) {
        viewModelScope.launch {
            val limitMillis = (hours * 60 * 60 * 1000L) + (minutes * 60 * 1000L)
            appLimitRepository.updateLimitTime(packageName, limitMillis)
        }
    }

    /** Enable or disable a limit. */
    fun toggleLimit(
            packageName: String,
            isEnabled: Boolean,
    ) {
        viewModelScope.launch { appLimitRepository.setLimitEnabled(packageName, isEnabled) }
    }

    /** Delete a limit. */
    fun deleteLimit(packageName: String) {
        viewModelScope.launch { appLimitRepository.deleteLimit(packageName) }
    }

    /** Get a specific limit. */
    suspend fun getLimit(packageName: String): AppLimit? {
        return appLimitRepository.getLimit(packageName)
    }
}
