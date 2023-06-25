package com.narvatov.datingapp.ui.viewmodel.messages

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.messages.MessagesRepository
import com.narvatov.datingapp.model.local.message.Conversation
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MessagesViewModel(private val messagesRepository: MessagesRepository) : ViewModel() {

    val conversationFlow = messagesRepository.conversationFlow

    fun updateMessageRead(conversation: Conversation) = launchCatching {
        if (!conversation.chatMessage.isRead) {
            messagesRepository.updateMessageRead(conversation.id)
        }
    }

}