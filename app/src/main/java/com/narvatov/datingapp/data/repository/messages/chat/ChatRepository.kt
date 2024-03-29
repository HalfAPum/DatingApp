package com.narvatov.datingapp.data.repository.messages.chat

import com.narvatov.datingapp.data.remotedb.firestore.ChatRemoteDataSource
import com.narvatov.datingapp.data.remotedb.firestore.ConversationRemoteDataSource
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.data.repository.notification.NotificationRepository
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.model.remote.ConversationEntity.Companion.updateConversationEntity
import com.narvatov.datingapp.model.remote.SendNewMessage.Companion.newSendMessageEntity
import com.narvatov.datingapp.ui.navigation.BottomNavigationDestination
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import kotlin.properties.Delegates.notNull

class ChatRepository(
    private val userId: String,
    private val friendId: String,
    private val chatRemoteDataSource: ChatRemoteDataSource,
    private val conversationRemoteDataSource: ConversationRemoteDataSource,
    private val notificationRepository: NotificationRepository,
) : Repository() {

    private var conversationId: String by notNull()

    val chatMessageFlow = chatRemoteDataSource.chatMessageFlow

    init {
        launchCatching {
            try {
                conversationId = conversationRemoteDataSource.getConversationId(friendId)
            } catch (e: IllegalArgumentException) {
                //Error occurs when we open deeplink message from deleted account.
                //Consider adding blocking dialog with explanation what happened.
                navigate(BottomNavigationDestination.Messages, clearBackStack = true)
            }
        }
    }

    suspend fun sendMessage(message: String, friend: User) {
        chatRemoteDataSource.sendMessage(newSendMessageEntity(message, userId, friendId))

        val updateConversationEntity = updateConversationEntity(message, userId)
        conversationRemoteDataSource.updateConversation(conversationId, updateConversationEntity)

        notificationRepository.sendNotification(friend, message)
    }

}