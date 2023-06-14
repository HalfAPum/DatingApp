package com.narvatov.datingapp.ui.screen.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.navigation.Chat
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.messages.MessagesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Messages(
    viewModel: MessagesViewModel = getViewModel()
) {
    val conversations by viewModel.conversationFlow.collectAsState(emptyList())

    LazyColumn {
        items(conversations) { conversation ->
            Row(Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .height(100.dp)
                .clickable { navigate(Chat, conversation.friendId) }
            ) {
                Image(
                    bitmap = conversation.photoBitmap.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = conversation.friendName + stringResource(R.string.space_photo),
                    modifier = Modifier.size(100.dp),
                )

                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = conversation.friendName,
                        style = Typography.h6,
                    )

                    WeightedSpacer()

                    Text(
                        text = conversation.lastText,
                        style = Typography.body1
                    )
                }
            }
        }
    }
}