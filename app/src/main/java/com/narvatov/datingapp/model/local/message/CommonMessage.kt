package com.narvatov.datingapp.model.local.message

import android.icu.text.SimpleDateFormat
import java.util.Locale

interface CommonMessage {

    val sendTime: Long

    val sendTimeFormatted: String
        get() = SimpleDateFormat("hh:mm a", Locale.CANADA).format(sendTime)

}