package com.narvatov.datingapp.ui.viewmodel.messages

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.UserRepository
import com.narvatov.datingapp.model.local.User
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MessagesViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<Collection<User>>(emptyList())
    val usersStateFlow = _usersStateFlow.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() = launchCatching {
        val users = userRepository.getAllUsers()

        _usersStateFlow.emit(users.values)
    }

}