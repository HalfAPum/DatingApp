package com.narvatov.datingapp.data.remotedb

object Schema {
    const val USER_TABLE = "user"
    const val USER_NAME = "name"
    const val USER_EMAIL = "email"
    const val USER_PASSWORD = "password"
    const val USER_PHOTO_BASE_64 = "photo_base_64"

    const val CHAT_TABLE = "chat"
    const val CHAT_MESSAGE = "message"
    const val CHAT_TIMESTAMP = "timestamp"
    const val CHAT_SENDER_ID = "sender_id"
    const val CHAT_RECEIVER_ID = "receiver_id"
}