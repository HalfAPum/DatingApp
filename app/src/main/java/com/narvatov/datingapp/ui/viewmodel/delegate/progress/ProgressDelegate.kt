package com.narvatov.datingapp.ui.viewmodel.delegate.progress

import kotlinx.coroutines.flow.MutableStateFlow

class ProgressDelegate : IProgressDelegate {

    override val progressStateFlow = MutableStateFlow(false)

}