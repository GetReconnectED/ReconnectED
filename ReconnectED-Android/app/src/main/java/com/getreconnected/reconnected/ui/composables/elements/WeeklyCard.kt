package com.getreconnected.reconnected.ui.composables.elements

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.formatTime
import com.getreconnected.reconnected.core.models.entities.WeeklyScreenTime

/**
 * A composable that displays an icon for a given drawable.
 *
 * @param drawable The drawable to display.
 * @param modifier The modifier to apply to the icon.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
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

/**
 * A composable that displays a card for a given weekly screen time.
 *
 * @param week The weekly screen time to display.
 * @param context The context to use for getting the application icon.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun WeeklyCard(
    week: WeeklyScreenTime,
    context: Context,
) {
    val pm = context.packageManager

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    "Week ${week.weekNumber}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    formatTime(week.totalTimeMillis),
                    color = MaterialTheme.colorScheme.primary, // Green stat value
                )
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
                        modifier = Modifier.size(40.dp).padding(4.dp),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun WeeklyCardPreview() {
    val week =
        WeeklyScreenTime(
            weekNumber = 1,
            totalTimeMillis = 3600000,
            topApps = listOf("com.example.app1", "com.example.app2", "com.example.app3"),
        )
    val context = LocalContext.current

    WeeklyCard(week, context)
}
