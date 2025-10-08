package com.getreconnected.reconnected.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.getreconnected.reconnected.ui.composables.NavDrawerContent
import com.getreconnected.reconnected.ui.composables.elements.TopBar
import com.getreconnected.reconnected.ui.composables.menus.Assistant
import com.getreconnected.reconnected.ui.composables.menus.Calendar
import com.getreconnected.reconnected.ui.composables.menus.Dashboard
import com.getreconnected.reconnected.ui.composables.menus.ScreenTimeLimit
import com.getreconnected.reconnected.ui.composables.menus.ScreenTimeTracker
import com.getreconnected.reconnected.ui.navigation.Menus
import com.getreconnected.reconnected.ui.navigation.ReconnectedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ReconnectedViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: Menus.Dashboard.route

    val title = when (currentRoute) {
        Menus.Dashboard.route -> "Dashboard"
        Menus.ScreenTimeTracker.route -> "Screen Time Tracker"
        Menus.ScreenTimeLimit.route -> "Screen Time Limit"
        Menus.Calendar.route -> "Calendar"
        Menus.Assistant.route -> "AI Assistant"
        else -> ""
    }


    ModalNavigationDrawer(
        modifier = Modifier.background(Color(0xFF008F46)),
        drawerState = drawerState,
        drawerContent = {
            NavDrawerContent(navController, viewModel, drawerState, scope, modifier)
        }) {
        Scaffold(
            topBar = {
                TopBar(
                    title = title, onOpenDrawer = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    })
            }) { padding ->
            NavHost(
                navController = navController, startDestination = Menus.Dashboard.route
            ) {
                composable(Menus.Dashboard.route) { Dashboard(Modifier.padding(padding)) }
                composable(Menus.ScreenTimeTracker.route) {
                    ScreenTimeTracker(
                        Modifier.padding(
                            padding
                        )
                    )
                }
                composable(Menus.ScreenTimeLimit.route) { ScreenTimeLimit(Modifier.padding(padding)) }
                composable(Menus.Calendar.route) { Calendar(Modifier.padding(padding)) }
                composable(Menus.Assistant.route) { Assistant(Modifier.padding(padding)) }
            }

        }
    }

}


