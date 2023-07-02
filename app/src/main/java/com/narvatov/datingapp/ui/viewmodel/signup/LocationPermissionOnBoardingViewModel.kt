package com.narvatov.datingapp.ui.viewmodel.signup

import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationRequest
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.ui.navigation.OnBoardingFlow
import com.narvatov.datingapp.ui.navigation.OnBoardingManager
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LocationPermissionOnBoardingViewModel(
    private val userSessionRepository: UserSessionRepository,
    private val onBoardingManager: OnBoardingManager,
) : ViewModel() {

    val locationRequest = LocationRequest.create().apply {
        setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        setInterval(5000)
        setFastestInterval(2000)
    }

    fun updateUserLocation() {

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