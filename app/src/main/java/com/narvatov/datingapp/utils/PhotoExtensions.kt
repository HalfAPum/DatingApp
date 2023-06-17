package com.narvatov.datingapp.utils

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import com.narvatov.datingapp.R

fun CameraIntent() = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

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