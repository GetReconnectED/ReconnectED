package com.getreconnected.reconnected.ui.navigation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReconnectedViewModel : ViewModel() {
    private val _selected = mutableStateOf(Menus.Dashboard.route)
    val selected: State<String> = _selected

    fun setSelected(route: String) {
        _selected.value = route
    }
}