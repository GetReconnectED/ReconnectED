package com.getreconnected.reconnected

import android.app.Application
import androidx.room.Room
import com.getreconnected.reconnected.data.AppDatabase
import com.getreconnected.reconnected.data.ScreenTimeRepository

class ReconnectedApp : Application() {
    val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "screen_time_db"
        ).build()
    }
    val repository by lazy { ScreenTimeRepository(database.weeklyDao()) }
}