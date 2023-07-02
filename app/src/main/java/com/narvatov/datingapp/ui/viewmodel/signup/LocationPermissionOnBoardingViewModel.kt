package com.narvatov.datingapp.ui.viewmodel.signup

import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.Location
import com.narvatov.datingapp.ui.navigation.OnBoardingFlow
import com.narvatov.datingapp.ui.navigation.OnBoardingManager
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.IProgressDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.ProgressDelegate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LocationPermissionOnBoardingViewModel(
    private val userSessionRepository: UserSessionRepository,
    private val onBoardingManager: OnBoardingManager,
) : ViewModel(), IProgressDelegate by ProgressDelegate() {

    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        5000
    ).apply {
        setMinUpdateIntervalMillis(2000)
    }.build()

    fun permissionGranted() {
        showProgress()
    }

    fun locationDetected(location: Location) {

        //Todo temp
        processOnBoarding(ignoreLocationOnBoarding = true)
    }

    fun processOnBoarding(ignoreLocationOnBoarding: Boolean = false) = launchCatching {
        if (ignoreLocationOnBoarding) {
            onBoardingManager.addIgnoreOnBoardingDestination(
                onBoardingFlow = OnBoardingFlow.LocationPermissionOnBoarding
            )
        }

        onBoardingManager.processOnBoarding()
    }


}