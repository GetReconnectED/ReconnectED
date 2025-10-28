package com.getreconnected.reconnected.ui.menus

import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.activities.MainActivity
import com.getreconnected.reconnected.core.Application
import com.getreconnected.reconnected.core.dataManager.QuotesManager
import com.getreconnected.reconnected.core.models.Menus
import com.getreconnected.reconnected.core.models.getMenuRoute
import com.getreconnected.reconnected.core.util.formatTime
import com.getreconnected.reconnected.core.util.getDaysActive
import com.getreconnected.reconnected.core.util.getScreenTimeInMillis
import com.getreconnected.reconnected.core.util.hasUsageStatsPermission
import com.getreconnected.reconnected.core.viewModels.UIRouteViewModel
import com.getreconnected.reconnected.ui.composables.elements.StatCard
import com.getreconnected.reconnected.ui.theme.LocalReconnectEDColors
import com.getreconnected.reconnected.ui.theme.interDisplayFamily
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.cartesianLayerPadding
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import kotlinx.coroutines.delay
import java.util.Calendar

/**
 * Composable for rendering the Dashboard screen interface. The Dashboard includes
 * components for displaying user statistics such as screen time, days active, as well as
 * navigation options for additional features like AI Assistant and App Usage Limits.
 *
 * @param navController Used to handle navigation actions within the app.
 * @param viewModel The view model providing state and business logic for the Dashboard screen.
 * @param modifier The modifier to be applied to the Dashboard layout.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun Dashboard(
    navController: NavController,
    viewModel: UIRouteViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val daysActive = getDaysActive(context)
    var daysActiveWord: String = "days"
    val hasPermission = hasUsageStatsPermission(context)
    val scrollState = rememberScrollState()
    val gradientStart = LocalReconnectEDColors.current.gradientStart
    val gradientEnd = LocalReconnectEDColors.current.gradientEnd

    val (selectedQuote, setSelectedQuote) = remember { mutableStateOf(QuotesManager.getQuotes(context).random()) }

    val screenTimeMillis by produceState(initialValue = -1L, key1 = hasPermission) {
        if (hasPermission) {
            while (true) {
                value = getScreenTimeInMillis(context)
                delay(60_000)
            }
        } else {
            value = -1L
        }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (!hasPermission) {
                Toast
                    .makeText(
                        context,
                        "Please grant permission to continue",
                        Toast.LENGTH_LONG,
                    ).show()
                // if doesn't have permission, exit the application.
                (context as MainActivity).finish()
            }
        }

    if (daysActive == 1L) daysActiveWord = "day"
    if (!hasPermission) {
        Toast
            .makeText(
                context,
                "Please grant ${Application.NAME} app usage permission to continue",
                Toast.LENGTH_LONG,
            ).show()
        LaunchedEffect(Unit) {
            launcher.launch(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
    }

    val screenTimeValue =
        when {
            !hasPermission -> "Tap to permit"
            else -> formatTime(screenTimeMillis, true)
        }

    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(Menus.Dashboard.name) { inclusive = false }
            launchSingleTop = true
        }
        viewModel.setSelectedRoute(getMenuRoute(route))
    }

    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors = listOf(gradientStart, gradientEnd),
                        ),
                ).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(modifier = Modifier)
        GreetingTextWithTime(name = viewModel.activeUser.value)

        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            modifier =
                Modifier.fillMaxWidth().height(125.dp).clickable {
                    setSelectedQuote(QuotesManager.getQuotes(context).random())
                },
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "“${selectedQuote.quote}”",
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Italic,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
                Text(
                    text = "- ${selectedQuote.author}",
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontWeight = FontWeight.Light,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StatCard(
                title = "Screen Time Today",
                value = screenTimeValue,
                icon = painterResource(R.drawable.daily_screen_time),
                color = Color(0xFF008F46), // dark green
                modifier =
                    Modifier.weight(1f).fillMaxWidth().fillMaxHeight().clickable {
                        if (!hasPermission) {
                            context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                        } else {
                            navigateTo(Menus.ScreenTimeTracker.name)
                        }
                    },
            )
            StatCard(
                title = "Days Active",
                value = "$daysActive $daysActiveWord",
                icon = painterResource(R.drawable.days_active),
                color = Color(0xFF0453AE), // dark green
                modifier = Modifier.weight(1f).fillMaxWidth().fillMaxHeight(),
            )
        }

        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Weekly Average Screen Time",
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                )
                Spacer(Modifier.height(16.dp))
                WeeklyAvgScreenTimeChart()
                Spacer(Modifier.height(16.dp))
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                modifier =
                    Modifier.weight(1f).fillMaxHeight().clickable {
                        navigateTo(Menus.AIAssistant.name)
                    },
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "AI Assistant",
                        style =
                            TextStyle(
                                fontFamily = interDisplayFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gemini_logo),
                            contentDescription = "Gemini AI Logo",
                            modifier = Modifier.height(128.dp).width(128.dp).padding(16.dp),
                        )
                    }
                    Text(
                        text = "Advisor powered by GemIni API",
                        style =
                            TextStyle(
                                fontFamily = interDisplayFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                    )
                }
            }
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                modifier =
                    Modifier.weight(1f).fillMaxHeight().clickable {
                        navigateTo(Menus.ScreenTimeLimit.name)
                    },
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "Limit App Usage",
                        style =
                            TextStyle(
                                fontFamily = interDisplayFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.screen_time_limit_green),
                            contentDescription = "Limit App Usage",
                            modifier = Modifier.height(128.dp).width(128.dp).padding(16.dp),
                        )
                    }
                    Text(
                        text = "Set time limits to your applications and restrict usage",
                        style =
                            TextStyle(
                                fontFamily = interDisplayFamily,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                    )
                }
            }
        }
        Spacer(modifier = Modifier)
    }
}

/**
 * Displays a greeting text based on the time of day and the provided name.
 * The greeting adapts to different times of the day (morning, afternoon, evening, night)
 * and incorporates the user's name into the message.
 *
 * @param name The name of the user to be included in the greeting text.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun GreetingTextWithTime(name: String) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val timeOfDay =
        when (hour) {
            in 5..11 -> "morning"
            in 12..17 -> "afternoon"
            in 18..23 -> "evening"
            else -> "night"
        }
    Text(
        text = "Good $timeOfDay, $name!",
        style =
            TextStyle(
                fontFamily = interDisplayFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            ),
    )
}

private val BottomAxisLabelKey = ExtraStore.Key<List<String>>()
private val BottomAxisValueFormatter =
    CartesianValueFormatter { context, x, _ ->
        context.model.extraStore[BottomAxisLabelKey][x.toInt()]
    }

private val data = mapOf("Sun" to 1, "Mon" to 2, "Tue" to 6, "Wed" to 4, "Thu" to 9, "Fri" to 5, "Sat" to 3)

/**
 * A composable that renders a bar chart representing the average screen time for a week.
 *
 * @param modelProducer A producer for the Cartesian chart model, which provides the data and updates for the chart.
 * @param modifier Modifier to customize the layout and styling of the chart's container.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
private fun WeeklyAvgScreenTimeChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {
    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberColumnCartesianLayer(
                    ColumnCartesianLayer.ColumnProvider.series(
                        rememberLineComponent(
                            fill = fill(MaterialTheme.colorScheme.primary),
                            thickness = 16.dp,
                        ),
                    ),
                ),
                startAxis =
                    VerticalAxis.rememberStart(
                        label =
                            rememberTextComponent(
                                color = MaterialTheme.colorScheme.onSurface,
                                textSize = 12.sp,
                            ),
                    ),
                bottomAxis =
                    HorizontalAxis.rememberBottom(
                        itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() },
                        valueFormatter = BottomAxisValueFormatter,
                        label =
                            rememberTextComponent(
                                color = MaterialTheme.colorScheme.onSurface,
                                textSize = 12.sp,
                            ),
                    ),
                layerPadding = { cartesianLayerPadding(scalableStart = 8.dp, scalableEnd = 8.dp) },
            ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}

/**
 * A composable function that displays a bar chart representing the average screen time per day
 * over a week. It dynamically updates its data by leveraging a chart model producer.
 *
 * @param modifier Modifier to customize the layout and styling of the chart's container.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun WeeklyAvgScreenTimeChart(modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            columnSeries { series(data.values) }
            extras { it[BottomAxisLabelKey] = data.keys.toList() }
        }
    }
    WeeklyAvgScreenTimeChart(modelProducer, modifier)
}
