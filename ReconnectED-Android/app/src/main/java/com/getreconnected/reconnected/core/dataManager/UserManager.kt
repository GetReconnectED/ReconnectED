package com.getreconnected.reconnected.core.dataManager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.getreconnected.reconnected.core.models.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

/**
 * Manages user-related operations and data within the application.
 *
 * This object is intended to serve as a central point for handling user information,
 * providing functionalities for user management and integration with other system components.
 */
object UserManager {
    var isLoggedIn: Boolean = false
        private set
    var user: User? = null
        private set

    /**
     * Save user data to local storage.
     *
     * @param userInfo Firebase user information to be saved.
     */
    suspend fun login(userInfo: FirebaseUser) {
        val avatarBitmap: Bitmap? =
            withContext(Dispatchers.IO) {
                try {
                    userInfo.photoUrl?.let {
                        val url = URL(it.toString())
                        BitmapFactory.decodeStream(url.openStream())
                    }
                } catch (e: Exception) {
                    null
                }
            }
        user =
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
    fun logout() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        user = null
        isLoggedIn = false
    }

    /**
     * Update user information in the Firestore database.
     */
    fun update() {}

    /**
     * Remove user data from the Firestore database.
     */
    fun unregister() {}
}
