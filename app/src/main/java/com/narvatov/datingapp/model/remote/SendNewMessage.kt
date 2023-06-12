package com.narvatov.datingapp.model.remote

import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.putAll
import com.narvatov.datingapp.model.local.message.send.SendMessage

class SendNewMessage : HashMap<String, String>() {

    companion object {
        fun SendMessage.toSendNewMessageEntity(): SendNewMessage = SendNewMessage().putAll(
            Schema.CHAT_MESSAGE to text,
            Schema.CHAT_SENDER_ID to senderId,
            Schema.CHAT_RECEIVER_ID to receiverId,
            Schema.CHAT_TIMESTAMP to timestamp,
        )
    }

}