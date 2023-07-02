package com.narvatov.datingapp.ui.viewmodel.onborading

import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.halfapum.general.coroutines.launch
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.preference.permission.LocationPreferencesDataStore
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.notification.PermissionPreference
import com.narvatov.datingapp.model.local.user.Location
import com.narvatov.datingapp.ui.navigation.OnBoardingFlow
import com.narvatov.datingapp.ui.navigation.OnBoardingManager
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.IProgressDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.ProgressDelegate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LocationPermissionOnBoardingViewModel(
    private val userSessionRepository: UserSessionRepository,
    override val onBoardingManager: OnBoardingManager,
    override val permissionPreferenceDataStore: LocationPreferencesDataStore,
) : PermissionOnBoardingViewModel(), IProgressDelegate by ProgressDelegate() {

    override val currentDestination = OnBoardingFlow.LocationPermissionOnBoarding

    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        5000
    ).apply {
        setMinUpdateIntervalMillis(2000)
    }.build()

    fun permissionGranted() = launch {
        permissionPreferenceDataStore.save(PermissionPreference.GRANTED)
        showProgress()
    }

    fun locationDetected(location: Location) = launchCatching {
        userSessionRepository.updateUserLocation(location)

        processOnBoarding()
    }

}