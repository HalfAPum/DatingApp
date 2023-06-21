package com.narvatov.datingapp.di

import com.google.gson.GsonBuilder
import com.narvatov.datingapp.data.api.fcm.FCMApi
import com.narvatov.datingapp.data.api.match.MatchApi
import com.narvatov.datingapp.data.api.user.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //todo settimeout
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    single { provideOkHttpClient(get()) }

    single(named(FCM_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(FCM_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    single(named(FRIEND_ZILLA_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(FRIEND_ZILLA_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    fun provideFCMApi(retrofit: Retrofit) = retrofit.create(FCMApi::class.java)

    single { provideFCMApi(get(qualifier = named(FCM_RETROFIT))) }

    fun provideUserApi(retrofit: Retrofit) = retrofit.create(UserApi::class.java)

    single { provideUserApi(get(qualifier = named(FRIEND_ZILLA_RETROFIT))) }

    fun provideMatchApi(retrofit: Retrofit) = retrofit.create(MatchApi::class.java)

    single { provideMatchApi(get(qualifier = named(FRIEND_ZILLA_RETROFIT))) }

}

private const val FCM_BASE_URL = "https://fcm.googleapis.com/fcm/"
private const val FCM_RETROFIT = "FCMRetrofit"

private const val FRIEND_ZILLA_BASE_URL = "http://10.0.2.2:8080/v1/"
private const val FRIEND_ZILLA_RETROFIT = "FriendZillaRetrofit"