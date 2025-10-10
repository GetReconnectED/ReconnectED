package com.getreconnected.reconnected.data

import kotlinx.coroutines.flow.Flow

class ScreenTimeRepository(
    val dao: WeeklyScreenTimeDao,
) {
    val allWeeks: Flow<List<WeeklyScreenTime>> = dao.getAll()

    suspend fun insert(week: WeeklyScreenTime) {
        dao.insertOrUpdate(week)
    }
}
