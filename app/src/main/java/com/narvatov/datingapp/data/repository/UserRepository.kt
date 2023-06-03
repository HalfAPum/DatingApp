package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.preference.PreferencesDataStore
import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.model.local.NewUser
import com.narvatov.datingapp.model.local.UserAuth
import com.narvatov.datingapp.model.local.UserAuth.Companion.toUserAuth
import com.narvatov.datingapp.model.remote.NewUserEntity.Companion.toNewUserEntity
import org.koin.core.annotation.Factory

@Factory
class UserRepository(
    private val preferencesDataStore: PreferencesDataStore,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userSessionRepository: UserSessionRepository,
) : Repository() {

    suspend fun signIn(userAuth: UserAuth) = IOOperation {
        userSessionRepository.user = userRemoteDataSource.getUser(userAuth)

        preferencesDataStore.saveUserPreferences(userAuth)
    }

    suspend fun signUp(newUser: NewUser) = IOOperation {
        userRemoteDataSource.saveNewUser(newUser.toNewUserEntity())

        signIn(newUser.toUserAuth())
    }

    suspend fun deleteAccount() = IOOperation {

    }

    suspend fun logout() = IOOperation {
        preferencesDataStore.saveUserPreferences(UserAuth.emptyUser)
    }

}