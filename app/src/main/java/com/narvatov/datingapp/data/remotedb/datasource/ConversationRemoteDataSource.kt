package com.narvatov.datingapp.data.remotedb.datasource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.snapshots
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.requestString
import com.narvatov.datingapp.model.local.message.Conversation
import com.narvatov.datingapp.model.remote.ConversationEntity
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.LinkedList

class ConversationRemoteDataSource(
    private val userId: String,
) : RemoteDataSource() {

    override val collectionName = Schema.CONVERSATION_TABLE

    val conversationFlow = collection
        .whereEqualTo(Schema.CONVERSATION_AUTHOR_ID, userId)
        .snapshots()
        .map { it.documents }
        .map { it.mapMessage() }
        .combine(collection
            .whereEqualTo(Schema.CONVERSATION_RESPONDER_ID, userId)
            .snapshots()
            .map { it.documents }
            .map { it.mapMessage() }) { source1, source2 ->
            LinkedList<Conversation>().apply {
                addAll(source1)
                addAll(source2)
            }.sortedBy { it.sendTime }
        }

    private fun List<DocumentSnapshot>.mapMessage(): List<Conversation> {
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

    suspend fun addConversation(conversationEntity: ConversationEntity) = IOOperation {
        collection.add(conversationEntity).await().id
    }

    suspend fun updateConversation(
        conversationId: String,
        conversationEntity: ConversationEntity
    ) = IOOperation {
        collection.document(conversationId)
            .update(conversationEntity as Map<String, Any>)
            .await()
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