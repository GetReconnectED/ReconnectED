package com.getreconnected.reconnected.ui.composables.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import com.getreconnected.reconnected.core.util.formatTime
import com.getreconnected.reconnected.ui.theme.interDisplayFamily
import com.google.accompanist.drawablepainter.rememberDrawablePainter

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
            Modifier.Companion
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Companion.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier =
                Modifier.Companion
                    .size(40.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Companion.Center,
        ) {
            Image(
                painter = rememberDrawablePainter(drawable = appUsageInfo.appIcon),
                contentDescription = "${appUsageInfo.appName} icon",
                modifier = Modifier.Companion.size(40.dp),
            )
        }
        Column(modifier = Modifier.Companion.weight(1f)) {
            Text(
                text = appUsageInfo.appName,
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Companion.SemiBold,
                    ),
            )
            Text(
                text = formatTime(appUsageInfo.usageTime),
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 16.sp,
                        color = Color.Companion.Gray,
                    ),
            )
        }
    }
}
