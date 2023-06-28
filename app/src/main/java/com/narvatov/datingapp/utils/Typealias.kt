package com.narvatov.datingapp.utils

typealias GenericCallback<T> = (T) -> Unit

typealias UnitCallback = () -> Unit

typealias SuspendUnitCallback = suspend () -> Unit