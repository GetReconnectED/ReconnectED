package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.formatTime
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import com.getreconnected.reconnected.core.viewModels.ScreenTimeTrackerViewModel
import com.getreconnected.reconnected.ui.theme.interDisplayFamily
import com.google.accompanist.drawablepainter.rememberDrawablePainter

/**
 * Composable that displays and track screen time usage.
 *
 * @param modifier Modifier to apply to the composable for styling and layout customization.
 * @param viewModel The ViewModel instance responsible for providing application usage statistics.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun ScreenTimeTracker(
    modifier: Modifier = Modifier,
    viewModel: ScreenTimeTrackerViewModel,
) {
    val appUsageStats by viewModel.appUsageStats.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUsageStats()
    }

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
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Track your application usage",
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                ),
        )
        AppTrackerContainer(appList = appUsageStats)
    }
}

/**
 * A composable function that represents a container displaying app screen time information.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppTrackerContainer(appList: List<AppUsageInfo>) {
    Column {
        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
                    AppUsageItem(appUsageInfo = app)
                }
            }
        }
    }
}

/**
 * Display an item showing application usage information.
 *
 * @param appUsageInfo An object containing the application's icon, name, and usage time details.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppUsageItem(appUsageInfo: AppUsageInfo) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = rememberDrawablePainter(drawable = appUsageInfo.appIcon),
                contentDescription = "${appUsageInfo.appName} icon",
                modifier = Modifier.size(40.dp),
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = appUsageInfo.appName,
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    ),
            )
            Text(
                text = formatTime(appUsageInfo.usageTime),
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 16.sp,
                        color = Color.Gray,
                    ),
            )
        }
    }
}
