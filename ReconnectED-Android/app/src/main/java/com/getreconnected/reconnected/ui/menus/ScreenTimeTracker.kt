package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.getreconnected.reconnected.core.formatTime
import com.getreconnected.reconnected.core.models.AppUsageInfo
import com.getreconnected.reconnected.core.viewModels.ScreenTimeTrackerViewModel
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

    LazyColumn(modifier = modifier) {
        items(appUsageStats) { appUsageInfo ->
            AppUsageItem(appUsageInfo = appUsageInfo)
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
                .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = rememberDrawablePainter(drawable = appUsageInfo.appIcon),
            contentDescription = "${appUsageInfo.appName} icon",
            modifier = Modifier.size(40.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = appUsageInfo.appName, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = formatTime(appUsageInfo.usageTime),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}
