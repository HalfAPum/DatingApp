package com.narvatov.datingapp.ui.viewmodel.messages.chat

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.Dispatcher
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.remotedb.firestore.UserRemoteDataSource
import com.narvatov.datingapp.data.repository.messages.chat.ChatRepository
import com.narvatov.datingapp.domain.chat.SendMessageUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import org.koin.android.annotation.KoinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

@KoinViewModel
class ChatViewModel(
    friendId: String,
    userRemoteDataSource: UserRemoteDataSource,
    dispatcher: Dispatcher,
) : ViewModel() {

    private val chatRepository by inject<ChatRepository>(
        ChatRepository::class.java,
        parameters = { parametersOf(friendId) },
    )

    private val sendMessageUseCase: SendMessageUseCase by lazy {
        inject<SendMessageUseCase>(
            SendMessageUseCase::class.java,
            parameters = { parametersOf(chatRepository) },
        ).value
    }

    val chatMessageFlow = chatRepository.chatMessageFlow.flowOn(dispatcher.IO)

    val friendFlow = userRemoteDataSource.getUserFlow(friendId)

    fun sendMessage(message: String) = launchCatching {
        sendMessageUseCase(message, friendFlow.first())
    }

}