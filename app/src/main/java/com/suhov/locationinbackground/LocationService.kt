package com.suhov.locationinbackground

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService: Service() {

    private lateinit var locationClient: LocationClient
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val longCoordinates = 7
    private val updateInterval = 5000L
    private val notificationID = 1

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            LocService.ACTION_START -> start()
            LocService.ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, LocService.chanelID)
            .setContentTitle(LocService.contentTitle)
            .setContentText(LocService.contentDefaultText)
            .setSmallIcon(android.R.drawable.ic_input_add)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        locationClient
            .getLocationUpdates(updateInterval)
            .catch { e ->
                e.printStackTrace()
                val error = e as? LocationClient.LocationException ?: return@catch
                val errorMessageForUser = String.format(LocService.contentErrorText, error.message)
                val updatedNotification = notification.setContentText( errorMessageForUser )
                notificationManager.notify(notificationID, updatedNotification.build())
                delay(updateInterval)
                start()
                Log.e(LocService.logTag, error.message)
            }
            .onEach { location ->
                val lat = location.latitude.toString().take(longCoordinates)
                val lon = location.longitude.toString().take(longCoordinates)
                val contentText = String.format(LocService.contentText, lat, lon)
                val updatedNotification = notification.setContentText( contentText )
                notificationManager.notify(notificationID, updatedNotification.build())
            }
            .launchIn(serviceScope)

        startForeground(notificationID, notification.build())
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}