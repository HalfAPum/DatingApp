package com.narvatov.datingapp.ui.common

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.MainActivity.Companion.CAMERA_IMAGE_CODE
import com.narvatov.datingapp.ui.MainActivity.Companion.GALLERY_IMAGE_CODE
import com.narvatov.datingapp.ui.theme.Typography


@Composable
fun PhotoPickBottomSheet() {
    val activity = LocalContext.current as Activity

    Column(modifier = Modifier.fillMaxWidth().background(color = Color.LightGray)) {
        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.select_photo_from_gallery),
            style = Typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val getIntent = Intent(Intent.ACTION_GET_CONTENT)
                    getIntent.type = "image/*"

                    val pickIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    pickIntent.type = "image/*"

                    val chooserIntent = Intent.createChooser(
                        getIntent,
                        activity.getString(R.string.select_image)
                    )
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

                    activity.startActivityForResult(chooserIntent, GALLERY_IMAGE_CODE)
                }
                .padding(vertical = 10.dp)
                .padding(start = 20.dp)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.take_photo_now),
            style = Typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    activity.startActivityForResult(cameraIntent, CAMERA_IMAGE_CODE)
                }
                .padding(vertical = 10.dp)
                .padding(start = 20.dp)
        )

        Spacer(Modifier.height(20.dp))
    }
}