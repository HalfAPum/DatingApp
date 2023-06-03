package com.narvatov.datingapp.data.remotedb

import android.content.Context
import com.narvatov.datingapp.R

fun throwNoSuchUserException(context: Context): Nothing = throw NoSuchElementException(
    context.getString(R.string.invalid_credentials)
)