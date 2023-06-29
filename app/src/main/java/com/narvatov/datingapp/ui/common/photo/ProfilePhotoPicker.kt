package com.narvatov.datingapp.ui.common.photo

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.showPhotoBottomSheet
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Shapes

@Composable
fun ProfilePhotoPicker(
    modifier: Modifier = Modifier,
    photoBitmap: Bitmap? = null,
) {
    val focusManager = LocalFocusManager.current
    Box(modifier = modifier.size(128.dp)) {
        Box(modifier = Modifier
            .size(120.dp)
            .align(Alignment.TopStart)
            .clip(Shapes.large)
            .background(color = Color.LightGray)
            .clickable {
                focusManager.clearFocus()
                showPhotoBottomSheet()
            }
        ) {
            when {
                photoBitmap != null -> {
                    Image(
                        bitmap = photoBitmap.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.profile_image),
                        modifier = Modifier.fillMaxSize().align(Alignment.Center),
                    )
                }
                else -> {
                    Image(
                        imageVector = Icons.Rounded.PhotoCamera,
                        contentDescription = stringResource(R.string.upload_image),
                        modifier = Modifier.size(60.dp).align(Alignment.Center)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomEnd)
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
                .background(color = PrimaryColor, shape = CircleShape)
                .clip(CircleShape)
                .clickable {
                    focusManager.clearFocus()
                    showPhotoBottomSheet()
                }
        ) {
            Icon(
                imageVector = Icons.Rounded.PhotoCamera,
                contentDescription = stringResource(R.string.upload_image),
                modifier = Modifier.size(16.dp).align(Alignment.Center),
                tint = OnPrimaryColor,
            )
        }
    }
}