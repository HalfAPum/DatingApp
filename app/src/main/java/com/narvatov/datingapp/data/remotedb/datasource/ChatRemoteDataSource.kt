package com.narvatov.datingapp.data.remotedb.datasource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.requestString
import com.narvatov.datingapp.model.local.message.ChatMessage
import com.narvatov.datingapp.model.remote.SendNewMessage
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory
import java.util.Date
import java.util.LinkedList

//Todo pass user and friend when create object
@Factory
class ChatRemoteDataSource : RemoteDataSource() {

    suspend fun sendMessage(sendNewMessage: SendNewMessage) = IOOperation {
        db.collection(Schema.CHAT_TABLE).add(sendNewMessage).await()
    }

    //Todo add paging
    fun chatMessagesFlow(
        userId: String,
        friendId: String
    ) = friendChatMessagesFlow(userId, friendId)
        .combine(userChatMessagesFlow(userId, friendId)) { friendMessages, userMessages ->
        LinkedList<ChatMessage>().apply {
            addAll(friendMessages)
            addAll(userMessages)
        }.sortedBy { it.sendDate.time }
    }

    private fun friendChatMessagesFlow(
        userId: String,
        friendId: String
    ) = db.collection(Schema.CHAT_TABLE)
        .whereEqualTo(Schema.CHAT_RECEIVER_ID, userId)
        .whereEqualTo(Schema.CHAT_SENDER_ID, friendId)
        .chatMessagesFlow(userId)

    private fun userChatMessagesFlow(
        userId: String,
        friendId: String
    ) = db.collection(Schema.CHAT_TABLE)
        .whereEqualTo(Schema.CHAT_RECEIVER_ID, friendId)
        .whereEqualTo(Schema.CHAT_SENDER_ID, userId)
        .chatMessagesFlow(userId)


    private fun Query.chatMessagesFlow(
        userId: String
    ) = orderBy(Schema.CHAT_TIMESTAMP, Query.Direction.DESCENDING)
        .snapshots()
        .map {
            it.documents }
        .map { documentChange ->
            documentChange
//                .filter { it.type == DocumentChange.Type.ADDED }
//                .map { it. }
                .mapMessage(userId)
        }

    private fun List<DocumentSnapshot>.mapMessage(userId: String): List<ChatMessage> {
        return map { rawMessage ->
            val text = rawMessage.requestString(Schema.CHAT_MESSAGE)
            val senderId = rawMessage.requestString(Schema.CHAT_SENDER_ID)
            val timestamp = rawMessage.requestString(Schema.CHAT_TIMESTAMP)
            val sendDate = Date(timestamp.toLong())

            ChatMessage.getChatMessage(userId, senderId, text, sendDate)
        }
    }

}