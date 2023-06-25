package com.narvatov.datingapp.data.repository.messages

import com.narvatov.datingapp.data.remotedb.firestore.ConversationRemoteDataSource
import com.narvatov.datingapp.model.remote.ConversationEntity.Companion.updateConversationReadEntity
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class MessagesRepository(private val conversationRemoteDataSource: ConversationRemoteDataSource) {

    val conversationFlow = conversationRemoteDataSource.conversationFlow

    val newConversationMessagesFlow = conversationFlow.map {
        it.count { it.showNewMessageBadge }
    }

    suspend fun updateMessageRead(conversationId: String) {
        conversationRemoteDataSource.updateConversation(conversationId, updateConversationReadEntity())
    }

}