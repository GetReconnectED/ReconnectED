package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.viewModels.AppUsageViewModel
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

/**
 * A composable that displays a screen for setting and visualizing application usage limits.
 * The screen contains a gradient background and a container to showcase app usage details.
 *
 * @param modifier Modifier to be applied to the root layout, allowing customization of
 * size, padding, background, or other styling properties.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun ScreenTimeLimit(modifier: Modifier = Modifier) {
    // The Scaffold and TopAppBar have been removed.
    // This Column is now the main container for your screen's content.
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color(0xFFD1FAE5),
                                    Color(0xFFDBEAFE),
                                ),
                        ),
                ).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // "Limit your application usage" Text
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Limit your application usage",
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                ),
        )
        AppUsageContainer()
    }
}

/**
 * A composable function that represents a container displaying app usage information.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppUsageContainer() {
    val appList =
        listOf(
            AppUsageViewModel(
                name = "YouTube",
                usageTime = "2h 39m",
                icon = Icons.Filled.PlayArrow,
                iconBackgroundColor = Color(0xFFFF0000),
            ),
            AppUsageViewModel(
                name = "Facebook",
                usageTime = "2h 10m",
                icon = Icons.Filled.ThumbUp,
                iconBackgroundColor = Color(0xFF1877F2),
            ),
        )
    // The Card containing the list
    Column {
        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            // Allow card to take remaining space
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(appList) { app ->
                    AppUsageLimitItem(appInfo = app)
                }
            }
        }
    }
}

/**
 * Composable function that displays an item representing a specific application's usage information
 * with its icon, name, and usage time. It also includes an option to set usage limits.
 *
 * @param appInfo An object of type AppUsageInfo containing details about the application's name,
 * icon, usage time, and background color for the icon.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppUsageLimitItem(appInfo: AppUsageViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(appInfo.iconBackgroundColor),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = appInfo.icon,
                contentDescription = "${appInfo.name} logo",
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = appInfo.name,
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
            )
            Text(
                text = appInfo.usageTime,
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 16.sp,
                        color = Color.Gray,
                    ),
            )
        }
        IconButton(onClick = { /* Handle settings click */ }) {
            Icon(
                painter = painterResource(R.drawable.screen_time_limit_icon),
                contentDescription = "Set Limit",
                tint = Color.Gray,
            )
        }
    }
}
