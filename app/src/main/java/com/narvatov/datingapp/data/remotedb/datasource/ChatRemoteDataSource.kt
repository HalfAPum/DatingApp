package com.narvatov.datingapp.data.remotedb.datasource

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.awaitUnit
import com.narvatov.datingapp.data.remotedb.mapMessage
import com.narvatov.datingapp.model.local.message.ChatMessage
import com.narvatov.datingapp.model.remote.SendNewMessage
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
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
        .map { it.documents.mapMessage(userId) }


    suspend fun sendMessage(sendNewMessage: SendNewMessage) = IOOperation {
        collection.add(sendNewMessage).awaitUnit()
    }

}