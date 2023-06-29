package com.narvatov.datingapp.ui.common.header

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun TextHeader(
    @StringRes
    textRes: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        HeaderBackButton(modifier = Modifier.align(Alignment.CenterVertically))

        Text(
            text = stringResource(textRes),
            style = Typography.h5,
            modifier = Modifier.padding(start = 30.dp)
        )
    }
}