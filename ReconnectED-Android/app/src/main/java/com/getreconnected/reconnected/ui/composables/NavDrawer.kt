package com.getreconnected.reconnected.ui.composables

import android.widget.Toast
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.getreconnected.reconnected.activities.MainActivity
import com.getreconnected.reconnected.core.dataManager.UserManager
import com.getreconnected.reconnected.core.models.Menus
import com.getreconnected.reconnected.core.models.getMenuRoute
import com.getreconnected.reconnected.core.viewModels.UIRouteViewModel
import com.getreconnected.reconnected.ui.theme.LocalReconnectEDColors
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
    val context = LocalContext.current
    val drawerItemShape = RectangleShape
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route
    val sidebarButtonText =
            TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
            )
    val selectedGreen = LocalReconnectEDColors.current.selectedGreen
    var showLogoutDialog by remember { mutableStateOf(false) }

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
            drawerContainerColor = MaterialTheme.colorScheme.primary, // Sidebar background green
    ) {
        Column {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Image(
                        painter = painterResource(id = R.drawable.recologo_ca),
                        contentDescription = "Companion App Logo",
                        modifier = Modifier.width(237.dp).height(232.dp),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                )
            }

            NavigationDrawerItem(
                    icon = {
                        Icon(
                                painter = painterResource(R.drawable.dashboard_icon),
                                contentDescription = "Dashboard",
                                tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    label = { Text("Dashboard", style = sidebarButtonText) },
                    selected = currentDestination == Menus.Dashboard.name,
                    onClick = { navigateAndClose(Menus.Dashboard.name) },
                    shape = drawerItemShape,
                    colors =
                            NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = selectedGreen,
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
            )

            NavigationDrawerItem(
                    icon = {
                        Icon(
                                painter = painterResource(R.drawable.screen_time_tracker_icon),
                                contentDescription = "Screen Time Tracker",
                                tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    label = { Text("Screen Time Tracker", style = sidebarButtonText) },
                    selected = currentDestination == Menus.ScreenTimeTracker.name,
                    onClick = { navigateAndClose(Menus.ScreenTimeTracker.name) },
                    shape = drawerItemShape,
                    colors =
                            NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = selectedGreen,
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
            )

            NavigationDrawerItem(
                    icon = {
                        Icon(
                                painter = painterResource(R.drawable.screen_time_limit_icon),
                                contentDescription = "Screen Time Limit",
                                tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    label = { Text("Screen Time Limit", style = sidebarButtonText) },
                    selected = currentDestination == Menus.ScreenTimeLimit.name,
                    onClick = { navigateAndClose(Menus.ScreenTimeLimit.name) },
                    shape = drawerItemShape,
                    colors =
                            NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = selectedGreen,
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
            )

            NavigationDrawerItem(
                    icon = {
                        Icon(
                                painter = painterResource(R.drawable.calendar_icon),
                                contentDescription = "Calendar",
                                tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    label = { Text("Calendar", style = sidebarButtonText) },
                    selected = currentDestination == Menus.Calendar.name,
                    onClick = { navigateAndClose(Menus.Calendar.name) },
                    shape = drawerItemShape,
                    colors =
                            NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = selectedGreen,
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
            )

            NavigationDrawerItem(
                    icon = {
                        Icon(
                                painter = painterResource(R.drawable.ai_assistant_icon),
                                contentDescription = "AI Assistant",
                                tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    label = { Text("AI Assistant", style = sidebarButtonText) },
                    selected = currentDestination == Menus.AIAssistant.name,
                    onClick = { navigateAndClose(Menus.AIAssistant.name) },
                    shape = drawerItemShape,
                    colors =
                            NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = selectedGreen,
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
            )

            NavigationDrawerItem(
                    icon = {
                        Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    label = { Text("Settings", style = sidebarButtonText) },
                    selected = currentDestination == Menus.Settings.name,
                    onClick = { navigateAndClose(Menus.Settings.name) },
                    shape = drawerItemShape,
                    colors =
                            NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = selectedGreen,
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
            )

            Spacer(modifier = Modifier.weight(1f))
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                ElevatedCard(
                        modifier = Modifier.width(302.dp).height(84.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors =
                                CardDefaults.cardColors(
                                        containerColor = selectedGreen
                                ), // Profile box
                ) {
                    Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                    ) {
                        UserManager.user?.avatar?.let { avatarBitmap ->
                            Image(
                                    painter = BitmapPainter(avatarBitmap.asImageBitmap()),
                                    contentDescription = "Profile",
                                    modifier = Modifier.size(48.dp).clip(CircleShape),
                                    contentScale = ContentScale.Crop,
                            )
                        }
                                ?: Icon(
                                        imageVector = Icons.Default.AccountCircle,
                                        contentDescription = "Profile",
                                        modifier = Modifier.size(48.dp),
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                                text = viewModel.activeUser.value,
                                style = sidebarButtonText.copy(fontSize = 20.sp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            NavigationDrawerItem(
                    icon = {
                        Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Logout",
                                tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    label = { Text("Logout", style = sidebarButtonText) },
                    selected = false,
                    onClick = { showLogoutDialog = true },
                    shape = drawerItemShape,
                    colors =
                            NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = selectedGreen,
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            ),
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Logout confirmation dialog
    if (showLogoutDialog) {
        AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Logout") },
                text = { Text("Are you sure you want to logout?") },
                confirmButton = {
                    Button(
                            onClick = {
                                showLogoutDialog = false
                                scope.launch { drawerState.close() }
                                Toast.makeText(
                                                context,
                                                "You are now logged out.",
                                                Toast.LENGTH_LONG,
                                        )
                                        .show()
                                UserManager.logout()
                                // TODO: Navigate to login screen
                                (context as MainActivity).finish()
                            }
                    ) {
                        Text(
                                style = TextStyle(fontFamily = interDisplayFamily),
                                text = "Logout",
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text(
                                style = TextStyle(fontFamily = interDisplayFamily),
                                text = "Cancel",
                        )
                    }
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun NavDrawerContentPreview() {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val viewModel: UIRouteViewModel = viewModel()
    val drawerState =
            rememberDrawerState(initialValue = androidx.compose.material3.DrawerValue.Closed)

    ReconnectEDTheme { NavDrawer(navController, viewModel, drawerState, scope) }
}
