package com.suhov.locationinbackground

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.hasLocationPermission(): Boolean {
    val coarseLocationIsGranted = ContextCompat.checkSelfPermission(
        /* context = */ this,
        /* permission = */ Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val fineLocationIsGranted = ContextCompat.checkSelfPermission(
        /* context = */ this,
        /* permission = */ Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    return coarseLocationIsGranted && fineLocationIsGranted
}