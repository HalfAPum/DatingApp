package com.narvatov.datingapp.data.remotedb.firestore

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.awaitUnit
import com.narvatov.datingapp.data.remotedb.mapConversations
import com.narvatov.datingapp.data.remotedb.throwNoConversationId
import com.narvatov.datingapp.model.local.message.Conversation
import com.narvatov.datingapp.model.remote.ConversationEntity
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.LinkedList

class ConversationRemoteDataSource(
    private val userId: String,
) : FireStoreRemoteDataSource() {

    override val collectionName = Schema.CONVERSATION_TABLE

    val conversationFlow = userAuthoredConversationFlow
        .combine(userRespondedConversationFlow) { source1, source2 ->
            LinkedList<Conversation>().apply {
                addAll(source1)
                addAll(source2)
            }.sortedByDescending { it.sendDate.time }
        }

    private val userAuthoredConversationFlow
        get() = collection
            .whereEqualTo(Schema.CONVERSATION_AUTHOR_ID, userId)
            .conversationFlow()

    private val userRespondedConversationFlow
        get() = collection
            .whereEqualTo(Schema.CONVERSATION_RESPONDER_ID, userId)
            .conversationFlow()

    private fun Query.conversationFlow() = this
        .snapshots()
        .map { it.documents }
        .map { it.mapConversations(userId) }

    suspend fun getConversationId(friendId: String) = IOOperation {
        val userAuthorFriendConversation = collection
            .whereEqualTo(Schema.CONVERSATION_AUTHOR_ID,userId)
            .whereEqualTo(Schema.CONVERSATION_RESPONDER_ID, friendId)
            .get()
            .await()
            .documents.getOrNull(0)?.id

        val friendAuthorUserConversation = collection
            .whereEqualTo(Schema.CONVERSATION_AUTHOR_ID,friendId)
            .whereEqualTo(Schema.CONVERSATION_RESPONDER_ID, userId)
            .get()
            .await()
            .documents.getOrNull(0)?.id

        userAuthorFriendConversation
            ?: friendAuthorUserConversation
            ?: throwNoConversationId()
    }


    suspend fun addConversation(conversationEntity: ConversationEntity) = IOOperation {
        collection.add(conversationEntity).awaitUnit()
    }


    suspend fun updateConversation(
        conversationId: String,
        conversationEntity: ConversationEntity
    ) = IOOperation {
        collection.document(conversationId)
            .update(conversationEntity as Map<String, Any>)
            .awaitUnit()
    }

}