package com.narvatov.datingapp.ui.screen.connect

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.utils.SuspendUnitCallback
import kotlinx.coroutines.launch

@Composable
fun ConnectActionButton(
    imageVector: ImageVector,
    backgroundColor: Color,
    onClick: SuspendUnitCallback,
) {
    val swipeScope = rememberCoroutineScope()
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        modifier = Modifier.size(width = 120.dp, height = 40.dp),
        onClick = {
            swipeScope.launch { onClick.invoke() }
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
                colorFilter = ColorFilter.tint(color = Color.White)
            )
        }
    }
}