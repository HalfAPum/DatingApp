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
        launchCatching { loadNewFriends(INITIAL_LOAD_SIZE) }
    }

    private suspend fun loadNewFriends(limit: Int) {
        val friends = connectRepository.getNonMatchedFriends(limit)

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

        loadNewFriends(CONTINUOUS_LOAD_SIZE)
    }

    private fun showAd() {
        showAdMob()
    }

    companion object {
        private const val INITIAL_LOAD_SIZE = 4
        private const val CONTINUOUS_LOAD_SIZE = 3
    }
}