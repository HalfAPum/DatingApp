package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.api.FCMApi
import com.narvatov.datingapp.model.remote.SendNotification
import org.koin.core.annotation.Factory

@Factory
class NotificationRepository(
    private val fcmApi: FCMApi,
) : Repository() {

    suspend fun sendNotification(message: SendNotification) = IOOperation {
        fcmApi.sendNotification(message)
    }

}