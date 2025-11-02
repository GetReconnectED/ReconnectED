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
        Log.d(TAG, "syncTodayUsage: Starting sync for ${usageData.size} apps")

        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.w(TAG, "syncTodayUsage: User not authenticated, skipping sync")
            return
        }
        Log.d(TAG, "syncTodayUsage: Authenticated user found (uid length: ${userId.length})")

        val today = getTodayDateString()
        Log.d(TAG, "syncTodayUsage: Syncing data for date: $today")

        val batch = firestore.batch()
        Log.d(TAG, "syncTodayUsage: Created Firestore batch")

        try {
            var processedCount = 0
            usageData.forEach { (packageName, data) ->
                val (appName, usageMillis) = data
                Log.v(
                        TAG,
                        "syncTodayUsage: Processing app #${processedCount + 1}: $appName (${usageMillis}ms)"
                )

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
                Log.v(TAG, "syncTodayUsage: Saved to local DB: $appName")

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
                Log.v(TAG, "syncTodayUsage: Added to batch: $appName")
                processedCount++
            }

            // Commit the batch
            Log.d(TAG, "syncTodayUsage: Committing batch with $processedCount apps to Firestore")
            batch.commit().await()
            Log.i(
                    TAG,
                    "syncTodayUsage: ✓ Successfully synced ${usageData.size} app usage records for $today"
            )
        } catch (e: Exception) {
            Log.e(TAG, "syncTodayUsage: ✗ Error syncing usage data to Firestore: ${e.message}", e)
        }
    }

    /** Upload a single app's usage for today. */
    suspend fun syncAppUsage(packageName: String, appName: String, usageMillis: Long) {
        Log.d(TAG, "syncAppUsage: Syncing single app: $appName (${usageMillis}ms)")

        val userId = auth.currentUser?.uid
        if (userId == null) {
            Log.w(TAG, "syncAppUsage: User not authenticated, skipping sync")
            return
        }

        val today = getTodayDateString()
        Log.v(TAG, "syncAppUsage: Date: $today")

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

            Log.v(TAG, "syncAppUsage: Writing to Firestore...")
            docRef.set(usageMap).await()
            Log.i(TAG, "syncAppUsage: ✓ Synced usage for $appName: ${usageMillis}ms")
        } catch (e: Exception) {
            Log.e(TAG, "syncAppUsage: ✗ Error syncing usage for $packageName: ${e.message}", e)
        }
    }

    private fun getTodayDateString(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}
