package com.narvatov.datingapp.ui.common.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narvatov.datingapp.ui.theme.BackgroundGrey
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.HintGrey
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.utils.UnitCallback

@Composable
fun WideButtonSecondary(
    text: String,
    modifier: Modifier = Modifier,
    onClick: UnitCallback,
) {
    Box(modifier = modifier
        .height(56.dp)
        .fillMaxWidth()
        .clip(shape = Shapes.small)
        .background(color = BackgroundGrey, shape = Shapes.small)
        .clickable { onClick.invoke() }
        .border(width = 1.dp, color = BorderColor, shape = Shapes.small)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = HintGrey,
            modifier = Modifier.padding(vertical = 8.dp).align(Alignment.Center)
        )
    }
}