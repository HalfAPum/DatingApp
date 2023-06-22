package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.remotedb.firestore.delegate.dispatcher.DispatcherDelegate
import com.narvatov.datingapp.data.remotedb.firestore.delegate.dispatcher.IDispatcherDelegate
import com.narvatov.datingapp.data.remotedb.firestore.delegate.repository.IRepositoryCoroutineDelegate
import com.narvatov.datingapp.data.remotedb.firestore.delegate.repository.RepositoryCoroutineDelegate
import com.narvatov.datingapp.delegate.common.context.ContextDelegate
import com.narvatov.datingapp.delegate.common.context.IContextDelegate
import okio.IOException
import retrofit2.HttpException

abstract class Repository : IDispatcherDelegate by DispatcherDelegate, IContextDelegate by ContextDelegate,
    IRepositoryCoroutineDelegate by RepositoryCoroutineDelegate {

    suspend fun <T> errorCall(postMessage: Int, block: suspend () -> T): T {
        return try {
            block()
        } catch (e: HttpException) {
            throw IOException(context.getString(postMessage))
        } catch (e: NullPointerException) {
            throw IOException(context.getString(postMessage))
        }
    }

}