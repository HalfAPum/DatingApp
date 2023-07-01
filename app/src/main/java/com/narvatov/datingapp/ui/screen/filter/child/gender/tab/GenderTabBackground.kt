package com.narvatov.datingapp.ui.screen.filter.child.gender.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.ui.noRippleClickable
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.Shapes

@Composable
fun GenderTabBackground(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = Color.White, shape = Shapes.small)
            .border(width = 1.dp, color = BorderColor, shape = Shapes.small)
            .noRippleClickable { onClick() },
    )
}