package com.narvatov.datingapp.ui.viewmodel.delegate.progress

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow

interface IProgressDelegate {

    val progressStateFlow: MutableStateFlow<Boolean>

    fun ViewModel.showProgress() = launch {
        progressStateFlow.emit(true)
    }

    fun ViewModel.hideProgress() = launch {
        progressStateFlow.emit(false)
    }

}