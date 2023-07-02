package com.narvatov.datingapp.ui.common.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Shapes

@Composable
fun GenderTabIndicator(
    modifier: Modifier,
    indicatorOffsetX: Dp = 0.dp,
    indicatorOffsetY: Dp = 0.dp,
) {
    Box(
        modifier = modifier
            .offset(x = indicatorOffsetX, y = indicatorOffsetY)
            .clip(Shapes.small)
            .background(PrimaryColor)
    )
}