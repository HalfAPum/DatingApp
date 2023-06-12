package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.remotedb.datasource.ChatRemoteDataSource
import com.narvatov.datingapp.model.local.message.ChatMessage
import com.narvatov.datingapp.model.local.message.send.SendMessage
import com.narvatov.datingapp.model.remote.SendNewMessage.Companion.toSendNewMessageEntity
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ChatRepository(
    private val chatRemoteDataSource: ChatRemoteDataSource,
) : Repository() {

    suspend fun sendMessage(sendMessage: SendMessage) {
        chatRemoteDataSource.sendMessage(sendMessage.toSendNewMessageEntity())
    }

    fun chatMessageFlow(userId: String, friendId: String) : Flow<List<ChatMessage>> {
        return chatRemoteDataSource.chatMessagesFlow(userId, friendId)
    }
}