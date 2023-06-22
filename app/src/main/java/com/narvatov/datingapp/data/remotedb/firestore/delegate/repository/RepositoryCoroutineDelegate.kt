package com.narvatov.datingapp.data.remotedb.firestore.delegate.repository

import com.narvatov.datingapp.data.remotedb.firestore.delegate.dispatcher.DispatcherDelegate.IODispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

object RepositoryCoroutineDelegate : IRepositoryCoroutineDelegate {

    override val repositoryScope = CoroutineScope(IODispatcher + SupervisorJob())

}