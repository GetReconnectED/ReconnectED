package com.getreconnected.reconnected.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.core.util.formatTime
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

/**
 * Activity that is displayed when an app's usage limit is reached.
 * Shows a blocking screen and sends the user back to the home screen.
 */
class AppBlockedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val packageName = intent.getStringExtra("package_name") ?: ""
        val appName = intent.getStringExtra("app_name") ?: "This app"
        val limitMillis = intent.getLongExtra("limit_millis", 0L)

        setContent {
            ReconnectEDTheme {
                AppBlockedScreen(
                    appName = appName,
                    limitMillis = limitMillis,
                    onGoHome = {
                        goToHomeScreen()
                        finish()
                    },
                )
            }
        }
    }

    private fun goToHomeScreen() {
        val homeIntent =
            Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        startActivity(homeIntent)
    }
}

/**
 * Composable screen that displays the app blocked message.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AppBlockedScreen(
    appName: String,
    limitMillis: Long,
    onGoHome: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color(0xFFEF4444), // Red
                                    Color(0xFFF97316), // Orange
                                ),
                        ),
                ).padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Locked",
            modifier = Modifier.size(120.dp),
            tint = Color.White,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Time's Up!",
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "You've reached your limit for",
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.9f),
                ),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = appName,
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                ),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Daily limit: ${formatTime(limitMillis)}",
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.8f),
                ),
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Take a break and reconnect with what matters!",
            style =
                TextStyle(
                    fontFamily = interDisplayFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.9f),
                ),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onGoHome,
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFFEF4444),
                ),
            modifier = Modifier.height(56.dp),
        ) {
            Text(
                text = "Go to Home Screen",
                style =
                    TextStyle(
                        fontFamily = interDisplayFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )
        }
    }
}
