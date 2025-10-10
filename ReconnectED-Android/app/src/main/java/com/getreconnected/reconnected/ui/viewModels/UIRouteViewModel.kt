package com.getreconnected.reconnected.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.getreconnected.reconnected.ui.models.Menus

/**
 * The view model for UI routes.
 */
class UIRouteViewModel : ViewModel() {
    private val _selected = mutableStateOf(Menus.Dashboard)

    /**
     * Get the current selected route.
     */
    val selected: State<Menus> = _selected

    /**
     * Set the current selected route.
     *
     * @param route The new selected route.
     */
    fun setSelected(route: Menus) {
        _selected.value = route
    }
}
