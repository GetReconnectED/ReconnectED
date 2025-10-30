package com.getreconnected.reconnected.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.getreconnected.reconnected.R

/**
 * Service that displays a blocking overlay window over apps that have exceeded their limit.
 * This uses SYSTEM_ALERT_WINDOW permission to show an overlay that can't be easily dismissed.
 */
class AppBlockOverlayService : Service() {
    private var windowManager: WindowManager? = null
    private var overlayView: View? = null

    companion object {
        private const val TAG = "AppBlockOverlay"
        const val EXTRA_APP_NAME = "app_name"
        const val EXTRA_LIMIT_MILLIS = "limit_millis"

        fun show(context: Context, appName: String, limitMillis: Long) {
            val intent = Intent(context, AppBlockOverlayService::class.java).apply {
                putExtra(EXTRA_APP_NAME, appName)
                putExtra(EXTRA_LIMIT_MILLIS, limitMillis)
            }
            context.startService(intent)
        }

        fun hide(context: Context) {
            val intent = Intent(context, AppBlockOverlayService::class.java)
            context.stopService(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val appName = intent?.getStringExtra(EXTRA_APP_NAME) ?: "This app"
        val limitMillis = intent?.getLongExtra(EXTRA_LIMIT_MILLIS, 0) ?: 0

        showOverlay(appName, limitMillis)

        return START_NOT_STICKY
    }

    private fun showOverlay(appName: String, limitMillis: Long) {
        // Remove any existing overlay first
        removeOverlay()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // Inflate the overlay layout
        overlayView = LayoutInflater.from(this).inflate(R.layout.app_block_overlay, null)

        // Set up the overlay view
        overlayView?.apply {
            findViewById<TextView>(R.id.blockedAppName)?.text = appName

            val limitMinutes = limitMillis / (1000 * 60)
            findViewById<TextView>(R.id.limitText)?.text =
                "You've reached your ${limitMinutes}min limit for today"

            findViewById<Button>(R.id.goHomeButton)?.setOnClickListener {
                // Send user to home screen
                val homeIntent = Intent(Intent.ACTION_MAIN).apply {
                    addCategory(Intent.CATEGORY_HOME)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(homeIntent)

                // Hide the overlay
                removeOverlay()
                stopSelf()
            }
        }

        // Set up window parameters
        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START

        try {
            windowManager?.addView(overlayView, params)
            Log.d(TAG, "Overlay displayed for $appName")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to show overlay", e)
        }
    }

    private fun removeOverlay() {
        try {
            overlayView?.let {
                windowManager?.removeView(it)
                overlayView = null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error removing overlay", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeOverlay()
    }
}
