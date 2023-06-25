package com.narvatov.datingapp.model.remote

import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.model.local.user.User
import java.util.Date

class ConversationEntity : HashMap<String, Any>() {

    companion object {
        fun newConversationEntity(
            user: User,
            friend: User,
        ) : ConversationEntity = ConversationEntity().apply { putAll(mapOf(
            Schema.CONVERSATION_AUTHOR_ID to user.id,
            Schema.CONVERSATION_AUTHOR_PHOTO_BASE_64 to user.photoBase64,
            Schema.CONVERSATION_AUTHOR_NAME to user.name,

            Schema.CONVERSATION_RESPONDER_ID to friend.id,
            Schema.CONVERSATION_RESPONDER_PHOTO_BASE_64 to friend.photoBase64,
            Schema.CONVERSATION_RESPONDER_NAME to friend.name,

            Schema.CONVERSATION_IS_READ to false,
            Schema.CONVERSATION_LAST_MESSAGE to "",
            Schema.CONVERSATION_LAST_MESSAGE_SENDER_ID to user.id,
            Schema.CONVERSATION_LAST_MESSAGE_TIMESTAMP to Date().time.toString(),
        )) }

        fun updateConversationEntity(text: String, senderId: String): ConversationEntity = ConversationEntity().apply { putAll(mapOf(
            Schema.CONVERSATION_IS_READ to false,
            Schema.CONVERSATION_LAST_MESSAGE to text,
            Schema.CONVERSATION_LAST_MESSAGE_SENDER_ID to senderId,
            Schema.CONVERSATION_LAST_MESSAGE_TIMESTAMP to Date().time.toString(),
        )) }

        fun updateConversationReadEntity(): ConversationEntity = ConversationEntity().apply { putAll(mapOf(
            Schema.CONVERSATION_IS_READ to true,
        )) }
    }

}