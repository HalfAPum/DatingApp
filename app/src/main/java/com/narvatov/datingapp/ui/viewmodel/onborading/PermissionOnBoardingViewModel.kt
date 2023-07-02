package com.narvatov.datingapp.ui.viewmodel.onborading

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launch
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.preference.permission.PermissionPreferenceDataStore
import com.narvatov.datingapp.model.local.notification.PermissionPreference
import com.narvatov.datingapp.ui.navigation.OnBoardingFlow
import com.narvatov.datingapp.ui.navigation.OnBoardingManager

abstract class PermissionOnBoardingViewModel : ViewModel() {

    abstract val onBoardingManager: OnBoardingManager

    abstract val permissionPreferenceDataStore: PermissionPreferenceDataStore

    abstract val currentDestination: OnBoardingFlow

    fun permissionDenied() = launch {
        permissionPreferenceDataStore.save(PermissionPreference.DENIED)
        processOnBoarding()
    }

    fun permissionDeniedShowRationale() = launch {
        permissionPreferenceDataStore.save(PermissionPreference.SHOW_RATIONALE)
    }

    fun processOnBoarding(ignore: Boolean = false) = launchCatching {
        if (ignore) {
            onBoardingManager.addIgnoreOnBoardingDestination(
                onBoardingFlow = currentDestination
            )
        }

        onBoardingManager.processOnBoarding()
    }

}