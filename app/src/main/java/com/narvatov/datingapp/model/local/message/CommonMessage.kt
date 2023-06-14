package com.narvatov.datingapp.model.local.message

import android.icu.text.SimpleDateFormat
import java.util.Date

interface CommonMessage {

    val sendDate: Date

    val sendTime: String
        get() = SimpleDateFormat("hh:mm a").format(sendDate)

}