package com.narvatov.datingapp.ui.viewmodel.messages

import androidx.lifecycle.ViewModel
import com.narvatov.datingapp.data.repository.MessagesRepository
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MessagesViewModel(
    messagesRepository: MessagesRepository
) : ViewModel() {

    val conversationFlow = messagesRepository.conversationFlow

}