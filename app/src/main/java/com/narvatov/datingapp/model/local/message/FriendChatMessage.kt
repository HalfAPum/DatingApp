package com.narvatov.datingapp.model.local.message

import java.util.Date

data class FriendChatMessage(
    override val text: String,
    override val sendDate: Date,
): ChatMessage