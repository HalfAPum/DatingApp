package com.narvatov.datingapp.model.local.message.send

import java.util.Date

data class SendMessage(
    val text: String,
    val senderId: String,
    val receiverId: String,
    val sendDate: Date = Date(),
) {

    val timestamp: String
        get() = sendDate.time.toString()

}