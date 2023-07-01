package com.narvatov.datingapp.ui.viewmodel.delegate.error

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface IErrorDelegate {

    val context: Context

    val errorSharedFlow: MutableSharedFlow<String?>

    val exceptionHandlerEmitting: CoroutineExceptionHandler

    fun ViewModel.onFieldValueChanged() {
        launchPrintingError {
            errorSharedFlow.emit(null)
        }
    }

    fun ViewModel.launchPrintingError(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context + exceptionHandlerEmitting, start, block)

}