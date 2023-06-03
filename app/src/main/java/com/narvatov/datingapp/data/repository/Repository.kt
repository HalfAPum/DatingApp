package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.delegate.dispatcher.DispatcherDelegate
import com.narvatov.datingapp.data.delegate.dispatcher.IDispatcherDelegate

abstract class Repository : IDispatcherDelegate by DispatcherDelegate