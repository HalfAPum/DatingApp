package com.narvatov.datingapp.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.narvatov.datingapp.model.local.user.User

@Composable
fun BaseProfile(user: User) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(0.34F)) {
            Image(
                bitmap = user.photoBitmap,
                contentScale = ContentScale.Crop,
                contentDescription = user.photoDescription(),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}