package com.narvatov.datingapp.ui.viewmodel.connect

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.connect.ConnectRepository
import com.narvatov.datingapp.data.repository.user.UserRepository
import com.narvatov.datingapp.model.local.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ConnectViewModel(
    private val userRepository: UserRepository,
    private val connectRepository: ConnectRepository,
): ViewModel() {

    private val _usersStateFlow = MutableStateFlow<List<User>>(emptyList())
    val usersStateFlow = _usersStateFlow.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() = launchCatching {
        val users = userRepository.getAllUsers()

        _usersStateFlow.emit(users.values.toList())
    }

    fun createConversation(friend: User) = launchCatching {
        connectRepository.createConversation(friend)
    }
}