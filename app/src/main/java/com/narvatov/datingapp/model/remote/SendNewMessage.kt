package com.narvatov.datingapp.model.remote

import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.putAll
import java.util.Date

class SendNewMessage : HashMap<String, String>() {

    companion object {
        fun newSendMessageEntity(text: String, senderId: String, receiverId: String): SendNewMessage = SendNewMessage().putAll(
            Schema.CHAT_MESSAGE to text,
            Schema.CHAT_SENDER_ID to senderId,
            Schema.CHAT_RECEIVER_ID to receiverId,
            Schema.CHAT_TIMESTAMP to Date().time.toString(),
        )
    }

}