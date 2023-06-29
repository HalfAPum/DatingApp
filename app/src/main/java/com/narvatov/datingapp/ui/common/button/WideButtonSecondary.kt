package com.narvatov.datingapp.ui.common.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narvatov.datingapp.ui.theme.BackgroundGrey
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.HintGrey
import com.narvatov.datingapp.utils.UnitCallback

@Composable
fun WideButtonSecondary(
    text: String,
    modifier: Modifier = Modifier,
    onClick: UnitCallback,
) {
    Button(
        onClick = onClick,
        border = BorderStroke(width = 1.dp, color = BorderColor),
        colors = buttonColors(backgroundColor = BackgroundGrey),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = HintGrey,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}