package com.narvatov.datingapp.data.delegate.coroutine

import com.narvatov.datingapp.data.delegate.dispatcher.DispatcherDelegate.IODispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

object CoroutineDelegate : ICoroutineDelegate {

    override val repositoryScope = CoroutineScope(IODispatcher + SupervisorJob())

}