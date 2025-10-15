package com.getreconnected.reconnected.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.getreconnected.reconnected.core.models.entities.User
import com.getreconnected.reconnected.core.models.entities.UserDao

/**
 * Abstract class for managing the application's database using Room.
 *
 * This class serves as the main access point to the database. It defines the entities associated
 * with the database and the version of the database schema. It also provides an abstract method
 * to access the DAO (Data Access Object) for interacting with the `User` entity.
 *
 * Annotations:
 * - `@Database` specifies the list of entities (`User`) related to this database and the schema version.
 */
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

/**
 * Responsible for managing a single instance of the `AppDatabase` using the Singleton pattern.
 *
 * This class ensures that the database is initialized only once and provides a synchronized method
 * to access the instance of the `AppDatabase`. It uses Room to build the database instance and serves
 * as the central access point for the application's database.
 *
 * Thread Safety:
 * The database instance is fetched or created in a thread-safe manner by using a synchronized block
 * and the `@Volatile` annotation to ensure visibility and consistency across threads.
 *
 */
class DatabaseManager private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Retrieves the singleton instance of the `AppDatabase`.
         *
         * @param context The application context.
         * @return The singleton instance of the `AppDatabase`.
         */
        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "reconnected",
                        ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
