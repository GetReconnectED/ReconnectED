package com.getreconnected.reconnected.ui.composables.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

/**
 * A composable function that represents a container displaying app screen time information.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppTrackerContainer(appList: List<AppUsageInfo>) {
    Column {
        Card(
            modifier =
                Modifier.Companion
                    .fillMaxWidth()
                    .weight(1f),
            // Allow card to take remaining space
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

            ) {
            Text(
                modifier = Modifier.Companion.padding(horizontal = 8.dp),
                text = "Week 1",
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Companion.Bold,
                        color = Color.Companion.Black,
                    ),
            )
            LazyColumn(
                modifier = Modifier.Companion.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {

                items(appList) { app ->
                    AppUsageItem(appUsageInfo = app)
                }
            }
        }
    }
}
