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
import java.util.Date
import java.util.LinkedList

class ChatRemoteDataSource(
    private val userId: String,
    private val friendId: String,
) : RemoteDataSource() {

    override val collectionName = Schema.CHAT_TABLE

    //Todo add paging
    val chatMessageFlow = friendChatMessageFlow
        .combine(userChatMessageFlow) { friendMessages, userMessages ->
            LinkedList<ChatMessage>().apply {
                addAll(friendMessages)
                addAll(userMessages)
            }.sortedByDescending { it.sendDate.time }
        }

    private val friendChatMessageFlow
        get() = collection
            .whereEqualTo(Schema.CHAT_RECEIVER_ID, userId)
            .whereEqualTo(Schema.CHAT_SENDER_ID, friendId)
            .chatMessagesFlow()

    private val userChatMessageFlow
        get() = collection
            .whereEqualTo(Schema.CHAT_RECEIVER_ID, friendId)
            .whereEqualTo(Schema.CHAT_SENDER_ID, userId)
            .chatMessagesFlow()

    private fun Query.chatMessagesFlow() = this
        .orderBy(Schema.CHAT_TIMESTAMP, Query.Direction.DESCENDING)
        .snapshots()
        .map { it.documents.mapMessage() }

    private fun List<DocumentSnapshot>.mapMessage(): List<ChatMessage> {
        return map { rawMessage ->
            val text = rawMessage.requestString(Schema.CHAT_MESSAGE)
            val senderId = rawMessage.requestString(Schema.CHAT_SENDER_ID)
            val timestamp = rawMessage.requestString(Schema.CHAT_TIMESTAMP)
            val sendDate = Date(timestamp.toLong())

            ChatMessage.getChatMessage(userId, senderId, text, sendDate)
        }
    }


    suspend fun sendMessage(sendNewMessage: SendNewMessage) = IOOperation {
        collection.add(sendNewMessage).await()
    }

}