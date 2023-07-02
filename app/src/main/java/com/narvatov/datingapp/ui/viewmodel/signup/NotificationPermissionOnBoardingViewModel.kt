package com.narvatov.datingapp.ui.viewmodel.signup

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.preference.NotificationPreferencesDataStore
import com.narvatov.datingapp.model.local.notification.PermissionPreference
import com.narvatov.datingapp.ui.navigation.OnBoardingFlow
import com.narvatov.datingapp.ui.navigation.OnBoardingManager
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NotificationPermissionOnBoardingViewModel(
    private val notificationPreferenceDataStore: NotificationPreferencesDataStore,
    private val onBoardingManager: OnBoardingManager,
) : ViewModel() {

    suspend fun saveNotificationPreference(
        notificationPreference: PermissionPreference
    ) = notificationPreferenceDataStore.save(notificationPreference)

    fun processOnBoarding(ignoreNotificationOnBoarding: Boolean = false) = launchCatching {
        if (ignoreNotificationOnBoarding) {
            onBoardingManager.addIgnoreOnBoardingDestination(
                onBoardingFlow = OnBoardingFlow.NotificationPermissionOnBoarding
            )
        }

        onBoardingManager.processOnBoarding()
    }

}