package com.narvatov.datingapp.model.remote

import com.google.gson.annotations.SerializedName

data class SendNotification(
    @SerializedName("data")
    val notificationData: NotificationData,
    @SerializedName("registration_ids")
    val tokens: List<String>,
)

data class NotificationData(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("fcm_token")
    val fcmToken: String,
    @SerializedName("message")
    val message: String,
)