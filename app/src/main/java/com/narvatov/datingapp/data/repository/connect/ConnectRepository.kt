package com.narvatov.datingapp.data.repository.connect

import com.narvatov.datingapp.data.remotedb.datasource.ConversationRemoteDataSource
import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.model.remote.ConversationEntity.Companion.newConversationEntity
import org.koin.core.annotation.Factory

@Factory
class ConnectRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val conversationRemoteDataSource: ConversationRemoteDataSource,
    private val userSessionRepository: UserSessionRepository,
) {

    suspend fun get10NewFriends() = userRemoteDataSource.get10NewFriends(userSessionRepository.user)

    suspend fun createConversation(friend: User) {
        val conversationEntity = newConversationEntity(
            user = userSessionRepository.user,
            friend = friend,
        )

        conversationRemoteDataSource.addConversation(conversationEntity)
    }

    suspend fun markFriendMatched(friend: User) {
        userRemoteDataSource.updateMatch(userSessionRepository.user.id, friend.id)

        userSessionRepository.user.fullUserData[friend.id] = true
    }

}