package com.narvatov.datingapp.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun BaseProfile(user: User, additionalContent: @Composable () -> Unit = {}) {
    val maxHeight = LocalConfiguration.current.screenHeightDp.dp

    val twoThirdMaxHeight = (maxHeight / 3) * 2
    val overlappingPanelHeight = 40.dp
    val verticalScrollFlingAnimation = 10.dp

    Image(
        bitmap = user.photoBitmap,
        contentScale = ContentScale.Crop,
        contentDescription = user.photoDescription(),
        modifier = Modifier
            .fillMaxWidth()
            .height(twoThirdMaxHeight + overlappingPanelHeight + verticalScrollFlingAnimation),
    )

    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .verticalScroll(scrollState)
        .fillMaxWidth()
        .background(color = Color.Transparent)
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(twoThirdMaxHeight))

        Spacer(modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp)
            )
            .height(overlappingPanelHeight)
            .fillMaxWidth()
        )

        Column(modifier = Modifier.background(color = Color.White)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .defaultMinSize(minHeight = twoThirdMaxHeight)
        ) {
            Text(
                text = user.name,
                style = Typography.h6,
                modifier = Modifier.padding(top = 10.dp)
            )

            additionalContent()
        }
    }
}