package com.narvatov.datingapp.ui.screen.signup

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.tasks.Task
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.signup.LocationPermissionOnBoardingViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun LocationPermissionOnBoarding(
    viewModel: LocationPermissionOnBoardingViewModel = getViewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        println("FUCK locationPermissionLauncher $isGranted")

        getCurrentLocation(activity, viewModel)
    }

    val turnOnGPSLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        getCurrentLocation(activity, viewModel)
    }

    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        var text by remember { mutableStateOf("Hello allow us to get your location") }

        Text(
            text = text,
            style = Typography.h4,
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = {
                getCurrentLocation(activity, viewModel, locationPermissionLauncher, turnOnGPSLauncher)
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(
                text = "Permission"
            )
        }

        Button(
            onClick = { viewModel.processOnBoarding(ignoreLocationOnBoarding = true) },
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = "Skip"
            )
        }
    }
}

private fun getCurrentLocation(
    activity: Activity,
    viewModel: LocationPermissionOnBoardingViewModel,
    locationPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>? = null,
    turnOnGPSLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>? = null,
) {
    if (ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        println("FUCK WTF ${isGPSEnabled(activity)}")
        if (isGPSEnabled(activity)) {
            LocationServices.getFusedLocationProviderClient(activity)
                .requestLocationUpdates(viewModel.locationRequest, object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        println("FUCK LOCATION RESULT WTF1 ${locationResult}")
                        println("FUCK LOCATION RESULT WTF2 ${locationResult.locations.toList()}")
                        println("FUCK LOCATION RESULT WTF3 ${locationResult.lastLocation}")
                        LocationServices.getFusedLocationProviderClient(activity)
                            .removeLocationUpdates(this)
                        println("FUCK 4 wtf ${locationResult.locations.size}")
                        if (locationResult.locations.size > 0) {
                            println("FUCK 5 wtf ${locationResult.locations.last().latitude}")

                            val latitude = locationResult.locations.last().latitude
                            val longitude = locationResult.locations.last().longitude
                            activity.runOnUiThread {
                                viewModel.processOnBoarding()
                                Toast.makeText(
                                    activity,
                                    "Latitude: $latitude\nLongitude: $longitude",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }, Looper.getMainLooper())
        } else {
            turnOnGPS(activity, viewModel, turnOnGPSLauncher)
        }
    } else {
        locationPermissionLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

private fun turnOnGPS(
    activity: Activity,
    viewModel: LocationPermissionOnBoardingViewModel,
    turnOnGPSLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>?
) {
    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(viewModel.locationRequest)
    builder.setAlwaysShow(true)
    val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(
        activity.applicationContext
    )
        .checkLocationSettings(builder.build())
    result.addOnCompleteListener { task ->
        try {
            task.getResult(ApiException::class.java)
        } catch (e: ApiException) {
            when (e.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                    val resolvableApiException = e as ResolvableApiException
                    turnOnGPSLauncher?.launch(
                        IntentSenderRequest.Builder(resolvableApiException.resolution)
                        .build())
                } catch (ex: IntentSender.SendIntentException) {
                    ex.printStackTrace()
                }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
            }
        }
    }
}

private fun isGPSEnabled(activity: Activity): Boolean {
    val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}