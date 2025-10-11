package com.getreconnected.reconnected.legacy.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [WeeklyScreenTime::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weeklyDao(): WeeklyScreenTimeDao
}
