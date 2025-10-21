package com.getreconnected.reconnected.ui.composables.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

/**
 * A reusable composable for the stat cards on the dashboard.
 *
 * @param title The title of the stat card.
 * @param value The value of the stat card.
 * @param icon The icon of the stat card.
 * @param modifier The modifier to apply to the stat card.
 * @param color The color of the stat card.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun StatCard(
    title: String,
    value: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary, // Default: theme green
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = TextStyle(fontFamily = interDisplayFamily),
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = value,
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        ),
                    color = color,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = icon,
                contentDescription = title,
                tint = color,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun StatCardPreview() {
    ReconnectEDTheme {
        StatCard(
            "Screen Time Today",
            "3h 15m",
            icon = rememberVectorPainter(Icons.Default.CheckCircle),
        )
    }
}
