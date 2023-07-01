package com.narvatov.datingapp.ui.viewmodel.delegate.error

import android.content.Context
import com.narvatov.datingapp.utils.inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import timber.log.Timber

class ErrorDelegate : IErrorDelegate {

    override val context: Context by inject()

    override val errorSharedFlow = MutableSharedFlow<String?>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val exceptionHandlerEmitting = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)

        errorSharedFlow.tryEmit(exception.message)
    }

}