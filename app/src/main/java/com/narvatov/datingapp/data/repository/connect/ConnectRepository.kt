package com.narvatov.datingapp.data.repository.connect

import com.narvatov.datingapp.data.api.match.MatchApi
import com.narvatov.datingapp.data.remotedb.firestore.ConversationRemoteDataSource
import com.narvatov.datingapp.data.remotedb.firestore.UserRemoteDataSource
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.model.remote.AddMatchRequest
import com.narvatov.datingapp.model.remote.ConversationEntity.Companion.newConversationEntity
import com.narvatov.datingapp.model.remote.NonMatchedFriendsRequest
import org.koin.core.annotation.Factory

@Factory
class ConnectRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val conversationRemoteDataSource: ConversationRemoteDataSource,
    private val userSessionRepository: UserSessionRepository,
    private val matchApi: MatchApi,
) {

    suspend fun getNonMatchedFriends(limit: Int) = userRemoteDataSource.getUsersByIds(
        matchApi.getNonMatchedFriends(
            NonMatchedFriendsRequest(
                userId = userSessionRepository.user.id,
                limit = limit,
            )
        ).map { it.id }
    )

    suspend fun createConversation(friend: User) {
        val conversationEntity = newConversationEntity(
            user = userSessionRepository.user,
            friend = friend,
        )

        conversationRemoteDataSource.addConversation(conversationEntity)
    }

    suspend fun markFriendMatched(friend: User) {
        matchApi.addMatch(AddMatchRequest(userSessionRepository.user.id, friend.id))
    }

}