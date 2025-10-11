package com.getreconnected.reconnected.core.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.getreconnected.reconnected.core.models.Menus

/**
 * The view model for UI routes.
 */
class UIRouteViewModel : ViewModel() {
    private val _selectedRoute = mutableStateOf(Menus.Dashboard)
    private val _activeUser = mutableStateOf("User")

    val selectedRoute: State<Menus> = _selectedRoute // Get the current selected route.
    val activeUser: State<String> = _activeUser // Get the current active user.

    /**
     * Set the current selected route.
     *
     * @param route The new selected route.
     */
    fun setSelectedRoute(route: Menus) {
        _selectedRoute.value = route
    }

    /**
     * Set the current active user.
     *
     * @param user The new active user.
     */
    fun setActiveUser(user: String) {
        _activeUser.value = user
    }
}
