package com.suhov.locationinbackground

object MainScreen {
    const val startButton = "Start"
    const val stopButton = "Stop"
}

object LocService {
    const val chanelID = "location"
    const val contentTitle = "Tracking location..."
    const val contentDefaultText = "Location: null"
    const val logTag = "LocationService"
    const val contentText = "Location: (%s, %s)"
    const val contentErrorText = "Error - %s"
    const val ACTION_START = "ACTION_START"
    const val ACTION_STOP = "ACTION_STOP"
}

object Notification {
    const val chanelID = LocService.chanelID
    const val chanelName = "Location"
}

object LocExceptions {
    const val locationPermissionsAreMissing = "Missing location permission"
    const val GPSIsDisabled = "GPS is disabled"
}