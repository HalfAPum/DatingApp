package com.narvatov.datingapp.ui.screen.signup

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.model.local.notification.NotificationPreference
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.signup.NotificationPermissionOnBoardingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun NotificationPermissionOnBoarding(
    viewModel: NotificationPermissionOnBoardingViewModel = getViewModel()
) {
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        val context = LocalContext.current
        val activity = context as Activity

        var text by remember { mutableStateOf("Hello enable notification please") }

        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            scope.launch {
                text = when {
                    isGranted -> {
                        viewModel.saveNotificationPreference(NotificationPreference.GRANTED)
                        viewModel.processOnBoarding()
                        "Permission granted"
                    }

                    activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                        viewModel.saveNotificationPreference(NotificationPreference.SHOW_RATIONALE)
                        "Permission denied show rationale"
                    }

                    else -> {
                        viewModel.saveNotificationPreference(NotificationPreference.DENIED)
                        viewModel.processOnBoarding()
                        "Permission denied"
                    }
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
                } else { viewModel.processOnBoarding(ignoreNotificationOnBoarding = true) }
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Text(
                text = "Permission"
            )
        }

        Button(
            onClick = { viewModel.processOnBoarding(ignoreNotificationOnBoarding = true) },
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Text(
                text = "Skip"
            )
        }
    }
}