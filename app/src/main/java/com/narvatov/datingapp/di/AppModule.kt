package com.narvatov.datingapp.di

import com.halfapum.general.coroutines.Dispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    single { Dispatcher() }

}