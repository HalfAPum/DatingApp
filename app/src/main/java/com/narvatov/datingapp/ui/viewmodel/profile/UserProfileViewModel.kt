package com.narvatov.datingapp.ui.viewmodel.profile

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.profile.UserProfileRepository
import com.narvatov.datingapp.ui.navigation.SignIn
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UserProfileViewModel(
    private val userProfileRepository: UserProfileRepository,
) : ViewModel() {

    val userFlow = userProfileRepository.userFlow

    fun logout() = launchCatching {
        userProfileRepository.logout()

        navigate(SignIn, clearBackStack = true)
    }

    fun deleteAccount() = launchCatching {
        userProfileRepository.deleteAccount()

        navigate(SignIn, clearBackStack = true)
    }

}