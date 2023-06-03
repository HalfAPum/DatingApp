package com.narvatov.datingapp.ui.screen.sign

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.narvatov.datingapp.ui.theme.RedError
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.ErrorViewModel

@Composable
fun ErrorText(
    errorViewModel: ErrorViewModel,
    modifier: Modifier,
) {
    val errorMessage by errorViewModel.errorSharedFlow.collectAsState(null)

    if (errorMessage != null) {
        Text(
            text = errorMessage!!,
            color = RedError,
            textAlign = TextAlign.Center,
            style = Typography.h6,
            modifier = modifier
        )
    }
}