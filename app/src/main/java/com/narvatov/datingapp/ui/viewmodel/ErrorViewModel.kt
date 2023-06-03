package com.narvatov.datingapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narvatov.datingapp.utils.inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class ErrorViewModel : ViewModel() {

    protected val context: Context by inject()

    protected val _errorSharedFlow = MutableSharedFlow<String?>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val errorSharedFlow = _errorSharedFlow.asSharedFlow()

    private val exceptionHandlerEmitting = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)

        _errorSharedFlow.tryEmit(exception.message)
    }

    fun onFieldValueChanged() {
        launchPrintingError {
            _errorSharedFlow.emit(null)
        }
    }

    protected fun launchPrintingError(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + exceptionHandlerEmitting, start, block)

}