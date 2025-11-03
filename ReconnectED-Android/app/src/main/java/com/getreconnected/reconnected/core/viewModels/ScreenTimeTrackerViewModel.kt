package com.getreconnected.reconnected.core.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.getreconnected.reconnected.core.AppUsageRepository
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

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

    private val _weeklyUsageStats = MutableStateFlow<Map<String, Long>>(emptyMap())
    val weeklyUsageStats: StateFlow<Map<String, Long>> = _weeklyUsageStats.asStateFlow()

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    fun loadUsageStats() {
        viewModelScope.launch {
            _appUsageStats.value = appUsageRepository.getDailyUsageStats()
        }
    }

    fun loadWeeklyUsageStats() {
        viewModelScope.launch {
            _weeklyUsageStats.value = appUsageRepository.getWeeklyUsageStats()
        }
    }

    /**
     * Loads usage statistics for a specific date.
     * @param date The date to load usage statistics for.
     */
    fun loadUsageStatsForDate(date: LocalDate) {
        viewModelScope.launch {
            _selectedDate.value = date
            val calendar =
                Calendar.getInstance().apply {
                    set(date.year, date.monthValue - 1, date.dayOfMonth)
                }
            _appUsageStats.value = appUsageRepository.getUsageStatsForDate(calendar)
        }
    }

    /**
     * Clears the selected date and loads today's usage statistics.
     */
    fun clearSelectedDate() {
        viewModelScope.launch {
            _selectedDate.value = null
            loadUsageStats()
        }
    }
}
