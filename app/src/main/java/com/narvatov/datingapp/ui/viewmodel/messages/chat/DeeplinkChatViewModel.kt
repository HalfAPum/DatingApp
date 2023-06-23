package com.narvatov.datingapp.ui.viewmodel.messages.chat

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.sign.SignRepository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DeeplinkChatViewModel(
    private val userSessionRepository: UserSessionRepository,
    private val signRepository: SignRepository,
) : ViewModel() {

    private val _chatIsReadyStateFlow = MutableStateFlow(false)
    val chatIsReadyStateFlow = _chatIsReadyStateFlow.asStateFlow()

    init {
        launchCatching {
            if (userSessionRepository.user.isEmpty) {
                signRepository.signInSavedUser()
            }

            _chatIsReadyStateFlow.emit(true)

        }
    }

}