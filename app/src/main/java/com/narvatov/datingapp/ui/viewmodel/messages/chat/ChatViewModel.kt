package com.narvatov.datingapp.ui.viewmodel.messages.chat

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.UserRepository
import com.narvatov.datingapp.data.repository.UserSessionRepository
import com.narvatov.datingapp.model.local.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ChatViewModel(
    private val userId: String,
    private val userSessionRepository: UserSessionRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _friendStateFlow = MutableStateFlow<User?>(null)
    val friendStageFlow = _friendStateFlow.asStateFlow()

    init {
        getFriend()
    }

    fun getUser() = userSessionRepository.user

    fun getFriend() = launchCatching {
        val friend = userRepository.getUser(userId)

        _friendStateFlow.emit(friend)
    }

}