package com.narvatov.datingapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.os.Looper
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.narvatov.datingapp.model.local.user.Location

fun LocationRequest.getGPSLocation(
    activity: Activity,
    turnOnGPSLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
    onLocationDetectedCallback: (Location) -> Unit,
) {
    if (activity.isGPSEnabled) {
        getUserLocation(activity, onLocationDetectedCallback)
    } else {
        turnOnGPS(activity) {
            turnOnGPSLauncher.launch(IntentSenderRequest.Builder(it).build())
        }
    }
}

fun LocationRequest.turnOnGPS(
    context: Context,
    turnOnGPSCallback: (android.app.PendingIntent) -> Unit,
) {
    val locationSettingsRequest = LocationSettingsRequest.Builder().apply {
        addLocationRequest(this@turnOnGPS)
        setAlwaysShow(true)
    }.build()

    LocationServices
        .getSettingsClient(context.applicationContext)
        .checkLocationSettings(locationSettingsRequest)
        .addOnCompleteListener { task ->
            try {
                task.getResult(ApiException::class.java)
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        turnOnGPSCallback.invoke(resolvableApiException.resolution)
                    } catch (ex: IntentSender.SendIntentException) {
                        ex.printStackTrace()
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        }
}

//We already called all permission checks
@SuppressLint("MissingPermission")
fun LocationRequest.getUserLocation(
    activity: Activity,
    onLocationUpdated: (Location) -> Unit
) {
    LocationServices
        .getFusedLocationProviderClient(activity)
        .requestLocationUpdates(this, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                LocationServices
                    .getFusedLocationProviderClient(activity)
                    .removeLocationUpdates(this)

                if (locationResult.locations.isEmpty()) return

                val latitude = locationResult.locations.last().latitude
                val longitude = locationResult.locations.last().longitude

                onLocationUpdated.invoke(Location(latitude, longitude))
            }
        }, Looper.getMainLooper())
}