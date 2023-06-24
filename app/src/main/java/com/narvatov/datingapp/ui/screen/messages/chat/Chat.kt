package com.narvatov.datingapp.ui.screen.messages.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults.MinHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.model.local.message.FriendChatMessage
import com.narvatov.datingapp.model.local.message.UserChatMessage
import com.narvatov.datingapp.ui.ListSpacer
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.navigation.FriendProfile
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.screen.messages.chat.message.FriendMessage
import com.narvatov.datingapp.ui.screen.messages.chat.message.UserMessage
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.messages.chat.ChatViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Chat(friendId: String?) {
    ChatImpl(viewModel = getViewModel(parameters = { parametersOf(friendId) }))
}

@Composable
private fun ChatImpl(viewModel: ChatViewModel) {
    val friendFlow by viewModel.friendFlow.collectAsState(null)
    val chatMessages by viewModel.chatMessageFlow.collectAsState(emptyList())

    if (friendFlow != null) {
        val friend = friendFlow!!

        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .background(Color.LightGray)
                .clickable {
                    navigate(FriendProfile, friend)
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        bitmap = friend.photoBitmap,
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.profile_image),
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .size(50.dp)
                            .clip(CircleShape),
                    )

                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            text = friend.name,
                            style = Typography.h6,
                        )

                        WeightedSpacer()

                        Text(
                            text = if (friend.online) stringResource(R.string.online)
                                else stringResource(R.string.last_seen_recently),
                            style = Typography.body1,
                        )
                    }
                }
            }

            val chatMessagesListState = rememberLazyListState()

            LazyColumn(
                state = chatMessagesListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                reverseLayout = true,
            ) {
                ListSpacer()

                items(chatMessages) { chatMessage ->
                    when(chatMessage) {
                        is FriendChatMessage -> FriendMessage(chatMessage)
                        is UserChatMessage -> UserMessage(chatMessage)
                    }
                }

                ListSpacer()
            }

            Box(modifier = Modifier.background(Color.LightGray)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var message by rememberSaveable { mutableStateOf("") }

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.your_message),
                                color = Color.Black.copy(alpha = 0.5F),
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            viewModel.sendMessage(message)
                            message = ""
                        }),
                        maxLines = 6,
                        modifier = Modifier.padding(start = 20.dp).weight(1F)
                    )

                    val scrollScope = rememberCoroutineScope()

                    Image(
                        imageVector = Icons.Rounded.Send,
                        contentDescription = stringResource(R.string.send_message),
                        colorFilter = ColorFilter.tint(color = PrimaryColor),
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .size(MinHeight)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = PrimaryColor, shape = CircleShape)
                            .clickable {
                                viewModel.sendMessage(message)
                                message = ""

                                scrollScope.launch {
                                    chatMessagesListState.animateScrollToItem(0, 0)
                                }
                            },
                    )
                }
            }
        }
    }
}