package com.narvatov.datingapp.data.repository.user

import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.model.local.user.User
import org.koin.core.annotation.Single

@Single
class UserSessionRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
) {

    var user: User = User.emptyUser

    suspend fun updateUserAvailability(available: Boolean) {
        userRemoteDataSource.updateUserAvailability(user.id, available)
    }

}