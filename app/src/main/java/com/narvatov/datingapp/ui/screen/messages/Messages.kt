package com.narvatov.datingapp.ui.screen.messages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.narvatov.datingapp.ui.viewmodel.messages.MessagesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Messages(
    viewModel: MessagesViewModel = getViewModel()
) {
    val users = viewModel.usersStateFlow.collectAsState()
}