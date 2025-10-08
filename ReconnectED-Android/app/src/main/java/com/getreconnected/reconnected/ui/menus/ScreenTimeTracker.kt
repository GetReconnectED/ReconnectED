package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.getreconnected.reconnected.data.getDaysActive
import com.getreconnected.reconnected.ui.elements.WeeklyCard
import com.getreconnected.reconnected.ui.navigation.ReconnectedViewModel

@Composable
fun ScreenTimeTracker(modifier: Modifier = Modifier, viewModel: ReconnectedViewModel) {
    val context = LocalContext.current
    val installTime = getDaysActive(context)
    // Run once on entering the screen

    Column(
            modifier =
                    Modifier.fillMaxSize()
                            .background(
                                    brush =
                                            Brush.verticalGradient(
                                                    colors =
                                                            listOf(
                                                                    Color(0xFFD1FAE5),
                                                                    Color(0xFFDBEAFE)
                                                            )
                                            )
                            )
                            .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
    ) { WeeklyScreenTimeList(viewModel) }
}

@Composable
fun WeeklyScreenTimeList(viewModel: ReconnectedViewModel) {
    val weeks by viewModel.allWeeks.collectAsState(initial = emptyList())
    val context = LocalContext.current

    if (weeks.isEmpty()) {
        Text("No screen time data yet")
    } else {
        LazyColumn { items(weeks) { week -> WeeklyCard(week, context) } }
    }
}
