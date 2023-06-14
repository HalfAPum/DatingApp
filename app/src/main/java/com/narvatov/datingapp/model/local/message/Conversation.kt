package com.narvatov.datingapp.model.local.message

import android.graphics.Bitmap
import com.narvatov.datingapp.utils.toBitmap
import java.util.Date

data class Conversation(
    val friendId: String,
    val photoBase64: String,
    val friendName: String,
    val lastText: String,
    val isUserSend: Boolean,
    override val sendDate: Date,
) : CommonMessage {

    val photoBitmap: Bitmap by lazy { photoBase64.toBitmap }

}