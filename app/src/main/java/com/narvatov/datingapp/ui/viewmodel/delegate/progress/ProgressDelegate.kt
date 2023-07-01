package com.narvatov.datingapp.ui.viewmodel.delegate.progress

import kotlinx.coroutines.flow.MutableStateFlow

class ProgressDelegate(private val configHideAttempts: Int = NORMAL_HIDE_ATTEMPTS) : IProgressDelegate {

    private var attemptsCounter = NORMAL_HIDE_ATTEMPTS

    override val progressStateFlow = MutableStateFlow(false)

    override fun hideProgress() {
        if (attemptsCounter == configHideAttempts) {
            super.hideProgress()
        } else {
            attemptsCounter++
        }
    }

    companion object {
        private const val NORMAL_HIDE_ATTEMPTS = 1
    }

}