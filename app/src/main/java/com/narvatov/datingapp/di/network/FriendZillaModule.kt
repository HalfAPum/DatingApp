package com.narvatov.datingapp.di.network

import com.google.gson.GsonBuilder
import com.narvatov.datingapp.data.api.match.MatchApi
import com.narvatov.datingapp.data.api.user.UserApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val friendZillaNetworkModule = module {

    single(named(FRIEND_ZILLA_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(FRIEND_ZILLA_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    fun provideUserApi(retrofit: Retrofit) = retrofit.create(UserApi::class.java)

    single { provideUserApi(get(qualifier = named(FRIEND_ZILLA_RETROFIT))) }

    fun provideMatchApi(retrofit: Retrofit) = retrofit.create(MatchApi::class.java)

    single { provideMatchApi(get(qualifier = named(FRIEND_ZILLA_RETROFIT))) }

}

private const val FRIEND_ZILLA_BASE_URL = "http://10.0.2.2:8080/v1/"
private const val FRIEND_ZILLA_RETROFIT = "FriendZillaRetrofit"
