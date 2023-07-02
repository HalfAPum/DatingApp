package com.narvatov.datingapp.ui.screen.onboarding

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
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
import com.narvatov.datingapp.model.local.user.Location
import com.narvatov.datingapp.ui.common.LoaderBox
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.onborading.LocationPermissionOnBoardingViewModel
import com.narvatov.datingapp.utils.getGPSLocation
import com.narvatov.datingapp.utils.getUserLocation
import org.koin.androidx.compose.getViewModel

@Composable
fun LocationPermissionOnBoarding(
    viewModel: LocationPermissionOnBoardingViewModel = getViewModel()
) = LoaderBox(viewModel) {
    val context = LocalContext.current
    val activity = context as Activity

    val locationRequest = viewModel.locationRequest

    val onLocationDetectedCallback: (Location) -> Unit = {
        viewModel.locationDetected(it)
    }

    val turnOnGPSLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) {
        locationRequest.getUserLocation(activity, onLocationDetectedCallback)
    }

    var text by remember { mutableStateOf("Hello allow us to get your location") }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        text = when {
            isGranted -> {
                locationRequest.getGPSLocation(
                    activity = activity,
                    turnOnGPSLauncher = turnOnGPSLauncher,
                    onLocationDetectedCallback = onLocationDetectedCallback,
                )
                viewModel.permissionGranted()
                "Location permission granted"
            }
            activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                viewModel.permissionDeniedShowRationale()
                "Location permission denied show rationale."
            }
            else -> {
                viewModel.permissionDenied()
                "Location permission denied."
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text(
            text = text,
            style = Typography.h4,
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = {
                if (ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    locationRequest.getGPSLocation(
                        activity = activity,
                        turnOnGPSLauncher = turnOnGPSLauncher,
                        onLocationDetectedCallback = onLocationDetectedCallback,
                    )
                    viewModel.permissionGranted()
                } else {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(
                text = "Permission"
            )
        }

        Button(
            onClick = { viewModel.processOnBoarding(ignore = true) },
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = "Skip"
            )
        }
    }
}