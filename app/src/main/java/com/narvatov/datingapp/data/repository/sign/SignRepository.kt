package com.narvatov.datingapp.data.repository.sign

import com.narvatov.datingapp.data.preference.PreferencesDataStore
import com.narvatov.datingapp.data.remotedb.back4app.MatchRemoteDataSource
import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.data.remotedb.throwUserAlreadyExists
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.NewUser
import com.narvatov.datingapp.model.local.user.UserAuth
import com.narvatov.datingapp.model.local.user.UserAuth.Companion.toUserAuth
import com.narvatov.datingapp.model.remote.NewUserEntity.Companion.toNewUserEntity
import org.koin.core.annotation.Factory

@Factory
class SignRepository(
    private val matchRemoteDataSource: MatchRemoteDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userSessionRepository: UserSessionRepository,
) : Repository() {

    suspend fun signIn(userAuth: UserAuth) = IOOperation {
        val signedUser = userRemoteDataSource.getSignedUser(userAuth)
        val fcmToken = userRemoteDataSource.updateUserFCM(signedUser.id)

        userSessionRepository.processSignedUser(signedUser.copy(fcmToken = fcmToken))
        preferencesDataStore.saveUserPreferences(signedUser.toUserAuth())
    }

    suspend fun signInSavedUser() = IOOperation {
        val userAuth = preferencesDataStore.getUserPreferences()

        if (userAuth.isEmpty) return@IOOperation

        signIn(userAuth)
    }

    suspend fun signUp(newUser: NewUser) = IOOperation {
        if (checkUserExists(newUser)) throwUserAlreadyExists()

        val userId = userRemoteDataSource.saveNewUser(newUser.toNewUserEntity())

        matchRemoteDataSource.saveNewUser(userId)

        userRemoteDataSource.clearAllUsers()

        signIn(newUser.toUserAuth())
    }

    private suspend fun checkUserExists(newUser: NewUser) = IOOperation {
        try {
            userRemoteDataSource.getSignedUser(newUser.toUserAuth())

            true
        } catch (noSuchUser: NoSuchElementException) {
            false
        }
    }

}