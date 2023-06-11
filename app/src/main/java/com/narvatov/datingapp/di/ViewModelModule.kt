package com.narvatov.datingapp.di

import com.narvatov.datingapp.ui.viewmodel.messages.chat.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ChatViewModel(get(), get(), get()) }

}