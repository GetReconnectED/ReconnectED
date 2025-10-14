package com.getreconnected.reconnected.core.models.entities

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

/**
 * Represents a user entity in the system.
 *
 * This class defines the main user attributes and maps them to a database entity using Room annotations.
 *
 * @property uid Unique identifier for the user.
 * @property firstName Optional first name of the user.
 * @property lastName Optional last name of the user.
 * @property email Email address of the user.
 * @property created Timestamp indicating when the user was created.
 * @property lastSignIn Timestamp indicating the most recent login time.
 * @property avatar Optional avatar image for the user, ignored for database persistence.
 */
@Entity
data class User(
    @PrimaryKey val uid: String,
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val created: Int,
    val lastSignIn: Int,
    @Ignore val avatar: Bitmap? = null,
)

/**
 * Data Access Object (DAO) interface for managing User entities in the database.
 *
 * Provides methods to perform operations such as retrieving all users, finding a user by their unique identifier,
 * and deleting a user record from the database.
 *
 * This interface uses Room annotations to define the corresponding SQL queries for database interactions.
 */
@Dao
interface UserDao {
    /**
     * Retrieves all user records from the database.
     *
     * @return A list of all users as entities of type User.
     */
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    /**
     * Retrieves a user record from the database by their unique identifier (UID).
     *
     * @param uid The unique identifier of the user to be found.
     * @return The user entity matching the specified UID, or null if no user is found.
     */
    @Query("SELECT * FROM user WHERE uid = :uid LIMIT 1")
    fun findByUid(uid: String): User?

    /**
     * Deletes the specified user from the database.
     *
     * @param user The user entity to be deleted.
     */
    @Delete
    fun delete(user: User)
}
