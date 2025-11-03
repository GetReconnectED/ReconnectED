package com.getreconnected.reconnected.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.getreconnected.reconnected.services.AppLimitMonitorService
import com.getreconnected.reconnected.ui.AppNavigation
import com.getreconnected.reconnected.ui.theme.ReconnectEDTheme

/**
 * The main activity for the app.
 */
class MainActivity : ComponentActivity() {
    // Notification permission launcher for Android 13+
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.d("MainActivity", "Notification permission granted")
                startMonitoringService()
            } else {
                Log.w("MainActivity", "Notification permission denied")
                // Service will still start, but notifications won't show
                startMonitoringService()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "Starting main activity")
        installSplashScreen() // This must be called BEFORE super.onCreate()

        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "Setting content")
        setContent { ReconnectEDTheme { AppNavigation() } }

        // Request notification permission on Android 13+ and start monitoring service
        requestNotificationPermissionAndStartService()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Note: We don't stop the service here as it should run in the background
        // The service will be stopped when the user disables monitoring or logs out
    }

    private fun requestNotificationPermissionAndStartService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startMonitoringService()
                }
                else -> {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            startMonitoringService()
        }
    }

    private fun startMonitoringService() {
        try {
            AppLimitMonitorService.start(this)
            Log.d("MainActivity", "App limit monitoring service started")
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to start monitoring service", e)
        }
    }
}
