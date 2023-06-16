package com.narvatov.datingapp.model.local.message

import com.narvatov.datingapp.model.local.user.User

data class Conversation(
    val friend: User,
    val chatMessage: ChatMessage,
) : CommonMessage by chatMessage