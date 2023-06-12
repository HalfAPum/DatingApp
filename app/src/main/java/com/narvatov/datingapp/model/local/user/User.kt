package com.narvatov.datingapp.model.local.user

import android.graphics.Bitmap
import com.narvatov.datingapp.utils.toBitmap

data class User(
    val id: String,
    val email: String,
    val password: String,
    val name: String,
    val photoBase64: String,
) {

    val photoBitmap: Bitmap by lazy { photoBase64.toBitmap }

    companion object {
        val emptyUser = User("","", "", "", "")
    }

}
