package com.narvatov.datingapp.data.repository

import com.narvatov.datingapp.data.preference.PreferencesDataStore
import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.model.local.user.NewUser
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.model.local.user.UserAuth
import com.narvatov.datingapp.model.local.user.UserAuth.Companion.toUserAuth
import com.narvatov.datingapp.model.remote.NewUserEntity.Companion.toNewUserEntity
import org.koin.core.annotation.Single

@Single
class UserRepository(
    private val preferencesDataStore: PreferencesDataStore,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userSessionRepository: UserSessionRepository,
) : Repository() {

    suspend fun signIn(userAuth: UserAuth) = IOOperation {
        val signedUser = userRemoteDataSource.getUser(userAuth)

        userSessionRepository.user = signedUser

        preferencesDataStore.saveUserPreferences(signedUser.toUserAuth())
    }

    suspend fun signUp(newUser: NewUser) = IOOperation {
        userRemoteDataSource.saveNewUser(newUser.toNewUserEntity())

        userRemoteDataSource.clearAllUsers()

        signIn(newUser.toUserAuth())
    }

    suspend fun getAllUsers() = userRemoteDataSource.getAllUsers()

    suspend fun getUser(userId: String) = getAllUsers().getOrDefault(userId, User.emptyUser)

    suspend fun deleteAccount() = IOOperation {

        logout()
    }

    suspend fun logout() = IOOperation {

        userRemoteDataSource.clearAllUsers()

        preferencesDataStore.clearUserPreferences()
    }

}