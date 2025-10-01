package com.getreconnected.reconnected.ui.composables

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.ui.navigation.Menus
import com.getreconnected.reconnected.ui.navigation.ReconnectedViewModel
import com.getreconnected.reconnected.ui.theme.BackgroundColorPrimary
import com.getreconnected.reconnected.ui.theme.CardColorPrimary
import com.getreconnected.reconnected.ui.theme.ContainerColorPrimary
import com.getreconnected.reconnected.ui.theme.ContainerColorSelected
import com.getreconnected.reconnected.ui.theme.DrawerContainerColor
import com.getreconnected.reconnected.ui.theme.IconColorActive
import com.getreconnected.reconnected.ui.theme.IconColorPrimary
import com.getreconnected.reconnected.ui.theme.TextColorPrimary
import com.getreconnected.reconnected.ui.theme.TextColorSelected
import com.getreconnected.reconnected.ui.theme.sidebarButtonTextStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * The content of the navigation drawer.
 */
@Composable
fun NavDrawerContent(
    navController: NavController,
    viewModel: ReconnectedViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
) {

    val drawerItemShape = RectangleShape
    val drawerItemColors = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = ContainerColorSelected,
        selectedIconColor = TextColorSelected,
        selectedTextColor = TextColorSelected,
        unselectedContainerColor = ContainerColorPrimary,
        unselectedIconColor = TextColorPrimary,
        unselectedTextColor = TextColorPrimary
    )

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route

    /**
     * Navigate to the given route and close the drawer.
     */
    fun navigateAndClose(route: String) {
        navController.navigate(route) {
            popUpTo(Menus.Dashboard.route) { inclusive = false }
            launchSingleTop = true
        }
        viewModel.setSelected(route)
        scope.launch { drawerState.close() }
    }

    ModalDrawerSheet(
        drawerContainerColor = DrawerContainerColor, modifier = Modifier.fillMaxHeight()
    ) {
        Column {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.recologo_ca),
                    contentDescription = "Companion App Logo",
                    modifier = Modifier
                        .width(237.dp)
                        .height(232.dp),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
            }
            NavigationDrawerItem(
                icon = {
                Icon(
                    painter = painterResource(R.drawable.dashboard_icon),
                    contentDescription = "Dashboard",
                    tint = if (currentDestination == Menus.Dashboard.route) IconColorActive
                    else IconColorPrimary
                )
            },
                label = { Text("Dashboard", style = sidebarButtonTextStyle) },
                selected = currentDestination == Menus.Dashboard.route,
                onClick = { navigateAndClose(Menus.Dashboard.route) },
                colors = drawerItemColors,
                shape = drawerItemShape
            )
            NavigationDrawerItem(
                icon = {
                Icon(
                    painter = painterResource(R.drawable.screen_time_tracker_icon),
                    contentDescription = "Screen Time Tracker",
                    tint = if (currentDestination == Menus.ScreenTimeTracker.route) IconColorActive
                    else IconColorPrimary
                )
            },
                label = { Text("Screen Time Tracker", style = sidebarButtonTextStyle) },
                selected = currentDestination == Menus.ScreenTimeTracker.route,
                onClick = { navigateAndClose(Menus.ScreenTimeTracker.route) },
                colors = drawerItemColors,
                shape = drawerItemShape
            )
            NavigationDrawerItem(
                icon = {
                Icon(
                    painter = painterResource(R.drawable.screen_time_limit_icon),
                    contentDescription = "Screen Time Limit",
                    tint = if (currentDestination == Menus.ScreenTimeLimit.route) IconColorActive
                    else IconColorPrimary
                )
            },
                label = { Text("Screen Time Limit", style = sidebarButtonTextStyle) },
                selected = currentDestination == Menus.ScreenTimeLimit.route,
                onClick = { navigateAndClose(Menus.ScreenTimeLimit.route) },
                colors = drawerItemColors,
                shape = drawerItemShape
            )
            NavigationDrawerItem(
                icon = {
                Icon(
                    painter = painterResource(R.drawable.calendar_icon),
                    contentDescription = "Calendar",
                    tint = if (currentDestination == Menus.Calendar.route) IconColorActive
                    else IconColorPrimary
                )
            },
                label = { Text("Calendar", style = sidebarButtonTextStyle) },
                selected = currentDestination == Menus.Calendar.route,
                onClick = { navigateAndClose(Menus.Calendar.route) },
                colors = drawerItemColors,
                shape = drawerItemShape
            )
            NavigationDrawerItem(
                icon = {
                Icon(
                    painter = painterResource(R.drawable.ai_assistant_icon),
                    contentDescription = "AI Assistant",
                    tint = if (currentDestination == Menus.Assistant.route) IconColorActive
                    else IconColorPrimary
                )
            },
                label = { Text("AI Assistant", style = sidebarButtonTextStyle) },
                selected = currentDestination == Menus.Assistant.route,
                onClick = { navigateAndClose(Menus.Assistant.route) },
                colors = drawerItemColors,
                shape = drawerItemShape
            )
            // This spacer pushes the profile section to the bottom
            Spacer(modifier = Modifier.weight(1f))

            // --- PROFILE SECTION ---
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                ElevatedCard(
                    modifier = Modifier
                        .width(302.dp)
                        .height(84.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CardColorPrimary
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            tint = IconColorPrimary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = Build.USER,
                            style = sidebarButtonTextStyle.copy(fontSize = 28.sp),
                            color = TextColorPrimary,
                            modifier = Modifier.weight(1f) // Pushes button to the end
                        )

                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(color = BackgroundColorPrimary)
                                .clickable { /* TODO: Handle button click */ })
                    }
                }
                // Add a spacer for margin at the very bottom
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}