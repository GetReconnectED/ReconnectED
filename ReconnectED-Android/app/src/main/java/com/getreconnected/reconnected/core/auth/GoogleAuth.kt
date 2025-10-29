package com.getreconnected.reconnected.core.auth

import android.content.Intent
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

/**
 * A class responsible for managing Google authentication using Firebase AuthUI.
 */
class GoogleAuth {
    val currentUser get() = Firebase.auth.currentUser

    /**
     * Returns an instance of [AuthUI] for Google authentication.
     *
     * @return An instance of [AuthUI].
     */
    fun getGoogleAuthInstance(): AuthUI {
        Log.d("GoogleAuth", "getGoogleAuthInstance called")
        return AuthUI.getInstance()
    }

    /**
     * Shows the login screen for Google authentication.
     *
     * @return An [Intent] to start the login activity.
     */
    fun showLogin(): Intent {
        Log.d("GoogleAuth", "showLogin called")
        return this
            .getGoogleAuthInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
            .setDefaultProvider(AuthUI.IdpConfig.GoogleBuilder().build())
            // .setCredentialManagerEnabled(false)
            // FIXME: `.setCredentialManagerEnabled(false)` is a temporary fix for the following error:
            //
            // A sign-in error occurred.
            // com.firebase.ui.auth.FirebaseUiException: Invalid FirebaseUser or missing email/password.
            // at com.firebase.ui.auth.viewmodel.credentialmanager.CredentialManagerHandler
            //     .saveCredentials(CredentialManagerHandler.kt:52)
            // at com.firebase.ui.auth.ui.credentials.CredentialSaveActivity.onCreate(CredentialSaveActivity.kt:62)
            .build()
    }
}
