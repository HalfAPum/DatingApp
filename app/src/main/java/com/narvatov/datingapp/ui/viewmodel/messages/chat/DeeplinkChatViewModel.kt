package com.narvatov.datingapp.ui.viewmodel.messages.chat

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.sign.SignRepository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.IProgressDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.ProgressDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class DeeplinkChatViewModel(
    private val userSessionRepository: UserSessionRepository,
    private val signRepository: SignRepository,
) : ViewModel(), IProgressDelegate by ProgressDelegate() {

    private val _chatIsReadyStateFlow = MutableStateFlow(false)
    val chatIsReadyStateFlow = _chatIsReadyStateFlow.asStateFlow()

    init {
        showProgress()

        launchCatching {
            if (userSessionRepository.user.isEmpty) {
                signRepository.signInSavedUser()
            }

            _chatIsReadyStateFlow.emit(true)

            hideProgress()
        }
    }

}