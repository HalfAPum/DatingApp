package com.narvatov.datingapp.ui.screen.messages.chat.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.model.local.message.ChatMessage
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun UserMessage(chatMessage: ChatMessage) {
    Row {
        WeightedSpacer()

        Column {
            Box(
                modifier = Modifier.background(
                    color = Color.LightGray.copy(alpha = 0.8F),
                    shape = Shapes.large.copy(bottomEnd = CornerSize(0.dp))
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
                    .align(Alignment.End)
                    .padding(top = 4.dp)
            )
        }
    }
}