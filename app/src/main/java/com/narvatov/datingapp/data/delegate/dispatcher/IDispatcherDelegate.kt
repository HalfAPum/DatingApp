package com.narvatov.datingapp.data.delegate.dispatcher

import com.halfapum.general.coroutines.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

interface IDispatcherDelegate {

    val dispatcher: Dispatcher

    val IODispatcher: CoroutineDispatcher
        get() = dispatcher.IO
    val DefaultDispatcher: CoroutineDispatcher
        get() = dispatcher.Default
    val UnconfinedDispatcher: CoroutineDispatcher
        get() = dispatcher.Unconfined

    suspend fun <T> IOOperation(
        block: suspend CoroutineScope.() -> T
    ) = withContext(IODispatcher) { block() }

}