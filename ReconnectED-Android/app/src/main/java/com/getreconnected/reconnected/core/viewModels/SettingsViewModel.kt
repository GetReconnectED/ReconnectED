package com.getreconnected.reconnected.core.viewModels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.getreconnected.reconnected.activities.MainActivity
import com.getreconnected.reconnected.core.database.AppLimitDatabase
import com.getreconnected.reconnected.services.AppLimitMonitorService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SettingsViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        private const val TAG = "SettingsViewModel"
    }

    fun syncNow() {
        Log.d(TAG, "syncNow: User initiated manual sync")
        AppLimitMonitorService.syncNow()
        Log.d(TAG, "syncNow: Sync request sent to service")
    }

    suspend fun deleteAccount(context: Context) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.w(TAG, "deleteAccount: No user authenticated, cannot delete account")
            return
        }

        Log.i(
                TAG,
                "deleteAccount: Starting account deletion process (uid length: ${userId.length})"
        )

        try {
            // Delete all Firestore data
            Log.d(TAG, "deleteAccount: Step 1 - Deleting Firestore data")
            deleteUserDataFromFirestore(userId)
            Log.i(TAG, "deleteAccount: ✓ Firestore data deleted")

            // Clear local database
            Log.d(TAG, "deleteAccount: Step 2 - Clearing local database")
            val database = AppLimitDatabase.getDatabase(context)
            withContext(Dispatchers.IO) { database.clearAllTables() }
            Log.i(TAG, "deleteAccount: ✓ Local database cleared")

            // Delete Firebase Auth account
            Log.d(TAG, "deleteAccount: Step 3 - Deleting Firebase Auth account")
            try {
                auth.currentUser?.delete()?.await()
                Log.i(TAG, "deleteAccount: ✓ Firebase Auth account deleted")
            } catch (e: FirebaseAuthRecentLoginRequiredException) {
                Log.w(TAG, "deleteAccount: Recent authentication required for account deletion")
                Log.w(
                        TAG,
                        "deleteAccount: Skipping Firebase Auth account deletion - user will need to log in and delete again"
                )
                // Continue with sign out - data is already deleted
            }

            // Sign out
            auth.signOut()
            Log.d(TAG, "deleteAccount: Signed out from Firebase")

            // Navigate back to login
            Log.d(TAG, "deleteAccount: Navigating to login screen")
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
            Log.i(TAG, "deleteAccount: ✓ Account deletion completed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "deleteAccount: ✗ Account deletion failed: ${e.message}", e)
            throw Exception("Failed to delete account: ${e.message}", e)
        }
    }

    private suspend fun deleteUserDataFromFirestore(userId: String) {
        try {
            Log.d(TAG, "deleteUserDataFromFirestore: Starting Firestore deletion")

            // Delete all app usage data
            val usageRef = firestore.collection("users").document(userId).collection("appUsage")
            Log.v(TAG, "deleteUserDataFromFirestore: Querying appUsage collection")

            val usageDocs = usageRef.get().await()
            Log.d(
                    TAG,
                    "deleteUserDataFromFirestore: Found ${usageDocs.size()} date documents to delete"
            )

            val batch = firestore.batch()
            var totalAppDocsDeleted = 0

            for (dateDoc in usageDocs.documents) {
                Log.v(TAG, "deleteUserDataFromFirestore: Processing date: ${dateDoc.id}")

                // Delete all apps subcollection for each date
                val appsRef = dateDoc.reference.collection("apps")
                val appDocs = appsRef.get().await()
                Log.v(
                        TAG,
                        "deleteUserDataFromFirestore: Found ${appDocs.size()} app documents for date ${dateDoc.id}"
                )

                for (appDoc in appDocs.documents) {
                    batch.delete(appDoc.reference)
                    totalAppDocsDeleted++
                }

                // Delete the date document
                batch.delete(dateDoc.reference)
            }

            Log.d(
                    TAG,
                    "deleteUserDataFromFirestore: Deleting ${totalAppDocsDeleted} app documents and ${usageDocs.size()} date documents"
            )

            // Delete the user document
            batch.delete(firestore.collection("users").document(userId))
            Log.d(TAG, "deleteUserDataFromFirestore: Added user document to deletion batch")

            // Commit all deletes
            Log.d(TAG, "deleteUserDataFromFirestore: Committing batch deletion")
            batch.commit().await()
            Log.i(TAG, "deleteUserDataFromFirestore: ✓ Successfully deleted all Firestore data")
        } catch (e: Exception) {
            Log.e(
                    TAG,
                    "deleteUserDataFromFirestore: ✗ Failed to delete Firestore data: ${e.message}",
                    e
            )
            throw Exception("Failed to delete Firestore data: ${e.message}", e)
        }
    }
}
