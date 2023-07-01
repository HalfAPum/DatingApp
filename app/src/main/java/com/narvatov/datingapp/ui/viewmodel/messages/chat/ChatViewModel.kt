package com.narvatov.datingapp.ui.viewmodel.messages.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halfapum.general.coroutines.Dispatcher
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.remotedb.firestore.UserRemoteDataSource
import com.narvatov.datingapp.data.remotedb.throwNoFriendException
import com.narvatov.datingapp.data.repository.messages.chat.ChatRepository
import com.narvatov.datingapp.delegate.common.context.ContextDelegate
import com.narvatov.datingapp.delegate.common.context.IContextDelegate
import com.narvatov.datingapp.domain.chat.ChatMessagesFlowUseCase
import com.narvatov.datingapp.domain.chat.SendMessageUseCase
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.IProgressDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.ProgressDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.annotation.KoinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

@KoinViewModel
class ChatViewModel(
    friendId: String,
    userRemoteDataSource: UserRemoteDataSource,
    dispatcher: Dispatcher,
) : ViewModel(), IContextDelegate by ContextDelegate,
    IProgressDelegate by ProgressDelegate(configHideAttempts = 2) {

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

    private val chatMessagesFlowUseCase: ChatMessagesFlowUseCase by lazy {
        inject<ChatMessagesFlowUseCase>(
            ChatMessagesFlowUseCase::class.java,
            parameters = { parametersOf(chatRepository) },
        ).value
    }

    init {
        showProgress()
    }

    val chatMessageFlow = chatMessagesFlowUseCase()
        .onEach { hideProgress() }
        .flowOn(dispatcher.IO)

    private val _friendFlow = MutableStateFlow<User?>(null)
    val friendFlow = _friendFlow.asStateFlow()

    init {
        userRemoteDataSource.getUserFlow(friendId)
            .catch {
                //Error occurs when we are in chat with person and he deletes account.
                //Just ignore it don't pop user back because it is unexpected behavior for user.
                //Consider adding blocking dialog with explanation what happened.
            }
            .onEach {
                _friendFlow.emit(it)
                hideProgress()
            }
            .flowOn(dispatcher.IO)
            .launchIn(viewModelScope)
    }

    fun sendMessage(message: String) = launchCatching {
        val friend = friendFlow.first() ?: throwNoFriendException()

        sendMessageUseCase(message, friend)
    }

}