package com.narvatov.datingapp.data.remotedb.datasource

import com.google.firebase.firestore.ktx.snapshots
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.awaitUnit
import com.narvatov.datingapp.data.remotedb.mapMessage
import com.narvatov.datingapp.model.local.message.Conversation
import com.narvatov.datingapp.model.remote.ConversationEntity
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.LinkedList

class ConversationRemoteDataSource(
    private val userId: String,
) : RemoteDataSource() {

    override val collectionName = Schema.CONVERSATION_TABLE

    val conversationFlow = collection
        .whereEqualTo(Schema.CONVERSATION_AUTHOR_ID, userId)
        .snapshots()
        .map { it.documents }
        .map { it.mapMessage(userId) }
        .combine(collection
            .whereEqualTo(Schema.CONVERSATION_RESPONDER_ID, userId)
            .snapshots()
            .map { it.documents }
            .map { it.mapMessage(userId) }) { source1, source2 ->
            LinkedList<Conversation>().apply {
                addAll(source1)
                addAll(source2)
            }.sortedBy { it.sendTime }
        }

    suspend fun addConversation(conversationEntity: ConversationEntity) = IOOperation {
        collection.add(conversationEntity).await().id
    }

    suspend fun updateConversation(
        conversationId: String,
        conversationEntity: ConversationEntity
    ) = IOOperation {
        collection.document(conversationId)
            .update(conversationEntity as Map<String, Any>)
            .awaitUnit()
    }

    suspend fun getConversationId(friendId: String) = IOOperation {
        collection.whereEqualTo(Schema.CONVERSATION_AUTHOR_ID,userId)
            .whereEqualTo(Schema.CONVERSATION_RESPONDER_ID, friendId)
            .get()
            .await()
            .documents.getOrNull(0)?.id
            ?: collection.whereEqualTo(Schema.CONVERSATION_AUTHOR_ID,friendId)
                .whereEqualTo(Schema.CONVERSATION_RESPONDER_ID, userId)
                .get()
                .await()
                .documents[0].id
    }

}