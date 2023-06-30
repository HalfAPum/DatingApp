package com.narvatov.datingapp.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.narvatov.datingapp.R

fun CameraIntent(context: Context): Intent {
    return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
        putExtra(MediaStore.EXTRA_OUTPUT, PhotoFileUri.getInitialized(context))
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }
}

object PhotoFileUri {

    var photoTookUri: Uri? = null
        private set

    fun getInitialized(context: Context): Uri? {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        photoTookUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        return photoTookUri
    }
}
val Context.fileUri: Uri?
    get() {
        val values = ContentValues(1)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

fun GalleryIntent(context: Context): Intent {
    val getIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "image/*"
    }

    val pickIntent = Intent(
        Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    ).apply {
        type = "image/*"
    }

    return Intent.createChooser(
        getIntent,
        context.getString(R.string.select_image)
    ).apply {
        putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
    }
}