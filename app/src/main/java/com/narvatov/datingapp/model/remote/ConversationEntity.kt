package com.narvatov.datingapp.model.remote

import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.putAll
import java.util.Date

class ConversationEntity : HashMap<String, String>() {

    companion object {
//        fun newConversationEntity(text: String, senderId: String, receiverId: String): ConversationEntity = ConversationEntity().putAll(
//            Schema.CONVERSATION_LAST_MESSAGE to text,
//            Schema.CONVERSATION_SENDER_ID to senderId,
//            Schema.CONVERSATION_RECEIVER_ID to receiverId,
//        )

        fun updateConversationEntity(text: String, senderId: String): ConversationEntity = ConversationEntity().putAll(
            Schema.CONVERSATION_LAST_MESSAGE to text,
            Schema.CONVERSATION_LAST_MESSAGE_SENDER_ID to senderId,
            Schema.CONVERSATION_LAST_MESSAGE_TIMESTAMP to Date().time.toString(),
        )
    }

}