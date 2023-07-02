package com.narvatov.datingapp.ui.viewmodel.onborading

import com.halfapum.general.coroutines.launch
import com.narvatov.datingapp.data.preference.permission.NotificationPreferencesDataStore
import com.narvatov.datingapp.model.local.notification.PermissionPreference
import com.narvatov.datingapp.ui.navigation.OnBoardingFlow
import com.narvatov.datingapp.ui.navigation.OnBoardingManager
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NotificationPermissionOnBoardingViewModel(
    override val permissionPreferenceDataStore: NotificationPreferencesDataStore,
    override val onBoardingManager: OnBoardingManager,
) : PermissionOnBoardingViewModel() {

    override val currentDestination = OnBoardingFlow.NotificationPermissionOnBoarding

    fun permissionGranted() = launch {
        permissionPreferenceDataStore.save(PermissionPreference.GRANTED)
        processOnBoarding()
    }

}