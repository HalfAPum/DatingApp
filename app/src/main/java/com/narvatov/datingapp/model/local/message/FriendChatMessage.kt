package com.narvatov.datingapp.model.local.message

data class FriendChatMessage(
    override val text: String,
    override val sendTime: Long,
    override var showMessageTime: Boolean = true,
): ChatMessage