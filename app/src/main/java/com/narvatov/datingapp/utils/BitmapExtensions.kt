package com.narvatov.datingapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream


val Bitmap.toBase64: String
    get() {
        val byteArrayOutputStream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

val String.toImageBitmap: ImageBitmap
    get() = runCatching {
        val decodedString: ByteArray = Base64.decode(this, Base64.DEFAULT)

        val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        bitmap.asImageBitmap()
    }.getOrDefault(ImageBitmap(1,1))