package com.getreconnected.reconnected.core.models.entities

data class WeeklyScreenTime(
    val id: Int = 0,
    val weekNumber: Int,
    val totalTimeMillis: Long,
    val topApps: List<String>,
)
