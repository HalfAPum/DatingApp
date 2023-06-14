package com.narvatov.datingapp.data.repository.messages.chat

import com.narvatov.datingapp.data.remotedb.datasource.ChatRemoteDataSource
import com.narvatov.datingapp.data.remotedb.datasource.ConversationRemoteDataSource
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.model.remote.ConversationEntity.Companion.updateConversationEntity
import com.narvatov.datingapp.model.remote.SendNewMessage.Companion.newSendMessageEntity
import kotlin.properties.Delegates.notNull

class ChatRepository(
    private val userId: String,
    private val friendId: String,
    private val chatRemoteDataSource: ChatRemoteDataSource,
    private val conversationRemoteDataSource: ConversationRemoteDataSource,
) : Repository() {

    private var conversationId: String by notNull()

    val chatMessageFlow = chatRemoteDataSource.chatMessageFlow

    init {
        launchCatching {
            conversationId = conversationRemoteDataSource.getConversationId(friendId)
        }
    }

    suspend fun sendMessage(message: String) {
        chatRemoteDataSource.sendMessage(newSendMessageEntity(message, userId, friendId))

        val updateConversationEntity = updateConversationEntity(message, userId)
        conversationRemoteDataSource.updateConversation(conversationId, updateConversationEntity)
    }

}