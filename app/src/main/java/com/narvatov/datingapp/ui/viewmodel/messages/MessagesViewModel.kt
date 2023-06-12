package com.narvatov.datingapp.ui.viewmodel.messages

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.UserRepository
import com.narvatov.datingapp.model.local.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MessagesViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _usersStateFlow = MutableStateFlow<List<User>>(emptyList())
    val usersStateFlow = _usersStateFlow.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() = launchCatching {
        val users = userRepository.getAllUsers()

        _usersStateFlow.emit(users.values.toList())
    }

}