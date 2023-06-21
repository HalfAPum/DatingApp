package com.narvatov.datingapp.data.repository.sign

import com.narvatov.datingapp.data.api.user.UserApi
import com.narvatov.datingapp.data.preference.PreferencesDataStore
import com.narvatov.datingapp.data.remotedb.firestore.UserRemoteDataSource
import com.narvatov.datingapp.data.remotedb.throwUserAlreadyExists
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.NewUser
import com.narvatov.datingapp.model.local.user.UserAuth
import com.narvatov.datingapp.model.local.user.UserAuth.Companion.toUserAuth
import com.narvatov.datingapp.model.remote.NewUserEntity.Companion.toNewUserEntity
import com.narvatov.datingapp.model.remote.SignUpRequest
import org.koin.core.annotation.Factory

@Factory
class SignRepository(
    private val preferencesDataStore: PreferencesDataStore,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userSessionRepository: UserSessionRepository,
    private val userApi: UserApi,
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

        userApi.signUp(SignUpRequest(id = userId))

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