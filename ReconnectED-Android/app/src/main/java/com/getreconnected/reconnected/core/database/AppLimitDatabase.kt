package com.getreconnected.reconnected.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.getreconnected.reconnected.core.database.dao.AppLimitDao
import com.getreconnected.reconnected.core.database.dao.InstalledAppDao
import com.getreconnected.reconnected.core.database.entities.AppLimit
import com.getreconnected.reconnected.core.database.entities.InstalledApp

/**
 * Room database for storing app limits and installed apps.
 *
 * This database contains two tables:
 * 1. installed_apps - Stores information about all installed applications
 * 2. app_limits - Stores user-defined time limits for specific applications
 */
@Database(
    entities = [InstalledApp::class, AppLimit::class],
    version = 1,
    exportSchema = false,
)
abstract class AppLimitDatabase : RoomDatabase() {
    abstract fun installedAppDao(): InstalledAppDao

    abstract fun appLimitDao(): AppLimitDao

    companion object {
        @Volatile
        private var INSTANCE: AppLimitDatabase? = null

        /**
         * Get the singleton instance of the database.
         *
         * @param context Application context
         * @return The database instance
         */
        fun getDatabase(context: Context): AppLimitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppLimitDatabase::class.java,
                            "app_limit_database",
                        ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
