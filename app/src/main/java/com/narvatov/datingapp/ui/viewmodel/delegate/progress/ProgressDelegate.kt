package com.narvatov.datingapp.ui.viewmodel.delegate.progress

import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.Factory

@Factory
class ProgressDelegate : IProgressDelegate {

    override val progressStateFlow = MutableStateFlow(false)

}