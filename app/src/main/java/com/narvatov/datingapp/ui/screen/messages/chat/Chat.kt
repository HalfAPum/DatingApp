package com.narvatov.datingapp.ui.screen.messages.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.model.local.message.FriendChatMessage
import com.narvatov.datingapp.model.local.message.UserChatMessage
import com.narvatov.datingapp.ui.ListSpacer
import com.narvatov.datingapp.ui.common.LoaderBox
import com.narvatov.datingapp.ui.screen.messages.chat.message.FriendMessage
import com.narvatov.datingapp.ui.screen.messages.chat.message.UserMessage
import com.narvatov.datingapp.ui.theme.ChatBackground
import com.narvatov.datingapp.ui.viewmodel.messages.chat.ChatViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Chat(friendId: String?) {
    ChatImpl(viewModel = getViewModel(parameters = { parametersOf(friendId) }))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChatImpl(viewModel: ChatViewModel) = LoaderBox(viewModel) {
    val friendFlow by viewModel.friendFlow.collectAsState(null)
    val chatMessages by viewModel.chatMessageFlow.collectAsState(emptyList())

    if (friendFlow != null) {
        val friend = friendFlow!!

        Column(modifier = Modifier.fillMaxSize()) {
            ChatFriendBar(friend)

            val chatMessagesListState = rememberLazyListState()

            LazyColumn(
                state = chatMessagesListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .background(color = ChatBackground),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                reverseLayout = true,
            ) {
                ListSpacer()

                items(chatMessages, key = { it.sendTime }) { chatMessage ->
                    when(chatMessage) {
                        is FriendChatMessage -> FriendMessage(
                            chatMessage = chatMessage,
                            modifier = Modifier.padding(horizontal = 16.dp).animateItemPlacement(),
                        )
                        is UserChatMessage -> UserMessage(
                            chatMessage = chatMessage,
                            modifier = Modifier.padding(horizontal = 16.dp).animateItemPlacement(),
                        )
                    }
                }

                ListSpacer()
            }

            SendMessageBar(chatMessagesListState) { message ->
                viewModel.sendMessage(message)
            }
        }
    }
}