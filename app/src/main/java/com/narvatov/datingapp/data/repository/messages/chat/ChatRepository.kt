package com.narvatov.datingapp.data.repository.messages.chat

import com.narvatov.datingapp.data.remotedb.datasource.ChatRemoteDataSource
import com.narvatov.datingapp.data.remotedb.datasource.ConversationRemoteDataSource
import com.narvatov.datingapp.data.repository.NotificationRepository
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.model.remote.ConversationEntity.Companion.updateConversationEntity
import com.narvatov.datingapp.model.remote.NotificationData
import com.narvatov.datingapp.model.remote.SendNewMessage.Companion.newSendMessageEntity
import com.narvatov.datingapp.model.remote.SendNotification
import com.narvatov.datingapp.utils.inject
import kotlin.properties.Delegates.notNull

class ChatRepository(
    private val userId: String,
    private val friendId: String,
    private val chatRemoteDataSource: ChatRemoteDataSource,
    private val conversationRemoteDataSource: ConversationRemoteDataSource,
    private val notificationRepository: NotificationRepository,
) : Repository() {

    private val userSessionRepository by inject<UserSessionRepository>()

    private var conversationId: String by notNull()

    val chatMessageFlow = chatRemoteDataSource.chatMessageFlow

    init {
        launchCatching {
            conversationId = conversationRemoteDataSource.getConversationId(friendId)
        }
    }

    suspend fun sendMessage(message: String, friend: User) {
        chatRemoteDataSource.sendMessage(newSendMessageEntity(message, userId, friendId))

        val updateConversationEntity = updateConversationEntity(message, userId)
        conversationRemoteDataSource.updateConversation(conversationId, updateConversationEntity)

        if (friend.offline) {
            notificationRepository.sendNotification(
                SendNotification(
                    NotificationData(
                        userId,
                        userSessionRepository.user.name,
                        userSessionRepository.user.fcmToken,
                        message
                    ),
                    tokens = listOf(friend.fcmToken)
                )
            )
        }
    }

}