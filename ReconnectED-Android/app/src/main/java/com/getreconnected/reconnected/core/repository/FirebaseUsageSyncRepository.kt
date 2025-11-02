package com.getreconnected.reconnected.core.repository

import android.util.Log
import com.getreconnected.reconnected.core.database.AppLimitDatabase
import com.getreconnected.reconnected.core.database.entities.AppUsage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlinx.coroutines.tasks.await

/** Repository for syncing app usage data to Firebase Firestore. */
class FirebaseUsageSyncRepository(
        private val database: AppLimitDatabase,
        private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
        private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
) {
    companion object {
        private const val TAG = "FirebaseUsageSync"
        private const val COLLECTION_USERS = "users"
        private const val COLLECTION_APP_USAGE = "appUsage"
    }

    /**
     * Upload today's app usage data to Firestore. Data is stored in:
     * users/{userId}/appUsage/{date}/{packageName}
     */
    suspend fun syncTodayUsage(usageData: Map<String, Pair<String, Long>>) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.w(TAG, "User not authenticated, skipping sync")
            return
        }

        val today = getTodayDateString()
        val batch = firestore.batch()

        try {
            usageData.forEach { (packageName, data) ->
                val (appName, usageMillis) = data

                // Save to local database
                val appUsage =
                        AppUsage(
                                id = "${today}_$packageName",
                                packageName = packageName,
                                appName = appName,
                                date = today,
                                usageMillis = usageMillis,
                        )
                database.appUsageDao().insertOrUpdate(appUsage)

                // Prepare Firestore batch update
                val docRef =
                        firestore
                                .collection(COLLECTION_USERS)
                                .document(userId)
                                .collection(COLLECTION_APP_USAGE)
                                .document(today)
                                .collection("apps")
                                .document(packageName)

                val usageMap =
                        hashMapOf(
                                "packageName" to packageName,
                                "appName" to appName,
                                "usageMillis" to usageMillis,
                                "date" to today,
                                "timestamp" to System.currentTimeMillis(),
                        )

                batch.set(docRef, usageMap)
            }

            // Commit the batch
            batch.commit().await()
            Log.d(TAG, "Successfully synced ${usageData.size} app usage records for $today")
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing usage data to Firestore", e)
        }
    }

    /** Upload a single app's usage for today. */
    suspend fun syncAppUsage(packageName: String, appName: String, usageMillis: Long) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.w(TAG, "User not authenticated, skipping sync")
            return
        }

        val today = getTodayDateString()

        try {
            // Save to local database
            val appUsage =
                    AppUsage(
                            id = "${today}_$packageName",
                            packageName = packageName,
                            appName = appName,
                            date = today,
                            usageMillis = usageMillis,
                    )
            database.appUsageDao().insertOrUpdate(appUsage)

            // Upload to Firestore
            val docRef =
                    firestore
                            .collection(COLLECTION_USERS)
                            .document(userId)
                            .collection(COLLECTION_APP_USAGE)
                            .document(today)
                            .collection("apps")
                            .document(packageName)

            val usageMap =
                    hashMapOf(
                            "packageName" to packageName,
                            "appName" to appName,
                            "usageMillis" to usageMillis,
                            "date" to today,
                            "timestamp" to System.currentTimeMillis(),
                    )

            docRef.set(usageMap).await()
            Log.d(TAG, "Synced usage for $appName: ${usageMillis}ms")
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing usage for $packageName", e)
        }
    }

    private fun getTodayDateString(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
