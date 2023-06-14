package com.narvatov.datingapp.data.delegate.repository

import com.narvatov.datingapp.data.delegate.dispatcher.DispatcherDelegate.IODispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

object RepositoryCoroutineDelegate : IRepositoryCoroutineDelegate {

    override val repositoryScope = CoroutineScope(IODispatcher + SupervisorJob())

}