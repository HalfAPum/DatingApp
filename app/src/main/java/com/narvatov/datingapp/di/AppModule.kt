package com.narvatov.datingapp.di

import com.halfapum.general.coroutines.Dispatcher
import com.narvatov.datingapp.data.remotedb.firestore.ChatRemoteDataSource
import com.narvatov.datingapp.data.repository.messages.chat.ChatRepository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.domain.chat.ChatMessagesFlowUseCase
import com.narvatov.datingapp.domain.chat.SendMessageUseCase
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule = module {

    single { Dispatcher() }

    factory {
        ChatRepository(
            get<UserSessionRepository>().user.id,
            get(),
            get(parameters = { parametersOf(get()) }),
            get(),
            get()
        )
    }

    factory {
        SendMessageUseCase(it.get())
    }

    factory {
        ChatMessagesFlowUseCase(it.get())
    }

    factory {
        ChatRemoteDataSource(
            get<UserSessionRepository>().user.id,
            get(),
        )
    }

}