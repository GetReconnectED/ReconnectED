package com.getreconnected.reconnected.core.models.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

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
