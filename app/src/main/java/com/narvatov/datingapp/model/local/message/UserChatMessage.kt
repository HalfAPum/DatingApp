package com.narvatov.datingapp.model.local.message

import java.util.Date

data class UserChatMessage(
    override val text: String,
    override val sendDate: Date,
): ChatMessage