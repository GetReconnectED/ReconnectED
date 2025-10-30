package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.core.viewModels.ScreenTimeTrackerViewModel
import com.getreconnected.reconnected.ui.theme.interDisplayFamily
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.format.TextStyle as JavaTextStyle

/**
 * Composable that displays a calendar view with enhanced theming and styling,
 * along with a container showcasing application usage details.
 *
 * @param modifier Modifier to be applied to the root layout of the calendar, allowing
 * customization of size, padding, background, or other styling properties.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun Calendar(
    viewModel: ScreenTimeTrackerViewModel,
    modifier: Modifier = Modifier,
) {
    val appUsageStats by viewModel.appUsageStats.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()

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
                ).padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            modifier = Modifier.fillMaxWidth(),
        ) {
            ScreenTimeCalendar(
                selectedDate = selectedDate,
                onDateSelected = { date ->
                    if (selectedDate == date) {
                        viewModel.clearSelectedDate()
                    } else {
                        viewModel.loadUsageStatsForDate(date)
                    }
                },
            )
        }
        AppUsageContainer(
            appList = appUsageStats,
            selectedDate = selectedDate,
        )
    }
}

/**
 * A composable that displays a screen time calendar with the ability to navigate between months
 * and view the days of the selected month. It also highlights today's date for better visibility.
 *
 * @param selectedDate The currently selected date, if any.
 * @param onDateSelected Callback invoked when a date is clicked.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun ScreenTimeCalendar(
    selectedDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit = {},
) {
    val currentMonth = remember { YearMonth.now() }
    var displayedMonth by remember { mutableStateOf(currentMonth) }

    val startMonth = currentMonth.minusMonths(12)
    val endMonth = currentMonth.plusMonths(12)
    val today = LocalDate.now()
    val daysOfWeek = daysOfWeek()

    val state =
        rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = displayedMonth,
            firstDayOfWeek = daysOfWeek.first(),
        )

    val coroutineScope = rememberCoroutineScope()
    val monthFormatter = remember { DateTimeFormatter.ofPattern("MMMM yyyy") }

    // === Sync displayedMonth when user scrolls/swipes ===
    LaunchedEffect(state.firstVisibleMonth) {
        displayedMonth = state.firstVisibleMonth.yearMonth
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
    ) {
        // === Header (month + arrows) ===
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                coroutineScope.launch {
                    displayedMonth = displayedMonth.minusMonths(1)
                    state.animateScrollToMonth(displayedMonth)
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous month")
            }

            Text(
                text = displayedMonth.format(monthFormatter),
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Companion.Bold,
                        color = Color(0xFF020202),
                    ),
            )

            IconButton(onClick = {
                coroutineScope.launch {
                    displayedMonth = displayedMonth.plusMonths(1)
                    state.animateScrollToMonth(displayedMonth)
                }
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next month")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // === Weekday Headers ===
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    text = dayOfWeek.getDisplayName(JavaTextStyle.SHORT, Locale.getDefault()),
                    modifier = Modifier.weight(1f).padding(vertical = 4.dp),
                    textAlign = TextAlign.Center,
                    style =
                        TextStyle(
                            fontFamily = interDisplayFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Companion.Medium,
                            color = Color(0xFF595959),
                        ),
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // === Calendar Grid ===
        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                DayCell(
                    date = day.date,
                    today = today,
                    isSelected = selectedDate == day.date,
                    onClick = { onDateSelected(day.date) },
                )
            },
        )
    }
}

/**
 * A composable that represents a single day cell in a calendar view.
 *
 * @param date The specific date to be displayed in the cell.
 * @param today The current date, used to determine if the cell represents today.
 * @param isSelected Whether this date is currently selected.
 * @param onClick Callback invoked when the cell is clicked.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun DayCell(
    date: LocalDate,
    today: LocalDate,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val isToday = date == today

    Box(
        modifier =
            Modifier
                .aspectRatio(1f)
                .padding(2.dp)
                .background(
                    color =
                        when {
                            isSelected -> Color(0xFF10B981) // Green background for selected date
                            isToday -> Color(0xFFE0F2FE) // Light blue background for today
                            else -> Color.Transparent
                        },
                    shape = androidx.compose.foundation.shape.CircleShape,
                ).clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 16.sp,
                    fontWeight =
                        if (isSelected) {
                            FontWeight.Companion.Bold
                        } else {
                            FontWeight.Companion.Normal
                        },
                    color =
                        when {
                            isSelected -> Color.White // White text for selected date
                            isToday -> Color(0xFF020202) // Black text for today
                            else -> Color(0xAA595959) // Gray text for other dates
                        },
                ),
        )
    }
}
