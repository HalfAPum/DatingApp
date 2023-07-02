package com.narvatov.datingapp.ui.screen.onboarding

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.onborading.NotificationPermissionOnBoardingViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun NotificationPermissionOnBoarding(
    viewModel: NotificationPermissionOnBoardingViewModel = getViewModel()
) {
    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        val context = LocalContext.current
        val activity = context as Activity

        var text by remember { mutableStateOf("Hello enable notification please") }

        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            text = when {
                isGranted -> {
                    viewModel.permissionGranted()
                    "Permission granted"
                }

                activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    viewModel.permissionDeniedShowRationale()
                    "Permission denied show rationale"
                }

                else -> {
                    viewModel.permissionDenied()
                    "Permission denied"
                }
            }
        }

        Text(
            text = text,
            style = Typography.h4,
            modifier = Modifier.align(Alignment.Center)
        )

        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else { viewModel.processOnBoarding(ignore = true) }
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(
                text = "Permission"
            )
        }

        Button(
            onClick = { viewModel.processOnBoarding(ignore = true) },
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = "Skip"
            )
        }
    }
}