package com.narvatov.datingapp.ui.viewmodel.messages.chat

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.Dispatcher
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.ChatRepository
import com.narvatov.datingapp.data.repository.UserRepository
import com.narvatov.datingapp.data.repository.UserSessionRepository
import com.narvatov.datingapp.model.local.message.send.SendMessage
import com.narvatov.datingapp.model.local.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ChatViewModel(
    private val fiendId: String,
    userSessionRepository: UserSessionRepository,
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository,
    private val dispatcher: Dispatcher,
) : ViewModel() {

    val user: User = userSessionRepository.user

    private val _friendStateFlow = MutableStateFlow<User?>(null)
    val friendStageFlow = _friendStateFlow.asStateFlow()

    val chatMessageFlow = chatRepository
        .chatMessageFlow(user.id, fiendId)
        .flowOn(dispatcher.IO)

    init {
        getFriend()
    }

    private fun getFriend() = launchCatching {
        val friend = userRepository.getUser(fiendId)

        _friendStateFlow.emit(friend)
    }

    fun sendMessage(message: String) = launchCatching {
        chatRepository.sendMessage(SendMessage(message, user.id, fiendId))
    }

}