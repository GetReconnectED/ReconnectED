package com.getreconnected.reconnected.core.auth

import androidx.credentials.GetCredentialRequest
import com.getreconnected.reconnected.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption

class GoogleAuth {
    fun signIn() {
        // Instantiate a Google sign-in request
        val googleIdOption = GetGoogleIdOption.Builder()
            // Your server's client ID, not your Android client ID.
            .setServerClientId(R.string.default_web_client_id.toString())
            // Only show accounts previously used to sign in.
            .setFilterByAuthorizedAccounts(true)
            .build()

        // Create the Credential Manager request
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }
}
