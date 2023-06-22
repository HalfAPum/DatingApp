package com.narvatov.datingapp.data.remotedb.firestore.delegate.coroutine

import com.narvatov.datingapp.data.remotedb.firestore.delegate.dispatcher.DispatcherDelegate.IODispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

object CoroutineDelegate : ICoroutineDelegate {

    override val repositoryScope = CoroutineScope(IODispatcher + SupervisorJob())

}