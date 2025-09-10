package com.reconnected.reconnected.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reconnected.reconnected.R
import com.reconnected.reconnected.ui.theme.BackgroundColor
import com.reconnected.reconnected.ui.theme.DashboardBackgroundGradientColor
import com.reconnected.reconnected.ui.theme.DashboardCardBackgroundColor
import com.reconnected.reconnected.ui.theme.DashboardCardTextColor
import com.reconnected.reconnected.ui.theme.DashboardDaysActiveCardTextColor
import com.reconnected.reconnected.ui.theme.DashboardStatCardTextColor
import com.reconnected.reconnected.ui.theme.DrawerBackgroundColor
import com.reconnected.reconnected.ui.theme.DrawerContainerColor
import com.reconnected.reconnected.ui.theme.interDisplayFamily
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * The main dashboard screen.
 *
 * @param modifier The modifier to apply to this layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = modifier.background(BackgroundColor),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        }) {
        Scaffold(
            topBar = {
                TopBar(
                    onOpenDrawer = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    })
            }) { padding ->
            DashboardContent(
                modifier = Modifier.padding(padding)
            )

        }
    }

}

/**
 * The content of the dashboard screen.
 *
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun DashboardContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = DashboardBackgroundGradientColor
                )
            )
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // --- START OF MODIFIED SECTION ---
        // Call the new composable to display the time-based greeting
        GreetingTextWithTime(name = "Juan")
        // --- END OF MODIFIED SECTION ---
        // The quote card
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ), colors = CardDefaults.cardColors(
                containerColor = DashboardCardBackgroundColor,
            ), modifier = modifier
                .fillMaxWidth()
                .height(125.dp)
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Daily Inspiration", style = TextStyle(
                        fontFamily = interDisplayFamily, fontWeight = FontWeight.Light
                    ), color = DashboardCardTextColor
                )
                Text(
                    text = "“Digital detox is not about disconnecting, but reconnecting.”",
                    style = TextStyle(
                        fontFamily = interDisplayFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic,
                        fontSize = 16.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(vertical = 8.dp),
                    color = DashboardCardTextColor
                )
            }
        }
        Row(
            modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(
                title = "Screen Time Today",
                value = "3h 15m",
                icon = Icons.Default.DateRange,
                color = DashboardStatCardTextColor,
                modifier = modifier.weight(1f)  //.requiredHeight(100.dp)
            )
            StatCard(
                title = "Days Active",
                value = "14 days",
                icon = Icons.Default.CheckCircle,
                color = DashboardDaysActiveCardTextColor,
                modifier = modifier.weight(1f)  //.requiredHeight(100.dp)
            )
        }
    }
}

/**
 * A reusable composable for the stat cards on the dashboard.
 *
 * @param title The title of the card.
 * @param value The value of the card.
 * @param icon The icon to show in the card.
 * @param color The color of the card text.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun StatCard(
    title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = DashboardCardBackgroundColor,
        )
    ) {
        Row(
            modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier.weight(1f)
            ) {
                Text(
                    text = title, style = TextStyle(
                        fontFamily = interDisplayFamily
                    ), color = DashboardCardTextColor
                )
                Text(
                    text = value, style = TextStyle(
                        fontFamily = interDisplayFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ), color = color
                )
            }
            Spacer(modifier = modifier.width(8.dp))
            Icon(
                imageVector = icon, contentDescription = title, tint = color
            )
        }
    }
}

/**
 * The content of the drawer.
 *
 * @param modifier The modifier to apply to this layout.
 */
@Preview
@Composable
fun DrawerContent(modifier: Modifier = Modifier) {
    val sidebarButtonText = TextStyle(
        fontFamily = interDisplayFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = DrawerBackgroundColor
    )

    ModalDrawerSheet(
        drawerContainerColor = DrawerContainerColor, modifier = modifier.fillMaxHeight()
    ) {
        Column {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.recologo_ca),
                    contentDescription = "Companion App Logo",
                    modifier = modifier
                        .width(237.dp)
                        .height(232.dp),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center
                )
            }

            NavigationDrawerItem(label = {
                Text(
                    text = "Dashboard", style = sidebarButtonText
                )
            }, selected = false, onClick = { })
            NavigationDrawerItem(label = {
                Text(
                    text = "Screen Time Tracker", style = sidebarButtonText
                )
            }, selected = false, onClick = { /*TODO*/ })
            NavigationDrawerItem(label = {
                Text(
                    text = "Screen Time Limit", style = sidebarButtonText
                )
            }, selected = false, onClick = { /*TODO*/ })
            NavigationDrawerItem(label = {
                Text(
                    text = "Calendar", style = sidebarButtonText
                )
            }, selected = false, onClick = { /*TODO*/ })
            NavigationDrawerItem(label = {
                Text(
                    text = "AI Assistant", style = sidebarButtonText
                )
            }, selected = false, onClick = { /*TODO*/ })
            Spacer(modifier = modifier.fillMaxHeight())
            BottomAppBar { }
        }
    }
}

/**
 * The top bar of the dashboard screen.
 *
 * @param onOpenDrawer The action to perform when the drawer button is clicked.
 * @param modifier The modifier to apply to this layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onOpenDrawer: () -> Unit, modifier: Modifier = Modifier
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
        containerColor = DrawerContainerColor, titleContentColor = DrawerBackgroundColor
    ), title = {
        Text(
            text = "Dashboard", style = TextStyle(
                fontFamily = interDisplayFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
            )
        )
    }, navigationIcon = {
        IconButton(onClick = { onOpenDrawer() }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu Icon",
                tint = DrawerBackgroundColor

            )
        }
    }, actions = {
        IconButton(onClick = { /* Handle profile click */ }) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "User Profile",
                modifier = modifier
                    .padding(1.dp)
                    .width(36.dp)
                    .height(36.dp),
                tint = DrawerBackgroundColor
            )
        }
    })
}

/**
 * A composable that displays a greeting with the current time.
 *
 * @param name The name to greet.
 * @param modifier The modifier to apply to this layout.
 */
@Composable
fun GreetingTextWithTime(
    name: String, modifier: Modifier = Modifier
) {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val timeOfDay = when (hour) {
        in 5..11 -> "morning"
        in 12..17 -> "afternoon"
        in 18..23 -> "evening"
        else -> "night"
    }

    Text(
        modifier = modifier.padding(top = 16.dp),
        text = "Good $timeOfDay, $name!",
        style = TextStyle(
            fontFamily = interDisplayFamily, fontSize = 24.sp, fontWeight = FontWeight.Bold
        ),
        color = DashboardCardTextColor
    )
}
