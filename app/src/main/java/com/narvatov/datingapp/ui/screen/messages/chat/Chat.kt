package com.narvatov.datingapp.ui.screen.messages.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.messages.chat.ChatViewModel

@Composable
fun Chat(viewModel: ChatViewModel) {
    val user = viewModel.getUser()
    val friendFlow = viewModel.friendStageFlow.collectAsState()

//    val messages by viewModel.friendStageFlow.collectAsState()

    if (friendFlow.value != null) {
        val friend = friendFlow.value!!

        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.background(Color.LightGray)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        bitmap = friend.photoBitmap.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.profile_image),
                        modifier = Modifier
                            .padding(start = 40.dp)
                            .padding(vertical = 10.dp)
                            .size(50.dp)
                            .clip(CircleShape),
                    )

                    Text(
                        text = friend.name,
                        style = Typography.h6,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
            }
        }


    }
}