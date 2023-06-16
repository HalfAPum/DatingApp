package com.narvatov.datingapp.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.narvatov.datingapp.data.remotedb.throwEmptyUserPassedToProfileScreen
import com.narvatov.datingapp.data.remotedb.throwNoUserPassedToProfileScreen
import com.narvatov.datingapp.model.local.user.User

@Composable
fun FriendProfile(friend: User?) {
    val context = LocalContext.current

    when {
        friend == null -> throwNoUserPassedToProfileScreen(context)
        friend.isEmpty -> throwEmptyUserPassedToProfileScreen(context)
        else -> BaseProfile(friend)
    }
}