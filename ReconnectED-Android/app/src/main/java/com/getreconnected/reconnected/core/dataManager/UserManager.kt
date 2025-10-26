package com.getreconnected.reconnected.core.dataManager

import android.graphics.Bitmap
import com.getreconnected.reconnected.core.models.entities.User
import com.google.firebase.auth.FirebaseUser

/**
 * Manages user-related operations and data within the application.
 *
 * This object is intended to serve as a central point for handling user information,
 * providing functionalities for user management and integration with other system components.
 */
object UserManager {
    var isLoggedIn: Boolean = false
        private set

    /**
     * Save user data to local storage.
     *
     * @param userInfo Firebase user information to be saved.
     */
    fun login(userInfo: FirebaseUser) {
        val avatarBitmap: Bitmap? = null
        User(
            displayName = userInfo.displayName ?: "",
            email = userInfo.email ?: "",
            avatar = avatarBitmap,
            created = 0,
            lastSignIn = 0,
        )
        isLoggedIn = true
    }

    /**
     * Remove locally-saved information about the user.
     */
    fun logout() {}

    /**
     * Update user information in the Firestore database.
     */
    fun update() {}

    /**
     * Remove user data from the Firestore database.
     */
    fun unregister() {}
}
