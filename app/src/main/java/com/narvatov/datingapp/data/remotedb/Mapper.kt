package com.narvatov.datingapp.data.remotedb

import com.google.firebase.firestore.DocumentSnapshot
import com.narvatov.datingapp.data.remotedb.datasource.ChatRemoteDataSource
import com.narvatov.datingapp.data.remotedb.datasource.ConversationRemoteDataSource
import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.model.local.message.ChatMessage
import com.narvatov.datingapp.model.local.message.Conversation
import com.narvatov.datingapp.model.local.user.User
import java.util.Date

context (ConversationRemoteDataSource)
fun List<DocumentSnapshot>.mapConversations(userId: String): List<Conversation> {
    return map { rawMessage ->
        val friendId: String
        val friendPhotoBase64: String
        val friendName: String


        val authorId = rawMessage.requestString(Schema.CONVERSATION_AUTHOR_ID)
        val responderId = rawMessage.requestString(Schema.CONVERSATION_RESPONDER_ID)

        if (authorId == userId) {
            friendId = responderId
            friendPhotoBase64 = rawMessage.requestString(Schema.CONVERSATION_RESPONDER_PHOTO_BASE_64)
            friendName = rawMessage.requestString(Schema.CONVERSATION_RESPONDER_NAME)
        } else {
            friendId = authorId
            friendPhotoBase64 = rawMessage.requestString(Schema.CONVERSATION_AUTHOR_PHOTO_BASE_64)
            friendName = rawMessage.requestString(Schema.CONVERSATION_AUTHOR_NAME)
        }

        val lastText = rawMessage.requestString(Schema.CONVERSATION_LAST_MESSAGE)
        val senderId = rawMessage.requestString(Schema.CONVERSATION_LAST_MESSAGE_SENDER_ID)
        val timestamp = rawMessage.requestString(Schema.CONVERSATION_LAST_MESSAGE_TIMESTAMP)
        val sendDate = Date(timestamp.toLong())

        Conversation(friendId, friendPhotoBase64, friendName, lastText, senderId == userId, sendDate)
    }
}

context (ChatRemoteDataSource)
fun List<DocumentSnapshot>.mapMessages(userId: String): List<ChatMessage> {
    return map { rawMessage ->
        val text = rawMessage.requestString(Schema.CHAT_MESSAGE)
        val senderId = rawMessage.requestString(Schema.CHAT_SENDER_ID)
        val timestamp = rawMessage.requestString(Schema.CHAT_TIMESTAMP)
        val sendDate = Date(timestamp.toLong())

        ChatMessage.getChatMessage(userId, senderId, text, sendDate)
    }
}

context (UserRemoteDataSource)
fun DocumentSnapshot.mapUser(): User {
    val email = requestString(Schema.USER_EMAIL)
    val name = requestString(Schema.USER_NAME)
    val password = requestString(Schema.USER_PASSWORD)
    val photoBase64 = requestString(Schema.USER_PHOTO_BASE_64)
    val online = requestBoolean(Schema.USER_AVAILABLE)

    return User(id, email, password, name, photoBase64, online)
}

context (UserRemoteDataSource)
fun List<DocumentSnapshot>.mapUsers() = map { it.mapUser() }