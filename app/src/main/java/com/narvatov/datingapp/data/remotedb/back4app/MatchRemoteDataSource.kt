package com.narvatov.datingapp.data.remotedb.back4app

import com.narvatov.datingapp.data.remotedb.RemoteDataSource
import com.narvatov.datingapp.data.remotedb.Schema
import com.parse.ParseObject
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.core.annotation.Factory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Factory
class MatchRemoteDataSource : RemoteDataSource() {

    suspend fun saveNewUser(userId: String) = IOOperation {
        val newUser = ParseObject(Schema.USER_TABLE).apply {
            put(Schema.USER_ID, userId)
        }

        newUser.saveSuspend()
    }

    suspend fun saveDeleteUser(userId: String) = IOOperation {
        val newUser = ParseObject(Schema.USER_TABLE).apply {
            put(Schema.USER_ID, userId)
        }

        newUser.deleteSuspend()
    }

    //Back4app coroutines extensions

    private suspend fun ParseObject.saveSuspend() = suspendCancellableCoroutine { continuation ->
        saveInBackground {
            if (it == null) {
                continuation.resume(Unit)
            } else {
                continuation.resumeWithException(it)
            }
        }
    }


    private suspend fun ParseObject.deleteSuspend() = suspendCancellableCoroutine { continuation ->
        deleteInBackground {
            if (it == null) {
                continuation.resume(Unit)
            } else {
                continuation.resumeWithException(it)
            }
        }
    }

}