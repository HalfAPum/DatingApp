package com.narvatov.datingapp.data.repository.user

import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.model.local.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Single

@Single
class UserSessionRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
) : Repository() {

    private val _userStateFlow = MutableStateFlow(User.emptyUser)
    val userStateFlow = _userStateFlow.asStateFlow()

    var user: User = User.emptyUser
        private set

    fun updateUser(newUser: User) {
        user = newUser
        launchCatching { _userStateFlow.emit(user) }
    }

    suspend fun processSignedUser(user: User) {
        updateUser(user)

        updateUserAvailability(true)
    }

    suspend fun updateUserAvailability(available: Boolean) = IOOperation {
        userRemoteDataSource.updateUserAvailability(user.id, available)
    }

}