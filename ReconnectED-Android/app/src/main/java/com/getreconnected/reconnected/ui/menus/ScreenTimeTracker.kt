package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import com.getreconnected.reconnected.core.viewModels.ScreenTimeTrackerViewModel
import com.getreconnected.reconnected.ui.composables.elements.AppTrackerContainer
import com.getreconnected.reconnected.ui.composables.elements.AppUsageItem
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

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

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color(0xFFD1FAE5),
                                    Color(0xFFDBEAFE),
                                ),
                        ),
                ).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Track your application usage",
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                ),
        )
        AppWeekSummary()
        AppWeekSummary()
        AppWeekSummary()
    }
}

@Composable
fun AppWeekSummary(){
    Column {
        Card(
            modifier =
                Modifier.Companion
                    .fillMaxWidth(),
            // Allow card to take remaining space
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Companion.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),

            ) {
            Text(
                modifier = Modifier.Companion.padding(8.dp),
                text = "Week 1 - 40h 32m",
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Companion.Bold,
                        color = Color.Companion.Black,
                    ),
            )

        }
    }
}
