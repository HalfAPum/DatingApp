package com.narvatov.datingapp.ui.viewmodel.connect

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.data.repository.connect.ConnectRepository
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.loadAdMob
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.showAdMob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ConnectViewModel(private val connectRepository: ConnectRepository): ViewModel() {

    private val _newFriendStateFlow = MutableStateFlow<List<User>>(emptyList())
    val newFriendStateFlow = _newFriendStateFlow.asStateFlow()

    init {
        launchCatching { loadNewFriends() }
    }

    private suspend fun loadNewFriends() {
        val friends = connectRepository.getNonMatchedFriends(LOAD_SIZE)

        _newFriendStateFlow.emit(friends)

        delay(1000L)

        loadAdMob()
    }

    fun acceptFriend(friend: User) = launchCatching {
        connectRepository.createConversation(friend)

        connectRepository.markFriendMatched(friend)

        requestNewFriendBatch(friend)
    }

    fun rejectFriend(friend: User) = launchCatching {
        connectRepository.markFriendMatched(friend)

        requestNewFriendBatch(friend)
    }

    private suspend fun requestNewFriendBatch(friend: User) {
        if (newFriendStateFlow.value.firstOrNull() != friend) return

        showAd()

        loadNewFriends()
    }

    private fun showAd() {
        showAdMob()
    }

    companion object {
        private const val LOAD_SIZE = 4
    }

}