package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import com.getreconnected.reconnected.core.util.formatTime
import com.getreconnected.reconnected.core.viewModels.ScreenTimeLimitViewModel
import com.getreconnected.reconnected.ui.theme.interDisplayFamily
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlin.math.roundToInt

/**
 * A composable that displays a screen for setting and visualizing application usage limits. The
 * screen contains a gradient background and a container to showcase app usage details.
 *
 * @param modifier Modifier to be applied to the root layout, allowing customization of size,
 * padding, background, or other styling properties.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun ScreenTimeLimit(
        viewModel: ScreenTimeLimitViewModel,
        modifier: Modifier = Modifier,
) {
    val appUsageStats by viewModel.appUsageStats.collectAsState()
    val appLimits by viewModel.appLimits.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    LaunchedEffect(Unit) { viewModel.loadUsageStats() }

    Column(
            modifier =
                    modifier.fillMaxSize()
                            .background(
                                    brush =
                                            Brush.verticalGradient(
                                                    colors =
                                                            listOf(
                                                                    Color(0xFFD1FAE5),
                                                                    Color(0xFFDBEAFE),
                                                            ),
                                            ),
                            )
                            .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
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

        // Show info card with active limits count
        if (appLimits.isNotEmpty()) {
            Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors =
                            CardDefaults.cardColors(
                                    containerColor = Color(0xFF10B981).copy(alpha = 0.1f)
                            ),
            ) {
                Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                                text =
                                        "${appLimits.size} app${if (appLimits.size != 1) "s" else ""} with limits",
                                style =
                                        TextStyle(
                                                fontFamily = interDisplayFamily,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF10B981),
                                        ),
                        )
                        Text(
                                text = "Monitoring active",
                                style =
                                        TextStyle(
                                                fontFamily = interDisplayFamily,
                                                fontSize = 14.sp,
                                                color = Color.Gray,
                                        ),
                        )
                    }
                    Icon(
                            painter =
                                    androidx.compose.ui.res.painterResource(
                                            R.drawable.screen_time_limit_green
                                    ),
                            contentDescription = "Active",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(32.dp),
                    )
                }
            }
        }

        AppUsageContainer(appList = appUsageStats)
    }
}

/**
 * A composable function that represents a container displaying app usage information.
 * @param appList List of AppUsageInfo objects to display.
 * @param selectedDate The currently selected date, if any, to display in the header.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppUsageContainer(
        appList: List<AppUsageInfo>,
        selectedDate: java.time.LocalDate? = null,
) {
    Column {
        // Display selected date header if a date is selected
        if (selectedDate != null) {
            val dateFormatter = java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy")
            Text(
                    text = "Usage for ${selectedDate.format(dateFormatter)}",
                    style =
                            androidx.compose.ui.text.TextStyle(
                                    fontFamily =
                                            com.getreconnected
                                                    .reconnected
                                                    .ui
                                                    .theme
                                                    .interDisplayFamily,
                                    fontSize = 16.sp,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                                    color = androidx.compose.ui.graphics.Color(0xFF020202),
                            ),
                    modifier = Modifier.padding(bottom = 8.dp),
            )
        }

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
                    AppUsageLimitItem(
                            appInfo = app,
                            viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
                    )
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
fun AppUsageLimitItem(
        appInfo: AppUsageInfo,
        viewModel: ScreenTimeLimitViewModel,
) {
    var showPicker by remember { mutableStateOf(false) }
    val appLimits by viewModel.appLimits.collectAsState()
    val limit = appLimits.find { it.packageName == appInfo.packageName }

    // Calculate if limit is exceeded
    val isLimitExceeded = limit?.let { appInfo.usageTime >= it.limitMillis } ?: false
    val limitProgress =
            limit?.let {
                if (it.limitMillis > 0) {
                    (appInfo.usageTime.toFloat() / it.limitMillis.toFloat()).coerceIn(0f, 1f)
                } else {
                    0f
                }
            }
                    ?: 0f

    Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
        ) {
            Image(
                    painter = rememberDrawablePainter(drawable = appInfo.appIcon),
                    contentDescription = "${appInfo.appName} icon",
                    modifier = Modifier.size(40.dp),
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                    text = appInfo.appName,
                    style =
                            TextStyle(
                                    fontFamily = interDisplayFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isLimitExceeded) Color(0xFFEF4444) else Color.Black,
                            ),
            )
            Text(
                    text = formatTime(appInfo.usageTime),
                    style =
                            TextStyle(
                                    fontFamily = interDisplayFamily,
                                    fontSize = 16.sp,
                                    color = if (isLimitExceeded) Color(0xFFEF4444) else Color.Gray,
                            ),
            )
            // Show limit if set
            if (limit != null) {
                Text(
                        text =
                                "Limit: ${formatTime(limit.limitMillis)} ${if (limit.isEnabled) "✓" else "(disabled)"}",
                        style =
                                TextStyle(
                                        fontFamily = interDisplayFamily,
                                        fontSize = 14.sp,
                                        color =
                                                if (isLimitExceeded) Color(0xFFEF4444)
                                                else Color(0xFF10B981),
                                        fontWeight = FontWeight.Medium,
                                ),
                )
            }
        }

        IconButton(onClick = { showPicker = true }) {
            Icon(
                    painter = painterResource(R.drawable.screen_time_limit_icon),
                    contentDescription = "Set Limit",
                    tint =
                            when {
                                isLimitExceeded -> Color(0xFFEF4444)
                                limit != null -> Color(0xFF10B981)
                                else -> Color.Gray
                            },
            )
        }

        if (showPicker) {
            TimeLimitPickerDialog(
                    currentLimit = limit,
                    onDismiss = { showPicker = false },
                    onConfirm = { hours, minutes ->
                        if (limit != null) {
                            viewModel.updateLimit(appInfo.packageName, hours, minutes)
                        } else {
                            viewModel.setLimit(appInfo.packageName, appInfo.appName, hours, minutes)
                        }
                    },
                    onDelete =
                            if (limit != null) {
                                { viewModel.deleteLimit(appInfo.packageName) }
                            } else {
                                null
                            },
            )
        }
    }
}

@Composable
fun TimeLimitPickerDialog(
        currentLimit: com.getreconnected.reconnected.core.database.entities.AppLimit? = null,
        onDismiss: () -> Unit,
        onConfirm: (hours: Int, minutes: Int) -> Unit,
        onDelete: (() -> Unit)? = null,
) {
    // Initialize with current limit values if editing
    val initialHours = currentLimit?.let { (it.limitMillis / (1000 * 60 * 60)).toInt() } ?: 0
    val initialMinutes = currentLimit?.let { ((it.limitMillis / (1000 * 60)) % 60).toInt() } ?: 0

    var selectedHours by remember { mutableIntStateOf(initialHours) }
    var selectedMinutes by remember { mutableIntStateOf(initialMinutes) }

    AlertDialog(
            onDismissRequest = onDismiss,
            containerColor = MaterialTheme.colorScheme.surface,
            title = { Text(if (currentLimit != null) "Edit Time Limit" else "Set Time Limit") },
            text = {
                Column {
                    Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                    ) {
                        LoopingNumberPicker(
                                range = 0..99,
                                value = selectedHours,
                                onValueChange = { selectedHours = it },
                                label = "hrs",
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        LoopingNumberPicker(
                                range = 0..59,
                                value = selectedMinutes,
                                onValueChange = { selectedMinutes = it },
                                label = "min",
                        )
                    }

                    // Show delete button if editing existing limit
                    if (onDelete != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        TextButton(
                                onClick = {
                                    onDelete()
                                    onDismiss()
                                },
                                modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                    text = "Remove Limit",
                                    style =
                                            TextStyle(
                                                    fontFamily = interDisplayFamily,
                                                    color = Color(0xFFEF4444),
                                                    fontWeight = FontWeight.Bold,
                                            ),
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                        onClick = {
                            onConfirm(selectedHours, selectedMinutes)
                            onDismiss()
                        }
                ) {
                    Text(
                            style = TextStyle(fontFamily = interDisplayFamily),
                            text = if (currentLimit != null) "Update" else "Set Limit",
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                            style = TextStyle(fontFamily = interDisplayFamily),
                            text = "Cancel",
                    )
                }
            },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoopingNumberPicker(
        range: IntRange,
        value: Int,
        onValueChange: (Int) -> Unit,
        label: String,
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
            modifier =
                    Modifier.height(180.dp) // slightly taller to fit the label
                            .width(70.dp),
            contentAlignment = Alignment.Center,
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
        ) {
            // Scroll picker area
            Box(
                    modifier =
                            Modifier.height(140.dp) // height for the scroll zone
                                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
            ) {
                // Highlight box
                Box(
                        modifier =
                                Modifier.align(Alignment.Center)
                                        .height(itemHeight)
                                        .fillMaxWidth()
                                        .background(Color(0x22000000), RoundedCornerShape(8.dp)),
                )

                LazyColumn(
                        state = listState,
                        flingBehavior = flingBehavior,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        contentPadding = PaddingValues(vertical = 50.dp),
                ) {
                    items(extendedList.size) { index ->
                        val number = extendedList[index]
                        val isSelected = index == centerItemIndex
                        Text(
                                text = number.toString().padStart(2, '0'),
                                style =
                                        TextStyle(
                                                fontSize = if (isSelected) 24.sp else 18.sp,
                                                fontWeight =
                                                        if (isSelected) FontWeight.Bold
                                                        else FontWeight.Normal,
                                                color = if (isSelected) Color.Black else Color.Gray,
                                                textAlign = TextAlign.Center,
                                        ),
                                modifier =
                                        Modifier.height(itemHeight)
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
                    modifier = Modifier.padding(top = 6.dp),
            )
        }
    }
}
