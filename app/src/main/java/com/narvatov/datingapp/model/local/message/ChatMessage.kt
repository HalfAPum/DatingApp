package com.narvatov.datingapp.model.local.message

import android.icu.text.SimpleDateFormat
import java.util.Date

interface ChatMessage {

    val text: String

    val sendDate: Date

    val sendTime: String
        get() = SimpleDateFormat("hh:mm a").format(sendDate)

    companion object {
        fun getChatMessage(userId: String, senderId: String, text: String, sendDate: Date): ChatMessage {
            return if (senderId == userId) UserChatMessage(text, sendDate)
            else FriendChatMessage(text, sendDate)
        }
    }

}