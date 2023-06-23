package com.narvatov.datingapp.ui.screen.messages.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.narvatov.datingapp.ui.viewmodel.messages.chat.DeeplinkChatViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun DeeplinkChat(
    friendId: String?,
    deeplinkChatViewModel: DeeplinkChatViewModel = getViewModel()
) {
    val chatIsReady by deeplinkChatViewModel.chatIsReadyStateFlow.collectAsState()

    if (chatIsReady) {
        Chat(friendId)
    }
}