package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme
import com.getreconnected.reconnected.ui.theme.interDisplayFamily

// TODO: WIP

@Composable
@Suppress("ktlint:standard:function-naming")
fun ScreenTimeLimit(modifier: Modifier = Modifier) {
    Text(
        text = "Screen Time Limit WIP",
        style =
            TextStyle(
                fontFamily = interDisplayFamily,
                fontWeight = FontWeight.Companion.SemiBold,
                fontStyle = FontStyle.Companion.Italic,
                fontSize = 16.sp,
            ),
        textAlign = TextAlign.Companion.Center,
        modifier = Modifier.Companion.padding(vertical = 8.dp),
    )
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun ScreenTimeLimitPreview() {
    ReconnectEDTheme {
        ScreenTimeLimit()
    }
}
