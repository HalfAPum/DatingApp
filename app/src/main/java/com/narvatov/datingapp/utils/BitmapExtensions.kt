package com.narvatov.datingapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream


val Bitmap.toBase64: String
    get() {
        val byteArrayOutputStream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

val String.toBitmap: Bitmap
    get() {
        val decodedString: ByteArray = Base64.decode(this, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }