package com.narvatov.datingapp.ui.screen.messages.chat.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.model.local.message.ChatMessage
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun FriendMessage(chatMessage: ChatMessage) {
    Column {
        Box(
            modifier = Modifier.background(
                color = PrimaryColor.copy(alpha = 0.07F),
                shape = Shapes.large.copy(bottomStart = CornerSize(0.dp))
            )
        ) {
            Text(
                text = chatMessage.text,
                style = Typography.body1,
                modifier = Modifier.padding(16.dp),
            )
        }

        Text(
            text = chatMessage.sendTime,
            style = Typography.caption,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 4.dp)
        )
    }
}