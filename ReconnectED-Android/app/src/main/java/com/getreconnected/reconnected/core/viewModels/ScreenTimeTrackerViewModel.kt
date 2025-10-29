package com.getreconnected.reconnected.core.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.getreconnected.reconnected.core.AppUsageRepository
import com.getreconnected.reconnected.core.models.AppUsageInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel class for tracking and managing app screen time usage.
 *
 * This ViewModel interacts with the AppUsageRepository to fetch and provide
 * the app usage statistics in the form of a StateFlow. It is responsible
 * for managing the lifecycle-conscious asynchronous data updates to the
 * associated UI components.
 *
 * @constructor Creates an instance of ScreenTimeTrackerViewModel.
 * @param application The Application context required for the AppUsageRepository.
 */
class ScreenTimeTrackerViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val appUsageRepository = AppUsageRepository(application)

    private val _appUsageStats = MutableStateFlow<List<AppUsageInfo>>(emptyList())
    val appUsageStats: StateFlow<List<AppUsageInfo>> = _appUsageStats

    fun loadUsageStats() {
        viewModelScope.launch {
            _appUsageStats.value = appUsageRepository.getDailyUsageStats()
        }
    }
}
