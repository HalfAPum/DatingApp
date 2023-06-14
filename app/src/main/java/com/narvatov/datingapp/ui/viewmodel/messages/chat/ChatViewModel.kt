package com.narvatov.datingapp.ui.viewmodel.messages.chat

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.Dispatcher
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.ChatRepository
import com.narvatov.datingapp.data.repository.UserRepository
import com.narvatov.datingapp.model.local.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import org.koin.android.annotation.KoinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

@KoinViewModel
class ChatViewModel(
    private val friendId: String,
    private val userRepository: UserRepository,
    dispatcher: Dispatcher,
) : ViewModel() {

    private val chatRepository by inject<ChatRepository>(
        ChatRepository::class.java,
        parameters = { parametersOf(friendId) },
    )

    private val _friendStateFlow = MutableStateFlow<User?>(null)
    val friendStageFlow = _friendStateFlow.asStateFlow()

    val chatMessageFlow = chatRepository.chatMessageFlow.flowOn(dispatcher.IO)

    init {
        getFriend()
    }

    private fun getFriend() = launchCatching {
        val friend = userRepository.getUser(friendId)

        _friendStateFlow.emit(friend)
    }

    fun sendMessage(message: String) = launchCatching {
        if (message.isBlank()) return@launchCatching

        chatRepository.sendMessage(message)
    }

}