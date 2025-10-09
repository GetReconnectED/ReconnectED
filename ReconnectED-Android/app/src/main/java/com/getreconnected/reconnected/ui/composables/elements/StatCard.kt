package com.getreconnected.reconnected.ui.composables.elements

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.ui.theme.CardColorLightPrimary
import com.getreconnected.reconnected.ui.theme.TextColorLightPrimary

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
    color: Color,
    modifier: Modifier = Modifier.Companion
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = CardColorLightPrimary)
    ) {
        Row(
            modifier = Modifier.Companion.padding(16.dp),
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Column(
                modifier = Modifier.Companion.weight(1f)
            ) {
                Text(
                    text = title, style = TextStyle(
                        fontFamily = interDisplayFamily
                    ), color = TextColorLightPrimary
                )
                Text(
                    text = value, style = TextStyle(
                        fontFamily = interDisplayFamily,
                        fontWeight = FontWeight.Companion.Bold,
                        fontSize = 24.sp
                    ), color = color // Use the color parameter
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
