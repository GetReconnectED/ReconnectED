package com.getreconnected.reconnected.ui.composables

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.core.viewModels.UIRouteViewModel
import com.getreconnected.reconnected.ui.composables.elements.StatCard
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import com.getreconnected.reconnected.ui.theme.interDisplayFamily
import java.util.Calendar

/**
 * The content of the dashboard screen.
 *
 * @param viewModel The view model for UI routes.
 * @param modifier The modifier to apply to the dashboard content.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun Dashboard(
    viewModel: UIRouteViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.Companion.verticalGradient(
                            colors =
                                listOf(
                                    Color(0xFFD1FAE5),
                                    Color(0xFFDBEAFE),
                                ),
                        ),
                ).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GreetingTextWithTime(name = viewModel.activeUser.value)
        ElevatedCard(
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 6.dp,
                ),
            modifier = Modifier.Companion.fillMaxWidth().height(125.dp),
        ) {
            Column(
                modifier = Modifier.Companion.fillMaxSize().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Daily Inspiration",
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontWeight = FontWeight.Companion.Light,
                        ),
                )
                Text(
                    text = "“Digital detox is not about disconnecting, but reconnecting.”",
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontWeight = FontWeight.Companion.SemiBold,
                            fontStyle = FontStyle.Companion.Italic,
                            fontSize = 16.sp,
                        ),
                    textAlign = TextAlign.Companion.Center,
                    modifier = Modifier.Companion.padding(vertical = 8.dp),
                    color = Color(0xFF020202),
                )
            }
        }

        // The StatCards now use the new color parameter
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StatCard(
                title = "Screen Time Today",
                value = "3h 15m",
                icon = Icons.Default.DateRange,
                color = Color.Green,
                modifier = Modifier.Companion.weight(1f),
                // .requiredHeight(100.dp)
            )
            StatCard(
                title = "Days Active",
                value = "14 days",
                icon = Icons.Default.CheckCircle,
                color = Color.Blue,
                modifier = Modifier.Companion.weight(1f),
                // .requiredHeight(100.dp)
            )
        }
    }
}

/**
 * Displays a personalized greeting based on the current time.
 *
 * @param name The name of the user.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun GreetingTextWithTime(name: String) {
    // Get the current hour of the day
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    // Determine the time-based greeting word
    val timeOfDay =
        when (hour) {
            in 5..11 -> "morning"
            in 12..17 -> "afternoon"
            in 18..23 -> "evening"
            else -> "night"
        }

    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = "Good $timeOfDay, $name!",
        style =
            TextStyle(
                fontFamily = interDisplayFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            ),
    )
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
@SuppressLint("ViewModelConstructorInComposable")
fun DashboardPreview() {
    val viewModel = UIRouteViewModel()
    ReconnectEDTheme {
        Dashboard(viewModel)
    }
}
