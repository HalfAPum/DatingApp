package com.narvatov.datingapp.model.local.message

data class ReadableConversationMessage(
    val chatMessage: ChatMessage,
    val isRead: Boolean,
) : ChatMessage by chatMessage