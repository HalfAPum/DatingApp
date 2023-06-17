package com.narvatov.datingapp.ui.screen.profile

import androidx.compose.runtime.Composable
import com.narvatov.datingapp.data.remotedb.throwEmptyUserPassedToProfileScreen
import com.narvatov.datingapp.data.remotedb.throwNoUserPassedToProfileScreen
import com.narvatov.datingapp.model.local.user.User

@Composable
fun FriendProfile(friend: User?) {
    when {
        friend == null -> throwNoUserPassedToProfileScreen()
        friend.isEmpty -> throwEmptyUserPassedToProfileScreen()
        else -> BaseProfile(friend)
    }
}