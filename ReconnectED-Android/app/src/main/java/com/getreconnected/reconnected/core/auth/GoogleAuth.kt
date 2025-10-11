package com.getreconnected.reconnected.core.auth

import android.content.Intent
import android.util.Log
import com.firebase.ui.auth.AuthUI

class GoogleAuth {
    /**
     * Returns an instance of [AuthUI] for Google authentication.
     */
    fun getGoogleAuthInstance(): AuthUI {
        Log.d("GoogleAuth", "getGoogleAuthInstance called")
        return AuthUI.getInstance()
    }

    /**
     * Shows the login screen for Google authentication.
     */
    fun showLogin(): Intent {
        Log.d("GoogleAuth", "showLogin called")
        return this
            .getGoogleAuthInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
            .setDefaultProvider(AuthUI.IdpConfig.GoogleBuilder().build())
            .setCredentialManagerEnabled(false)
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
