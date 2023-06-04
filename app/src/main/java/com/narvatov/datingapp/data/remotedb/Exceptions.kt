package com.narvatov.datingapp.data.remotedb

import android.content.Context
import com.narvatov.datingapp.R

fun throwNoSuchUserException(context: Context): Nothing = throw NoSuchElementException(
    context.getString(R.string.invalid_credentials)
)

fun throwUserDoesNotExist(context: Context): Nothing = throw NoSuchElementException(
    context.getString(R.string.requested_user_does_not_exist)
)

fun throwNoUserId(context: Context): Nothing = throw IllegalArgumentException(
    context.getString(R.string.no_user_id_was_provided)
)