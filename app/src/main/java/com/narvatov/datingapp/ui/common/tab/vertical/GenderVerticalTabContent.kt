package com.narvatov.datingapp.ui.common.tab.vertical

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.noRippleClickable
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.TextPrimaryColor
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun GenderVerticalTabContent(
    modifier: Modifier,
    onClick: () -> Unit,
    title: String,
    selected: Boolean
) {
    val color by animateColorAsState(if (selected) OnPrimaryColor else TextPrimaryColor)
    val iconColor by animateColorAsState(if (selected) Color.White else Color(0xFFADAFBB))
    Box(
        modifier = modifier.noRippleClickable { onClick() },
    ) {
        Text(
            text = title,
            style = Typography.body2,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.align(Alignment.Center)
        )

        Image(
            imageVector = Icons.Rounded.Check,
            contentDescription = stringResource(R.string.check),
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            colorFilter = ColorFilter.tint(color = iconColor)
        )
    }
}