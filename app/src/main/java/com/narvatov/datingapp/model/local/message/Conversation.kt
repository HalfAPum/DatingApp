package com.narvatov.datingapp.model.local.message

import com.narvatov.datingapp.model.local.user.User

data class Conversation(
    val id: String,
    val friend: User,
    val chatMessage: ReadableConversationMessage,
) : CommonMessage by chatMessage {

    val showNewMessageBadge: Boolean
        get() = when {
            chatMessage.chatMessage is UserChatMessage -> false
            chatMessage.chatMessage is FriendChatMessage && !chatMessage.isRead -> true
            else -> false
        }

}