package com.narvatov.datingapp.model.local.message

import java.util.Date

interface ChatMessage : CommonMessage {

    val text: String

    companion object {
        fun getChatMessage(userId: String, senderId: String, text: String, sendDate: Date): ChatMessage {
            return if (senderId == userId) UserChatMessage(text, sendDate)
            else FriendChatMessage(text, sendDate)
        }
    }

}