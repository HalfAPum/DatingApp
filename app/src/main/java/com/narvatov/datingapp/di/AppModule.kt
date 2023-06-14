package com.narvatov.datingapp.di

import com.halfapum.general.coroutines.Dispatcher
import com.narvatov.datingapp.data.remotedb.datasource.ChatRemoteDataSource
import com.narvatov.datingapp.data.remotedb.datasource.ConversationRemoteDataSource
import com.narvatov.datingapp.data.repository.messages.chat.ChatRepository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule = module {

    single { Dispatcher() }

    factory {
        ChatRepository(
            get<UserSessionRepository>().user.id,
            get(),
            get(parameters = { parametersOf(get()) }),
            get()
        )
    }

    factory {
        ChatRemoteDataSource(
            get<UserSessionRepository>().user.id,
            get(),
        )
    }

    factory {
        ConversationRemoteDataSource(
            get<UserSessionRepository>().user.id,
        )
    }

}