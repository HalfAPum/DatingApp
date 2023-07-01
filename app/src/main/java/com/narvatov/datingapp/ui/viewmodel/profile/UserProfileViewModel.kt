package com.narvatov.datingapp.ui.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.profile.UserProfileRepository
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.ui.navigation.SignIn
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.IProgressDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.ProgressDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UserProfileViewModel(
    private val userProfileRepository: UserProfileRepository,
) : ViewModel(), IProgressDelegate by ProgressDelegate() {

    private val _userFlow = MutableStateFlow(User.emptyUser)
    val userFlow = _userFlow.asStateFlow()

    private val collectUserUpdatesJob = userProfileRepository
        .userFlow
        .onEach { _userFlow.emit(it) }
        .launchIn(viewModelScope)

    fun logout() = launchCatching {
        collectUserUpdatesJob.cancel()
        showProgress()

        userProfileRepository.logout()

        navigate(SignIn, clearBackStack = true)
    }

    fun deleteAccount() = launchCatching {
        collectUserUpdatesJob.cancel()
        showProgress()

        userProfileRepository.deleteAccount()

        navigate(SignIn, clearBackStack = true)
    }

}