package com.narvatov.datingapp.ui.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun NewMessagesBadge(
    messagesCount: Int,
    modifier: Modifier = Modifier,
) {
    val limitedMessagesCount = if (messagesCount > MESSAGES_COUNT_LIMIT) MESSAGES_COUNT_LIMIT
        else messagesCount

    if (messagesCount > NO_NEW_CONVERSATION_MESSAGES) {
        Box(
            modifier = Modifier
                .background(color = PrimaryColor, shape = CircleShape)
                .then(modifier)
        ) {
            Text(
                text = limitedMessagesCount.toString(),
                style = Typography.caption.copy(fontWeight = FontWeight.Bold),
                color = OnPrimaryColor,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

const val NO_NEW_CONVERSATION_MESSAGES = 0
private const val MESSAGES_COUNT_LIMIT = 99

@OptIn(ExperimentalAnimationApi::class)
val enterBadgeAnimation: EnterTransition = fadeIn() + expandIn() + scaleIn()

@OptIn(ExperimentalAnimationApi::class)
val exitBadgeAnimation: ExitTransition = scaleOut() + shrinkOut() + fadeOut()