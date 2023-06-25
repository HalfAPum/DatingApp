package com.narvatov.datingapp.data.remotedb.firestore

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.awaitUnit
import com.narvatov.datingapp.data.remotedb.mapConversations
import com.narvatov.datingapp.data.remotedb.throwNoConversationId
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.message.Conversation
import com.narvatov.datingapp.model.remote.ConversationEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single
import java.util.LinkedList

@Single
class ConversationRemoteDataSource(
    private val userSessionRepository: UserSessionRepository,
) : FirestoreRemoteDataSource() {

    private val userId: String
        get() = userSessionRepository.user.id

    private val userStateFlow = userSessionRepository.userStateFlow

    override val collectionName = Schema.CONVERSATION_TABLE

    @OptIn(FlowPreview::class)
    val conversationFlow = userStateFlow.map { user ->
        userAuthoredConversationFlow(user.id)
            .combine(userRespondedConversationFlow(user.id)) { source1, source2 ->
                LinkedList<Conversation>().apply {
                    addAll(source1)
                    addAll(source2)
                }.sortedByDescending { it.sendDate.time }
            }
    }.flattenConcat()

    private fun userAuthoredConversationFlow(userId: String) = collection
        .whereEqualTo(Schema.CONVERSATION_AUTHOR_ID, userId)
        .conversationFlow()

    private fun userRespondedConversationFlow(userId: String) = collection
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


    suspend fun deleteUserConversations() = IOOperation {
        conversationFlow.first().map {
            async { collection.document(it.id).delete().awaitUnit() }
        }.awaitAll()
    }

}