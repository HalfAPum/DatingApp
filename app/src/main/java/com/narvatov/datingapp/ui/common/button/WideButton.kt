package com.narvatov.datingapp.ui.common.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.utils.UnitCallback

@Composable
fun WideButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: UnitCallback
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = OnPrimaryColor,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}