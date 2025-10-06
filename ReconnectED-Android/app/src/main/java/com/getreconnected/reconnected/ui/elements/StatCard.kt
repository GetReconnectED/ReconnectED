package com.getreconnected.reconnected.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

/**
 * A reusable composable for the stat cards on the dashboard.
 * It now accepts a color parameter to customize its appearance.
 */
@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color, // The new color parameter
    modifier: Modifier = Modifier.Companion,
    valueFontSize: TextUnit = 24.sp
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF4F4F4),
        )
    ) {
        Row(
            modifier = Modifier.Companion.padding(16.dp),
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Column(
                modifier = Modifier.Companion.weight(1f)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = interDisplayFamily,
                        fontWeight = FontWeight.Companion.Normal,
                        fontSize = 14.sp
                    ),
                    color = Color(0xFF020202)
                )
                Text(
                    text = value,
                    style = TextStyle(
                        fontFamily = interDisplayFamily,
                        fontWeight = FontWeight.Companion.Bold,
                        fontSize = valueFontSize,
                    ),
                    color = color // Use the color parameter
                )
            }
            Spacer(modifier = Modifier.Companion.width(8.dp))
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color // Use the color parameter
            )
        }
    }
}