package com.narvatov.datingapp.ui.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
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
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.PhotoViewModel
import com.narvatov.datingapp.utils.CameraIntent
import com.narvatov.datingapp.utils.GalleryIntent

@Composable
fun PhotoPickBottomSheet(viewModel: PhotoViewModel) {
    val selectPhotoLauncher = rememberLauncherForActivityResult(
        contract = StartActivityForResult(),
        onResult = viewModel::onPhotoSelected,
    )

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = StartActivityForResult(),
        onResult = viewModel::onPhotoTook,
    )

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth().background(color = Color.LightGray)) {
        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.select_photo_from_gallery),
            style = Typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectPhotoLauncher.launch(GalleryIntent(context)) }
                .padding(vertical = 10.dp)
                .padding(start = 20.dp)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.take_photo_now),
            style = Typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { takePhotoLauncher.launch(CameraIntent()) }
                .padding(vertical = 10.dp)
                .padding(start = 20.dp)
        )

        Spacer(Modifier.height(20.dp))
    }
}