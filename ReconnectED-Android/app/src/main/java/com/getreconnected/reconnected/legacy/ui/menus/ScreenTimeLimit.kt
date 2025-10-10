package com.getreconnected.reconnected.legacy.ui.menus

// --- ALL NECESSARY IMPORTS ---
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.ui.theme.interDisplayFamily // Make sure this import is correct

// Data class to hold app information
data class AppUsageInfo(
    val name: String,
    val usageTime: String,
    val icon: ImageVector,
    val iconBackgroundColor: Color,
)

@Composable
fun ScreenTimeLimit(modifier: Modifier = Modifier) {
    val appList =
        listOf(
            AppUsageInfo(
                name = "YouTube",
                usageTime = "2h 39m",
                icon = Icons.Filled.PlayArrow,
                iconBackgroundColor = Color(0xFFFF0000),
            ),
            AppUsageInfo(
                name = "Facebook",
                usageTime = "2h 10m",
                icon = Icons.Filled.ThumbUp,
                iconBackgroundColor = Color(0xFF1877F2),
            ),
        )

    // The Scaffold and TopAppBar have been removed.
    // This Column is now the main container for your screen's content.
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
        // "Limit your application usage" Text
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Limit your application usage",
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                ),
        )

        // The Card containing the list
        Card(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
            // Allow card to take remaining space
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                items(appList) { app ->
                    AppUsageLimitItem(appInfo = app)
                }
            }
        }
    }
}

@Composable
fun AppUsageLimitItem(appInfo: AppUsageInfo) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(appInfo.iconBackgroundColor),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = appInfo.icon,
                contentDescription = "${appInfo.name} logo",
                tint = Color.White,
                modifier = Modifier.size(24.dp),
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = appInfo.name,
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    ),
            )
            Text(
                text = appInfo.usageTime,
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 14.sp,
                        color = Color.Gray,
                    ),
            )
        }
        IconButton(onClick = { /* Handle settings click */ }) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Set Limit",
                tint = Color.Gray,
            )
        }
    }
}
