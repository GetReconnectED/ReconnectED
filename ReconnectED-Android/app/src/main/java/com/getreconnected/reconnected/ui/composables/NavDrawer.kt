package com.getreconnected.reconnected.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.models.Menus
import com.getreconnected.reconnected.core.models.getMenuRoute
import com.getreconnected.reconnected.core.viewModels.UIRouteViewModel
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import com.getreconnected.reconnected.ui.theme.interDisplayFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The navigation drawer content for the app.
 *
 * @param navController The navigation controller for the app.
 * @param viewModel The view model for UI routes.
 * @param drawerState The drawer state for the nav drawer.
 * @param scope The coroutine scope for the app.
 * @param modifier The modifier for the app.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun NavDrawer(
    navController: NavController,
    viewModel: UIRouteViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
) {
    val drawerItemShape = RectangleShape
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route
    val sidebarButtonText =
        TextStyle(
            fontFamily = interDisplayFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )

    fun navigateAndClose(route: String) {
        navController.navigate(route) {
            popUpTo(Menus.Dashboard.name) { inclusive = false }
            launchSingleTop = true
        }
        viewModel.setSelectedRoute(getMenuRoute(route))
        scope.launch { drawerState.close() }
    }

    ModalDrawerSheet(
        modifier = Modifier.fillMaxHeight(),
    ) {
        Column {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.recologo_ca),
                    contentDescription = "Companion App Logo",
                    modifier =
                        Modifier
                            .width(237.dp)
                            .height(232.dp),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                )
            }

            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.dashboard_icon),
                        contentDescription = "Dashboard",
                    )
                },
                label = { Text("Dashboard", style = sidebarButtonText) },
                selected = currentDestination == Menus.Dashboard.name,
                onClick = { navigateAndClose(Menus.Dashboard.name) },
                shape = drawerItemShape,
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.screen_time_tracker_icon),
                        contentDescription = "Screen Time Tracker",
                    )
                },
                label = { Text("Screen Time Tracker", style = sidebarButtonText) },
                selected = currentDestination == Menus.ScreenTimeTracker.name,
                onClick = { navigateAndClose(Menus.ScreenTimeTracker.name) },
                shape = drawerItemShape,
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.screen_time_limit_icon),
                        contentDescription = "Screen Time Limit",
                    )
                },
                label = { Text("Screen Time Limit", style = sidebarButtonText) },
                selected = currentDestination == Menus.ScreenTimeLimit.name,
                onClick = { navigateAndClose(Menus.ScreenTimeLimit.name) },
                shape = drawerItemShape,
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.calendar_icon),
                        contentDescription = "Calendar",
                    )
                },
                label = { Text("Calendar", style = sidebarButtonText) },
                selected = currentDestination == Menus.Calendar.name,
                onClick = { navigateAndClose(Menus.Calendar.name) },
                shape = drawerItemShape,
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ai_assistant_icon),
                        contentDescription = "AI Assistant",
                    )
                },
                label = { Text("AI Assistant", style = sidebarButtonText) },
                selected = currentDestination == Menus.Assistant.name,
                onClick = { navigateAndClose(Menus.Assistant.name) },
                shape = drawerItemShape,
            )
            // This spacer pushes the profile section to the bottom
            Spacer(modifier = Modifier.weight(1f))

            // --- PROFILE SECTION ---
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                ElevatedCard(
                    modifier =
                        Modifier
                            .width(302.dp)
                            .height(84.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.size(48.dp),
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Juan",
                            style = sidebarButtonText.copy(fontSize = 28.sp),
                            modifier = Modifier.weight(1f), // Pushes button to the end
                        )

                        Box(
                            modifier =
                                Modifier
                                    .size(52.dp)
                                    .clickable { /* TODO: Handle button click */ },
                        )
                    }
                }
                // Add a spacer for margin at the very bottom
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun NavDrawerContentPreview() {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val viewModel: UIRouteViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ReconnectEDTheme {
        NavDrawer(navController, viewModel, drawerState, scope)
    }
}
