package com.getreconnected.reconnected.core.models.entities

import android.graphics.Bitmap

/**
 * Represents a user entity in the system.
 *
 * @property displayName Display name of the user.
 * @property email Email address of the user.
 * @property created Timestamp indicating when the user was created.
 * @property lastSignIn Timestamp indicating the most recent login time.
 * @property avatar Optional avatar image for the user, ignored for database persistence.
 */
data class User(
    val displayName: String? = null,
    val email: String,
    val created: Int,
    val lastSignIn: Int,
    val avatar: Bitmap? = null,
)
