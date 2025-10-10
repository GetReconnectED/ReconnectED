package com.getreconnected.reconnected.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme

@Composable
fun Calendar(modifier: Modifier = Modifier) {
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
    }
}

@Preview(showBackground = true)
@Composable
@Suppress("ktlint:standard:function-naming")
fun CalendarPreview() {
    ReconnectEDTheme {
        Calendar()
    }
}
