package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.remotedb.datasource.ChatRemoteDataSource
import com.narvatov.datingapp.model.remote.SendNewMessage.Companion.newSendMessageEntity

class ChatRepository(
    private val userId: String,
    private val friendId: String,
    private val chatRemoteDataSource: ChatRemoteDataSource,
) : Repository() {

    val chatMessageFlow = chatRemoteDataSource.chatMessageFlow

    suspend fun sendMessage(message: String) {
        chatRemoteDataSource.sendMessage(newSendMessageEntity(message, userId, friendId))
    }

}