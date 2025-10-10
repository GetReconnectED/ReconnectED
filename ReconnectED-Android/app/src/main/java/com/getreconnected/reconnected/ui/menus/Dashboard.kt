package com.getreconnected.reconnected.ui.menus

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.data.formatScreenTime
import com.getreconnected.reconnected.data.getDaysActive
import com.getreconnected.reconnected.data.getScreenTimeInMillis
import com.getreconnected.reconnected.data.hasUsageStatsPermission
import com.getreconnected.reconnected.ui.elements.StatCard
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

// --- HELPER FUNCTIONS ---

@Composable
fun Dashboard(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val daysActive = getDaysActive(context)
    val hasPermission = hasUsageStatsPermission(context)
    val scrollState = rememberScrollState()

    val screenTimeMillis by produceState(initialValue = -1L, key1 = hasPermission) {
        if (hasPermission) {
            while (true) {
                value = getScreenTimeInMillis(context)
                delay(60_000) // 1 minute
            }
        } else {
            value = -1L
        }
    }

    if (!hasUsageStatsPermission(context)) {
        context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    val screenTimeValue =
        when {
            !hasPermission -> "Tap to permit"
            else -> formatScreenTime(screenTimeMillis)
        }

    // --- UI Layout ---
    Column(
        modifier =
            modifier
                .verticalScroll(scrollState)
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
        Spacer(
            modifier = Modifier,
        )
        GreetingTextWithTime(name = "Juan")

        ElevatedCard(
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 6.dp,
                ),
            colors =
                CardDefaults.cardColors(
                    containerColor = Color(0xFFF4F4F4),
                ),
            modifier =
                Modifier.Companion
                    .fillMaxWidth()
                    .height(125.dp),
        ) {
            Column(
                modifier =
                    Modifier.Companion
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
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
                    color = Color(0xFF020202),
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

        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Corrected typo here
        ) {
            StatCard(
                title = "Screen Time Today",
                value = screenTimeValue,
                icon = Icons.Default.DateRange,
                color = Color(0xFF008F46),
                modifier =
                    Modifier.Companion
                        .weight(1f)
                        .height(80.dp)
                        .clickable {
                            if (!hasPermission) {
                                context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                            }
                        },
            )
            StatCard(
                title = "Days Active",
                value = "$daysActive days",
                icon = Icons.Default.CheckCircle,
                color = Color(0xFF0453AE),
                modifier =
                    Modifier.Companion
                        .weight(1f)
                        .height(80.dp),
            )
        }
        ElevatedCard(
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 6.dp,
                ),
            colors =
                CardDefaults.cardColors(
                    containerColor = Color(0xFFF4F4F4),
                ),
            modifier =
                Modifier.Companion
                    .fillMaxWidth(),
        ) {
            Column(
                modifier =
                    Modifier.Companion
                        .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Companion.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.Companion.padding(top = 16.dp),
                    text = "Weekly Average Screen Time",
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Companion.SemiBold,
                        ),
                    color = Color(0xFF595959),
                )
                Spacer(Modifier.height(16.dp))
                WeeklyAvgScreenTimeChart()
                Spacer(Modifier.height(16.dp))
            }
        }
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Corrected typo here
        ) {
            ElevatedCard(
                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 6.dp,
                    ),
                colors =
                    CardDefaults.cardColors(
                        containerColor = Color(0xFFF4F4F4),
                    ),
                modifier =
                    Modifier.Companion
                        .weight(1f)
                        .height(200.dp),
            ) {
                Column(
                    modifier =
                        Modifier.Companion
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Companion.Start,
                ) {
                    Text(
                        text = "AI Assistant",
                        style =
                            TextStyle(
                                fontFamily = interDisplayFamily,
                                fontWeight = FontWeight.Companion.Bold,
                                fontSize = 16.sp,
                            ),
                        color = Color(0xFF595959),
                    )
                    Box(
                        modifier =
                            Modifier.Companion
                                .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gemini_logo),
                            contentDescription = "Gemini AI Logo",
                            modifier =
                                Modifier
                                    .height(128.dp)
                                    .width(128.dp)
                                    .padding(16.dp),
                        )
                    }
                    Text(
                        text = "Advisor powered by GemIni API",
                        style =
                            TextStyle(
                                fontFamily = interDisplayFamily,
                                fontWeight = FontWeight.Companion.Normal,
                                fontSize = 12.sp,
                            ),
                        color = Color(0xFF595959),
                    )
                }
            }
            ElevatedCard(
                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 6.dp,
                    ),
                colors =
                    CardDefaults.cardColors(
                        containerColor = Color(0xFFF4F4F4),
                    ),
                modifier =
                    Modifier.Companion
                        .weight(1f)
                        .height(200.dp),
            ) {
                Column(
                    modifier =
                        Modifier.Companion
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Companion.Start,
                ) {
                    Text(
                        text = "Limit App Usage",
                        style =
                            TextStyle(
                                fontFamily = interDisplayFamily,
                                fontWeight = FontWeight.Companion.Bold,
                                fontSize = 16.sp,
                            ),
                        color = Color(0xFF595959),
                    )
                    Box(
                        modifier =
                            Modifier.Companion
                                .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.screen_time_limit_green),
                            contentDescription = "Limit App Usage",
                            modifier =
                                Modifier
                                    .height(128.dp)
                                    .width(128.dp)
                                    .padding(16.dp),
                        )
                    }
                    Text(
                        text = "Set time limits to your applications and restrict usage",
                        style =
                            TextStyle(
                                fontFamily = interDisplayFamily,
                                fontWeight = FontWeight.Companion.Normal,
                                fontSize = 12.sp,
                            ),
                        color = Color(0xFF595959),
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier,
        )
    }
}

@Composable
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
                fontWeight = FontWeight.Companion.Bold,
            ),
        color = Color(0xFF020202),
    )
}

private val BottomAxisLabelKey = ExtraStore.Key<List<String>>()

private val BottomAxisValueFormatter =
    CartesianValueFormatter { context, x, _ ->
        context.model.extraStore[BottomAxisLabelKey][x.toInt()]
    }

private val data =
    mapOf("Sun" to 1, "Mon" to 2, "Tue" to 6, "Wed" to 4, "Thu" to 9, "Fri" to 5, "Sat" to 3)

@Composable
private fun WeeklyAvgScreenTimeChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {
    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberColumnCartesianLayer(
                    ColumnCartesianLayer.ColumnProvider.series(
                        rememberLineComponent(fill = fill(Color(0xff008F46)), thickness = 16.dp),
                    ),
                ),
                startAxis =
                    VerticalAxis.rememberStart(
                        label =
                            rememberTextComponent(
                                color = Color(0xFF595959),
                                textSize = 12.sp,
                            ),
                    ),
                bottomAxis =
                    HorizontalAxis.rememberBottom(
                        itemPlacer = remember { HorizontalAxis.ItemPlacer.segmented() },
                        valueFormatter = BottomAxisValueFormatter,
                        label =
                            rememberTextComponent(
                                color = Color(0xFF595959),
                                textSize = 12.sp,
                            ),
                    ),
                layerPadding = { cartesianLayerPadding(scalableStart = 8.dp, scalableEnd = 8.dp) },
            ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}

@Composable
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
