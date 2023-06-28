package com.narvatov.datingapp.model.local.message

interface ChatMessage : CommonMessage {

    var showMessageTime: Boolean

    val text: String

    companion object {
        fun getChatMessage(userId: String, senderId: String, text: String, sendTime: Long): ChatMessage {
            return if (senderId == userId) UserChatMessage(text, sendTime)
            else FriendChatMessage(text, sendTime)
        }
    }

}