package com.narvatov.datingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UserAvailabilityViewModel(
    private val userSessionRepository: UserSessionRepository,
) : ViewModel() {

    var isUserAvailable: Boolean = false
        set(value) {
            field = value

            launchCatching {
                userSessionRepository.updateUserAvailability(field)
            }
        }

}