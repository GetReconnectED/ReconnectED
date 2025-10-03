package com.getreconnected.reconnected.ui.composables.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.ui.composables.elements.StatCard
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

// TODO: WIP

@Composable
fun ScreenTimeLimit(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.Companion.verticalGradient(
                    colors = listOf(
                        Color(0xFFD1FAE5), Color(0xFFDBEAFE)
                    )
                )
            )
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier.Companion.padding(top = 16.dp),
            text = "Screen Time Limit",
            style = TextStyle(
                fontFamily = interDisplayFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Companion.Bold
            ),
            color = Color(0xFF020202)
        )

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ), colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF4F4F4),
            ), modifier = Modifier.Companion
                .fillMaxWidth()
                .height(125.dp)
        ) {
            Column(
                modifier = Modifier.Companion
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Daily Inspiration", style = TextStyle(
                        fontFamily = interDisplayFamily, fontWeight = FontWeight.Companion.Light
                    ), color = Color(0xFF020202)
                )
                Text(
                    text = "“Digital detox is not about disconnecting, but reconnecting.”",
                    style = TextStyle(
                        fontFamily = interDisplayFamily,
                        fontWeight = FontWeight.Companion.SemiBold,
                        fontStyle = FontStyle.Companion.Italic,
                        fontSize = 16.sp
                    ),
                    textAlign = TextAlign.Companion.Center,
                    modifier = Modifier.Companion.padding(vertical = 8.dp),
                    color = Color(0xFF020202)
                )
            }
        }

        // The StatCards now use the new color parameter
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                title = "Screen Time Today",
                value = "3h 15m",
                icon = Icons.Default.DateRange,
                color = Color(0xFF008F46), // Green color
                modifier = Modifier.Companion.weight(1f)
//                        .requiredHeight(100.dp)
            )
            StatCard(
                title = "Days Active",
                value = "14 days",
                icon = Icons.Default.CheckCircle,
                color = Color(0xFF0453AE), // Blue color
                modifier = Modifier.Companion.weight(1f)
//                        .requiredHeight(100.dp)
            )
        }
    }
}