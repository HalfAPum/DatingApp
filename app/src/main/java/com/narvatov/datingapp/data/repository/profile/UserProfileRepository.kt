package com.narvatov.datingapp.data.repository.profile

import com.narvatov.datingapp.data.api.user.UserApi
import com.narvatov.datingapp.data.preference.UserPreferencesDataStore
import com.narvatov.datingapp.data.remotedb.firestore.ConversationRemoteDataSource
import com.narvatov.datingapp.data.remotedb.firestore.UserRemoteDataSource
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.data.repository.user.UserSessionRepository
import com.narvatov.datingapp.model.local.user.User
import org.koin.core.annotation.Factory

@Factory
class UserProfileRepository(
    private val conversationRemoteDataSource: ConversationRemoteDataSource,
    private val userSessionRepository: UserSessionRepository,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val userApi: UserApi,
) : Repository() {

    val userFlow = userSessionRepository.userStateFlow

    suspend fun deleteAccount() = IOOperation {
        userRemoteDataSource.deleteUser(userSessionRepository.user.id)

        userApi.deleteAccount(id = userSessionRepository.user.id)

        conversationRemoteDataSource.deleteUserConversations()

        executePostLogoutActions()
    }

    suspend fun logout() = IOOperation {
        userRemoteDataSource.removeUserFCM(userSessionRepository.user.id)

        userSessionRepository.updateUserAvailability(false)

        executePostLogoutActions()
    }

    private suspend fun executePostLogoutActions() = IOOperation {
        userSessionRepository.updateUser(User.emptyUser)

        userPreferencesDataStore.clearUserPreferences()
    }

}