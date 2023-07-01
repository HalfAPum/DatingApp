package com.narvatov.datingapp.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.ui.noRippleClickable
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.IProgressDelegate

@Composable
fun LoaderBox(
    progressDelegateViewModel: IProgressDelegate,
    content: @Composable BoxScope.() -> Unit,
) {
    Box {
        content.invoke(this)

        val showProgress by progressDelegateViewModel.progressStateFlow.collectAsState()

        BackHandler(showProgress) {
            progressDelegateViewModel.hideProgress()
        }

        AnimatedVisibility(
            visible = showProgress,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.4F))
                    .noRippleClickable {}
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp)
                )
            }
        }
    }
}