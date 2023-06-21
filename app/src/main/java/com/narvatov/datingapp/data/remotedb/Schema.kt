package com.narvatov.datingapp.data.remotedb

object Schema {
    const val USER_TABLE = "user"
    const val USER_ID = "user_id"
    const val USER_NAME = "name"
    const val USER_EMAIL = "email"
    const val USER_PASSWORD = "password"
    const val USER_PHOTO_BASE_64 = "photo_base_64"
    const val USER_AVAILABLE = "available"
    const val USER_FCM_TOKEN = "fcm_token"

    const val CHAT_TABLE = "chat"
    const val CHAT_MESSAGE = "message"
    const val CHAT_TIMESTAMP = "timestamp"
    const val CHAT_SENDER_ID = "sender_id"
    const val CHAT_RECEIVER_ID = "receiver_id"

    const val CONVERSATION_TABLE = "conversation"
    const val CONVERSATION_AUTHOR_ID = "author_id"
    const val CONVERSATION_RESPONDER_ID = "responder_id"
    const val CONVERSATION_AUTHOR_PHOTO_BASE_64 = "author_photo_base_64"
    const val CONVERSATION_RESPONDER_PHOTO_BASE_64 = "responder_photo_base_64"
    const val CONVERSATION_AUTHOR_NAME = "author_name"
    const val CONVERSATION_RESPONDER_NAME = "responder_name"
    const val CONVERSATION_LAST_MESSAGE = "last_message"
    const val CONVERSATION_LAST_MESSAGE_TIMESTAMP = "last_message_timestamp"
    const val CONVERSATION_LAST_MESSAGE_SENDER_ID = "last_message_sender_id"

    const val MATCH_TABLE = "match"
    const val MATCH_INITIATOR_ID = "initiator_id"
    const val MATCH_RESPONDER_ID = "responder_id"
}