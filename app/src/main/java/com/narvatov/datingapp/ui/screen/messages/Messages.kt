package com.narvatov.datingapp.ui.screen.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.model.local.message.UserChatMessage
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.navigation.Chat
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.messages.MessagesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Messages(viewModel: MessagesViewModel = getViewModel()) {
    val conversations by viewModel.conversationFlow.collectAsState(emptyList())

    val cardHorizontalPadding = 20.dp
    val imageSize = 48.dp
    val contentToImagePadding = 10.dp

    LazyColumn {
        item {
            Text(
                text = stringResource(R.string.messages),
                style = Typography.h4,
                modifier = Modifier.padding(
                    start = cardHorizontalPadding,
                    top = 20.dp,
                    bottom = 20.dp
                ),
            )
        }
        itemsIndexed(conversations) { index, conversation ->
            Row(Modifier
                .clickable {
                    viewModel.updateMessageRead(conversation)
                    navigate(Chat, conversation.friend.id)
                }
                .padding(horizontal = cardHorizontalPadding)
                .height(68.dp)
                .padding(vertical = 10.dp)
                .fillMaxWidth()
            ) {
                Image(
                    bitmap = conversation.friend.photoBitmap,
                    contentScale = ContentScale.Crop,
                    contentDescription = conversation.friend.photoDescription(),
                    modifier = Modifier.size(imageSize).clip(CircleShape),
                )

                Column(modifier = Modifier.padding(start = contentToImagePadding).weight(1F)) {
                    Text(
                        text = conversation.friend.name,
                        style = Typography.body2.copy(fontWeight = FontWeight.Bold),
                    )

                    WeightedSpacer()

                    Text(
                        text = if (conversation.chatMessage.chatMessage is UserChatMessage) stringResource(
                            R.string.you
                        ) + conversation.chatMessage.text
                        else conversation.chatMessage.text,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Typography.body2
                    )
                }

                Column {
                    Text(
                        text = conversation.sendTime,
                        style = Typography.caption.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFFADAFBB)
                    )

                    WeightedSpacer()

                    if (conversation.showNewMessageBadge) {
                        Box(
                            modifier = Modifier
                                .background(color = PrimaryColor, shape = CircleShape)
                                .size(20.dp)
                                .align(Alignment.End)
                        ) {
                            Text(
                                text = "1",
                                style = Typography.caption.copy(fontWeight = FontWeight.Bold),
                                color = OnPrimaryColor,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            if (index != conversations.lastIndex) {
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = cardHorizontalPadding)
                        .padding(start = imageSize)
                        .padding(start = contentToImagePadding)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(color = Color(0xFFE8E6EA))
                )
            }
        }
    }
}