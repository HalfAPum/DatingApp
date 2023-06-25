package com.narvatov.datingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.narvatov.datingapp.data.repository.messages.MessagesRepository
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class BottomBarViewModel(messagesRepository: MessagesRepository) : ViewModel() {

    val newConversationMessagesFlow = messagesRepository.newConversationMessagesFlow

}