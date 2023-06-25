package com.narvatov.datingapp.data.remotedb

import com.google.firebase.firestore.DocumentSnapshot
import com.narvatov.datingapp.data.remotedb.firestore.ChatRemoteDataSource
import com.narvatov.datingapp.data.remotedb.firestore.ConversationRemoteDataSource
import com.narvatov.datingapp.data.remotedb.firestore.UserRemoteDataSource
import com.narvatov.datingapp.model.local.message.ChatMessage
import com.narvatov.datingapp.model.local.message.Conversation
import com.narvatov.datingapp.model.local.message.ReadableConversationMessage
import com.narvatov.datingapp.model.local.user.User
import java.util.Date

context (ConversationRemoteDataSource)
fun List<DocumentSnapshot>.mapConversations(userId: String): List<Conversation> {
    return map { rawConversation ->
        val friendId: String
        val friendPhotoBase64: String
        val friendName: String


        val authorId = rawConversation.requestString(Schema.CONVERSATION_AUTHOR_ID)
        val responderId = rawConversation.requestString(Schema.CONVERSATION_RESPONDER_ID)

        if (authorId == userId) {
            friendId = responderId
            friendPhotoBase64 = rawConversation.requestString(Schema.CONVERSATION_RESPONDER_PHOTO_BASE_64)
            friendName = rawConversation.requestString(Schema.CONVERSATION_RESPONDER_NAME)
        } else {
            friendId = authorId
            friendPhotoBase64 = rawConversation.requestString(Schema.CONVERSATION_AUTHOR_PHOTO_BASE_64)
            friendName = rawConversation.requestString(Schema.CONVERSATION_AUTHOR_NAME)
        }

        val isRead = rawConversation.getBoolean(Schema.CONVERSATION_IS_READ) ?: true
        val lastText = rawConversation.requestString(Schema.CONVERSATION_LAST_MESSAGE)
        val senderId = rawConversation.requestString(Schema.CONVERSATION_LAST_MESSAGE_SENDER_ID)
        val timestamp = rawConversation.requestString(Schema.CONVERSATION_LAST_MESSAGE_TIMESTAMP)
        val sendDate = Date(timestamp.toLong())

        val dumbFriend = User(
            id = friendId,
            email = "Stub",
            password = "Stub",
            name = friendName,
            photoBase64 = friendPhotoBase64,
            online = false,
            fcmToken = "Stub",
        )

        val chatMessage = ChatMessage.getChatMessage(userId, senderId, lastText, sendDate)
        val readableConversationMessage = ReadableConversationMessage(chatMessage, isRead)

        Conversation(rawConversation.id, dumbFriend, readableConversationMessage)
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
    val online = getBoolean(Schema.USER_AVAILABLE) ?: false
    val fcmToken = getString(Schema.USER_FCM_TOKEN)

    return User(id, email, password, name, photoBase64, online, fcmToken)
}