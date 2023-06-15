package com.narvatov.datingapp.di

import com.google.gson.GsonBuilder
import com.narvatov.datingapp.data.api.FCMApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    single { provideOkHttpClient(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    fun provideFCMApi(retrofit: Retrofit) = retrofit.create(FCMApi::class.java)

    single { provideFCMApi(get()) }

}

private const val BASE_URL = "https://fcm.googleapis.com/fcm/"