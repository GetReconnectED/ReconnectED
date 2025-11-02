package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.viewModels.AppUsageViewModel
import com.getreconnected.reconnected.ui.theme.interDisplayFamily
import kotlin.math.roundToInt

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
    var showPicker by remember { mutableStateOf(false) }
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
        var showPicker by remember { mutableStateOf(false) }

        IconButton(onClick = { showPicker = true }) {
            Icon(
                painter = painterResource(R.drawable.screen_time_limit_icon),
                contentDescription = "Set Limit",
                tint = Color.Gray,
            )
        }

        if (showPicker) {
            TimeLimitPickerDialog(
                onDismiss = { showPicker = false },
                onConfirm = { hours, minutes ->
                    println("Limit set for ${appInfo.name}: ${hours}h ${minutes}m")
                    // TODO: Save to ViewModel or database
                }
            )
        }

    }
}

@Composable
fun TimeLimitPickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (hours: Int, minutes: Int) -> Unit
) {
    var selectedHours by remember { mutableStateOf(0) }
    var selectedMinutes by remember { mutableStateOf(0) } // TODO: Minutes should start by zero

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text("Set Time Limit") },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LoopingNumberPicker(
                    range = 0..99,
                    value = selectedHours,
                    onValueChange = { selectedHours = it },
                    label = "hrs"
                )
                Spacer(modifier = Modifier.width(32.dp))
                LoopingNumberPicker(
                    range = 0..59,
                    value = selectedMinutes,
                    onValueChange = { selectedMinutes = it },
                    label = "min"
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(selectedHours, selectedMinutes)
                onDismiss()
            }) {
                Text(style = TextStyle(
                    fontFamily = interDisplayFamily), text = "Set Limit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(style = TextStyle(
                    fontFamily = interDisplayFamily), text = "Cancel")
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoopingNumberPicker(
    range: IntRange,
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String
) {
    val visibleCount = 5
    val totalCount = range.count()
    val extendedList = remember { List(1000) { range.first + (it % totalCount) } }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 500 + value)

    val snapLayoutInfoProvider = remember(listState) { SnapLayoutInfoProvider(listState) }
    val flingBehavior = rememberSnapFlingBehavior(snapLayoutInfoProvider)

    val itemHeight = 36.dp
    val density = LocalDensity.current

    // 👇 Explicit type & LocalDensity usage
    val centerItemIndex by remember {
        derivedStateOf<Int> {
            val firstItem = listState.firstVisibleItemIndex
            val offsetPx = listState.firstVisibleItemScrollOffset
            val itemHeightPx = with(density) { itemHeight.toPx() }
            val scrollOffset = offsetPx / itemHeightPx
            firstItem + scrollOffset.roundToInt() // +2 centers perfectly
        }
    }

    LaunchedEffect(centerItemIndex) {
        val newValue = extendedList.getOrNull(centerItemIndex)?.mod(range.last + 1) ?: 0
        if (newValue != value) onValueChange(newValue)
    }

    Box(
        modifier = Modifier
            .height(180.dp) // slightly taller to fit the label
            .width(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Scroll picker area
            Box(
                modifier = Modifier
                    .height(140.dp) // height for the scroll zone
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Highlight box
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(itemHeight)
                        .fillMaxWidth()
                        .background(Color(0x22000000), RoundedCornerShape(8.dp))
                )

                LazyColumn(
                    state = listState,
                    flingBehavior = flingBehavior,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 50.dp)
                ) {
                    items(extendedList.size) { index ->
                        val number = extendedList[index]
                        val isSelected = index == centerItemIndex
                        Text(
                            text = number.toString().padStart(2, '0'),
                            style = TextStyle(
                                fontSize = if (isSelected) 24.sp else 18.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.Black else Color.Gray,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .height(itemHeight)
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                        )
                    }
                }
            }

            // Label (now *below* the picker)
            Text(
                text = label,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}
