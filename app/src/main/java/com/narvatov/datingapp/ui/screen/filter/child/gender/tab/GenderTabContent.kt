package com.narvatov.datingapp.ui.screen.filter.child.gender.tab

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.narvatov.datingapp.ui.noRippleClickable
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.TextPrimaryColor
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun GenderTabContent(
    modifier: Modifier,
    onClick: () -> Unit,
    title: String,
    selected: Boolean
) {
    val color by animateColorAsState(if (selected) OnPrimaryColor else TextPrimaryColor)
    Box(
        modifier = modifier.noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = Typography.body2,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}