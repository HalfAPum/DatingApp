package com.narvatov.datingapp.data.remotedb

import android.content.Context
import com.narvatov.datingapp.R

fun throwNoSuchUserException(context: Context): Nothing = throw NoSuchElementException(
    context.getString(R.string.invalid_credentials)
)

fun throwNoUserId(context: Context): Nothing = throw IllegalArgumentException(
    context.getString(R.string.no_user_id_was_provided)
)

fun throwNoConversationId(context: Context): Nothing = throw IllegalArgumentException(
    context.getString(R.string.conversation_between_this_users_doesn_t_exist)
)

fun throwNoUserPassedToProfileScreen(context: Context): Nothing = throw IllegalArgumentException(
    context.getString(R.string.nullable_friend_profile_parameter)
)

fun throwEmptyUserPassedToProfileScreen(context: Context): Nothing = throw IllegalArgumentException(
    context.getString(R.string.empty_friend_profile_parameter)
)