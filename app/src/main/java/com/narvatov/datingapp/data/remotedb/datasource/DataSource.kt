package com.narvatov.datingapp.data.remotedb.datasource

import com.narvatov.datingapp.data.delegate.context.ContextDelegate
import com.narvatov.datingapp.data.delegate.context.IContextDelegate
import com.narvatov.datingapp.data.delegate.coroutine.CoroutineDelegate
import com.narvatov.datingapp.data.delegate.coroutine.ICoroutineDelegate
import com.narvatov.datingapp.data.delegate.dispatcher.DispatcherDelegate
import com.narvatov.datingapp.data.delegate.dispatcher.IDispatcherDelegate

abstract class DataSource : IDispatcherDelegate by DispatcherDelegate, IContextDelegate by ContextDelegate,
        ICoroutineDelegate by CoroutineDelegate