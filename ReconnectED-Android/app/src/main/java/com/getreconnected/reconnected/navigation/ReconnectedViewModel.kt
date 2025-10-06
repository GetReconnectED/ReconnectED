package com.getreconnected.reconnected.navigation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.getreconnected.reconnected.data.ScreenTimeRepository
import com.getreconnected.reconnected.data.WeeklyScreenTime
import com.getreconnected.reconnected.data.saveWeeklyUsage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReconnectedViewModel(
    private val repository: ScreenTimeRepository
) : ViewModel() {

    // ---- Your existing menu state ----
    private val _selected = mutableStateOf(Menus.Dashboard.route)
    val selected: State<String> = _selected

    fun setSelected(route: String) {
        _selected.value = route
    }

    // ---- Weekly Screen Time State ----
    val allWeeks: StateFlow<List<WeeklyScreenTime>> =
        repository.allWeeks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertWeek(week: WeeklyScreenTime) {
        viewModelScope.launch {
            repository.insert(week)
        }

    }

    fun refreshWeeklyUsage(context: Context, installTime: Long) {
        viewModelScope.launch {
            saveWeeklyUsage(
                context = context,
                dao = repository.dao,
                installTime = installTime
            )
        }
    }
}
