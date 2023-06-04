package com.narvatov.datingapp.data.delegate.coroutine

import com.halfapum.general.coroutines.launchCatching
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface ICoroutineDelegate {

    val repositoryScope: CoroutineScope

    fun launchCatching(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = repositoryScope.launchCatching(context, start, block)

}