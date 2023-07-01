package com.narvatov.datingapp.ui

import android.Manifest
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.tasks.Task
import com.narvatov.datingapp.delegate.activity.admob.AdMobDelegate
import com.narvatov.datingapp.delegate.activity.availability.UserAvailabilityDelegate
import com.narvatov.datingapp.model.local.notification.NotificationPreference.*
import com.narvatov.datingapp.ui.common.photo.PhotoPickBottomSheet
import com.narvatov.datingapp.ui.navigation.BottomBar
import com.narvatov.datingapp.ui.navigation.NavHostContent
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.bottomSheetVisibilityEvents
import com.narvatov.datingapp.ui.navigation.showBottomBar
import com.narvatov.datingapp.ui.theme.DatingAppTheme
import com.narvatov.datingapp.ui.theme.Shapes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {

    private val userAvailabilityDelegate by UserAvailabilityDelegate()
    private val adMobDelegate by AdMobDelegate()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userAvailabilityDelegate.initialize()
        adMobDelegate.initialize()

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        setContent {
            DatingAppTheme {
                val coroutineScope = rememberCoroutineScope()

                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val shouldBottomBarBeVisible = navBackStackEntry?.destination?.route?.showBottomBar ?: false

                bottomBarState.value = shouldBottomBarBeVisible

                window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                    val insetsCompat = toWindowInsetsCompat(insets, view)
                    bottomBarState.value = shouldBottomBarBeVisible && insetsCompat.isVisible(ime()).not()
                    view.onApplyWindowInsets(insets)
                }

                val modalSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
                    skipHalfExpanded = true
                )

                BackHandler(modalSheetState.isVisible) {
                    coroutineScope.launch { modalSheetState.hide() }
                }

                LaunchedEffect("bottomSheetVisibilityEvents") {
                    bottomSheetVisibilityEvents.collectLatest { isBottomSheetVisible ->
                        if (isBottomSheetVisible) {
                            modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                        } else {
                            modalSheetState.hide()
                        }
                    }
                }

                val locationPermissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    println("FUCK locationPermissionLauncher $isGranted")

                    getCurrentLocation()
                }

                val turnOnGPSLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult()
                ) {
                    getCurrentLocation()
                }

                val activityViewModelStoreOwner: ViewModelStoreOwner = this

                ModalBottomSheetLayout(
                    sheetState = modalSheetState,
                    sheetShape = Shapes.large.copy(bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp)),
                    sheetContent = { PhotoPickBottomSheet(getViewModel(owner = activityViewModelStoreOwner), modalSheetState) }
                ) {
                    Scaffold(
                        navController = navController,
                        bottomBar = { BottomBar(navController, bottomBarState.value) },
                        content = { _, innerPadding ->
                            NavHostContent(navController, activityViewModelStoreOwner, innerPadding)
                        }
                    )

//                    Button(
//                        {
//                            getCurrentLocation(locationPermissionLauncher, turnOnGPSLauncher)
//                        }
//                    ) {
//                        Text(
//                            text = "permission"
//                        )
//                    }
                }
            }
        }
    }

    lateinit var locationRequest: LocationRequest
    private fun getCurrentLocation(
        locationPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>? = null,
        turnOnGPSLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>? = null
    ) {
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            println("FUCK WTF ${isGPSEnabled()}")
            if (isGPSEnabled()) {
                LocationServices.getFusedLocationProviderClient(this@MainActivity)
                    .requestLocationUpdates(locationRequest, object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            super.onLocationResult(locationResult)
                            println("FUCK LOCATION RESULT WTF1 ${locationResult}")
                            println("FUCK LOCATION RESULT WTF2 ${locationResult.locations.toList()}")
                            println("FUCK LOCATION RESULT WTF3 ${locationResult.lastLocation}")
                            LocationServices.getFusedLocationProviderClient(this@MainActivity)
                                .removeLocationUpdates(this)
                            println("FUCK 4 wtf ${locationResult.locations.size}")
                            if (locationResult.locations.size > 0) {
                                println("FUCK 5 wtf ${locationResult.locations.last().latitude}")

                                val latitude = locationResult.locations.last().latitude
                                val longitude = locationResult.locations.last().longitude
                                runOnUiThread {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Latitude: $latitude\nLongitude: $longitude",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }, Looper.getMainLooper())
            } else {
                turnOnGPS(turnOnGPSLauncher)
            }
        } else {
            locationPermissionLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun turnOnGPS(turnOnGPSLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>?) {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(
            applicationContext
        )
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener { task ->
            try {
                task.getResult(ApiException::class.java)
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        turnOnGPSLauncher?.launch(IntentSenderRequest.Builder(resolvableApiException.resolution)
                            .build())
                    } catch (ex: SendIntentException) {
                        ex.printStackTrace()
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        }
    }

    private fun isGPSEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


}