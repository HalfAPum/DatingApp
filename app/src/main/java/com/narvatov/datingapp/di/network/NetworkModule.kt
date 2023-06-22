package com.narvatov.datingapp.di.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .callTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single { GsonBuilder().setLenient().create() }

    single { GsonConverterFactory.create(get()) }

}

private const val OKHTTP_TIMEOUT = 5L