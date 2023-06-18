package com.narvatov.datingapp.ui.viewmodel.connect

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.connect.ConnectRepository
import com.narvatov.datingapp.model.local.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ConnectViewModel(private val connectRepository: ConnectRepository): ViewModel() {

    private val _newFriendStateFlow = MutableStateFlow<List<User>>(emptyList())
    val newFriendStateFlow = _newFriendStateFlow.asStateFlow()

    init {
        loadNewFriends()
    }

    private fun loadNewFriends() = launchCatching {
        val friends = connectRepository.get10NewFriends()

        _newFriendStateFlow.emit(friends)
    }

    fun acceptFriend(friend: User) = launchCatching {
        connectRepository.createConversation(friend)

        connectRepository.markFriendMatched(friend)
    }

    fun rejectFriend(friend: User) = launchCatching {
        connectRepository.markFriendMatched(friend)
    }

}