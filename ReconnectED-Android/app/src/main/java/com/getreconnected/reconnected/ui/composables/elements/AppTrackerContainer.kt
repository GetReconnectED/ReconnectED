package com.getreconnected.reconnected.ui.composables.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
 * A composable function that represents a container displaying app screen time information.
 *
 * @param appList List of app usage information
 * @param weekNumber The week number to display (e.g., 1, 2, 3)
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppTrackerContainer(
    appList: List<AppUsageInfo>,
    weekNumber: Int = 1,
) {
    // Calculate total usage time
    val totalUsageTime = appList.sumOf { it.usageTime }

    // Get top 5 most used apps
    val topApps = appList.sortedByDescending { it.usageTime }.take(5)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            // Week number and total usage time on the same line
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Week $weekNumber",
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        ),
                )

                Text(
                    text = formatTime(totalUsageTime),
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF10B981), // Green color
                        ),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // App icons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                topApps.forEach { app ->
                    Image(
                        painter = rememberDrawablePainter(drawable = app.appIcon),
                        contentDescription = "${app.appName} icon",
                        modifier =
                            Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp)),
                    )
                }
            }
        }
    }
}
