package com.narvatov.datingapp.ui.common.square

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.utils.UnitCallback

@Composable
fun SquareIconButton(
    @DrawableRes
    iconRes: Int,
    modifier: Modifier = Modifier,
    onClick: UnitCallback,
) {
    Box(modifier = modifier
        .clip(shape = Shapes.small)
        .background(color = Color.Companion.White, shape = Shapes.small)
        .clickable { onClick.invoke() }
        .border(width = 1.dp, color = BorderColor, shape = Shapes.small)
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
