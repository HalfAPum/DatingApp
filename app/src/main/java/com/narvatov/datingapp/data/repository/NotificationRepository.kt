package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.api.fcm.FCMApi
import com.narvatov.datingapp.data.remotedb.throwEmptyFCMToken
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.model.remote.NotificationData
import com.narvatov.datingapp.model.remote.SendNotification
import org.koin.core.annotation.Factory

@Factory
class NotificationRepository(
    private val fcmApi: FCMApi,
    private val userSessionRepository: UserSessionRepository,
) : Repository() {

    suspend fun sendNotification(friend: User, message: String) = IOOperation {
        if (friend.fcmToken == null) return@IOOperation

        val sendNotification = SendNotification(
            NotificationData(
                userSessionRepository.user.id,
                userSessionRepository.user.name,
                userSessionRepository.user.fcmToken ?: throwEmptyFCMToken(),
                message
            ),
            tokens = listOf(friend.fcmToken!!)
        )

        fcmApi.sendNotification(sendNotification)
    }

}