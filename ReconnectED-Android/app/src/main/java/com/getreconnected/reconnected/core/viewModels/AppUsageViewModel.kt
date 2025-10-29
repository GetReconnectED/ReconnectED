package com.getreconnected.reconnected.core.viewModels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class AppUsageViewModel(
    val name: String,
    val usageTime: String,
    val icon: ImageVector,
    val iconBackgroundColor: Color,
)
