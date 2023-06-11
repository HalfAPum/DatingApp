package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.delegate.context.ContextDelegate
import com.narvatov.datingapp.data.delegate.context.IContextDelegate
import com.narvatov.datingapp.data.delegate.dispatcher.DispatcherDelegate
import com.narvatov.datingapp.data.delegate.dispatcher.IDispatcherDelegate
import okio.IOException
import retrofit2.HttpException

abstract class Repository : IDispatcherDelegate by DispatcherDelegate, IContextDelegate by ContextDelegate {

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