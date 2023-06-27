package com.narvatov.datingapp.di.network

import com.google.gson.GsonBuilder
import com.narvatov.datingapp.data.api.fcm.FCMApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val fcmNetworkModule = module {

    single(named(FCM_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(FCM_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    fun provideFCMApi(retrofit: Retrofit) = retrofit.create(FCMApi::class.java)

    single { provideFCMApi(get(qualifier = named(FCM_RETROFIT))) }

}

private const val FCM_BASE_URL = "https://fcm.googleapis.com/fcm/"
private const val FCM_RETROFIT = "FCMRetrofit"