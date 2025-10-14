package com.getreconnected.reconnected.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.getreconnected.reconnected.core.models.Menus
import com.getreconnected.reconnected.core.models.getMenuRoute
import com.getreconnected.reconnected.core.viewModels.UIRouteViewModel
import com.getreconnected.reconnected.ui.composables.NavDrawer
import com.getreconnected.reconnected.ui.composables.elements.TopBar
import com.getreconnected.reconnected.ui.menus.Assistant
import com.getreconnected.reconnected.ui.menus.Calendar
import com.getreconnected.reconnected.ui.menus.Dashboard
import com.getreconnected.reconnected.ui.menus.ScreenTimeLimit
import com.getreconnected.reconnected.ui.menus.ScreenTimeTracker
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import kotlinx.coroutines.launch

/**
 * The main screen for the app.
 *
 * @param modifier The modifier to apply to the main screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("ktlint:standard:function-naming")
fun MainScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val viewModel: UIRouteViewModel = viewModel() // Initialize the view model for UI routes

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // Initialize the drawer state
    val backStackEntry by navController.currentBackStackEntryAsState() // Get the current back stack entry
    val currentRouteString = backStackEntry?.destination?.route ?: Menus.Dashboard.title

    // Get the screen title based on the current route
    val currentMenu = getMenuRoute(currentRouteString)

    ModalNavigationDrawer(
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        drawerState = drawerState,
        drawerContent = {
            NavDrawer(navController, viewModel, drawerState, scope, modifier)
        },
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = currentMenu.title,
                    onOpenDrawer = {
                        scope.launch {
                            drawerState.apply { if (isClosed) open() else close() }
                        }
                    },
                )
            },
        ) { padding ->
            NavHost(navController = navController, startDestination = Menus.Dashboard.name) {
                composable(Menus.Dashboard.name) { Dashboard(viewModel, Modifier.padding(padding)) }
                composable(Menus.ScreenTimeTracker.name) {
                    // ScreenTimeTracker(Modifier.padding(padding), viewModel)
                    ScreenTimeTracker(Modifier.padding(padding))
                }
                composable(Menus.ScreenTimeLimit.name) {
                    ScreenTimeLimit(Modifier.padding(padding))
                }
                composable(Menus.Calendar.name) { Calendar(Modifier.padding(padding)) }
                composable(Menus.Assistant.name) { Assistant(Modifier.padding(padding)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun MainScreenPreview() {
    ReconnectEDTheme {
        MainScreen()
    }
}
