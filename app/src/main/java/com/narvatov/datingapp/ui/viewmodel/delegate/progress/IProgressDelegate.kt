package com.narvatov.datingapp.ui.viewmodel.delegate.progress

import kotlinx.coroutines.flow.MutableStateFlow

interface IProgressDelegate {

    val progressStateFlow: MutableStateFlow<Boolean>

    fun showProgress() {
        progressStateFlow.tryEmit(true)
    }

    fun hideProgress() {
        progressStateFlow.tryEmit(false)
    }

}