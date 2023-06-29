package com.narvatov.datingapp.ui.common.header

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R

@Composable
fun HeaderBackButton(modifier: Modifier = Modifier) {
    val activity = LocalContext.current as ComponentActivity

    Image(
        painter = painterResource(R.drawable.back),
        contentDescription = stringResource(R.string.back_button),
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .clickable {
                activity.onBackPressedDispatcher.onBackPressed()
            }
    )
}