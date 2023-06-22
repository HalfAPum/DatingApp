package com.narvatov.datingapp.data.remotedb

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.narvatov.datingapp.R
import com.narvatov.datingapp.delegate.common.context.IContextDelegate

context (IContextDelegate)
fun throwNoSuchUserException(): Nothing = throw NoSuchElementException(
    context.getString(R.string.invalid_credentials)
)

context (IContextDelegate)
fun throwUserAlreadyExists(): Nothing = throw CloneNotSupportedException(
    context.getString(R.string.user_with_such_email_already_exists)
)

context (IContextDelegate)
fun throwNoConversationId(): Nothing = throw IllegalArgumentException(
    context.getString(R.string.conversation_between_this_users_doesn_t_exist)
)

@Composable
fun throwNoUserPassedToProfileScreen(): Nothing {
    val context = LocalContext.current

    throw IllegalArgumentException(
        context.getString(R.string.nullable_friend_profile_parameter)
    )
}

@Composable
fun throwEmptyUserPassedToProfileScreen(): Nothing {
    val context = LocalContext.current

    throw IllegalArgumentException(
        context.getString(R.string.empty_friend_profile_parameter)
    )
}

context (IContextDelegate)
fun throwEmptyFCMToken(): Nothing = throw IllegalArgumentException(
    context.getString(R.string.no_fcmtoken_exception)
)