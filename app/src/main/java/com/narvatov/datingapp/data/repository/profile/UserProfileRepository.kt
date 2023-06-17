package com.narvatov.datingapp.data.repository.profile

import com.narvatov.datingapp.data.preference.PreferencesDataStore
import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.User
import org.koin.core.annotation.Factory

@Factory
class UserProfileRepository(
    private val userSessionRepository: UserSessionRepository,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val preferencesDataStore: PreferencesDataStore,
) : Repository() {

    val userFlow = userSessionRepository.userStateFlow

    suspend fun deleteAccount() = IOOperation {
        userRemoteDataSource.deleteUser(userSessionRepository.user.id)

        executePostLogoutActions()
    }

    suspend fun logout() = IOOperation {
        userRemoteDataSource.removeUserFCM(userSessionRepository.user.id)

        userSessionRepository.updateUserAvailability(false)

        executePostLogoutActions()
    }

    private suspend fun executePostLogoutActions() = IOOperation {
        userRemoteDataSource.clearAllUsers()

        userSessionRepository.updateUser(User.emptyUser)

        preferencesDataStore.clearUserPreferences()
    }

}