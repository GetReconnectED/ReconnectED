package com.getreconnected.reconnected.legacy.ui.elements

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.legacy.data.WeeklyScreenTime
import java.util.concurrent.TimeUnit

fun formatTime(millis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    return "${hours}h ${minutes}m"
}

@Composable
fun DrawableIcon(
    drawable: Drawable,
    modifier: Modifier = Modifier,
) {
    Image(
        bitmap = drawable.toBitmap().asImageBitmap(),
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
fun WeeklyCard(
    week: WeeklyScreenTime,
    context: Context,
) {
    val pm = context.packageManager

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text("Week ${week.weekNumber}", fontWeight = FontWeight.Bold)
                Text(formatTime(week.totalTimeMillis), color = Color.Green)
            }
            Row {
                week.topApps.forEach { pkg ->
                    val icon =
                        try {
                            pm.getApplicationIcon(pkg)
                        } catch (e: PackageManager.NameNotFoundException) {
                            ContextCompat.getDrawable(context, R.drawable.ic_default_app)!!
                        }
                    DrawableIcon(
                        drawable = icon,
                        modifier =
                            Modifier
                                .size(40.dp)
                                .padding(4.dp),
                    )
                }
            }
        }
    }
}
