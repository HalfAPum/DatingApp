package com.narvatov.datingapp.data.delegate.dispatcher

import com.halfapum.general.coroutines.Dispatcher
import com.narvatov.datingapp.utils.inject

object DispatcherDelegate : IDispatcherDelegate {

    override val dispatcher: Dispatcher by inject()

}