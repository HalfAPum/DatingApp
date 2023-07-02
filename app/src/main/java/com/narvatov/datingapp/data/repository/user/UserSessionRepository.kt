package com.narvatov.datingapp.data.repository.user

import com.narvatov.datingapp.data.api.user.UserApi
import com.narvatov.datingapp.data.remotedb.firestore.UserRemoteDataSource
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.model.local.user.Location
import com.narvatov.datingapp.model.local.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Single

@Single
class UserSessionRepository(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userApi: UserApi,
) : Repository() {

    private val _userStateFlow = MutableStateFlow(User.emptyUser)
    val userStateFlow = _userStateFlow.asStateFlow()

    var user: User = User.emptyUser
        private set

    fun updateUser(newUser: User) {
        user = newUser
        launchCatching { _userStateFlow.emit(user) }
    }

    suspend fun updateUserLocation(location: Location) = IOOperation {
        userApi.updateLocation(user.id, location)

        updateUserAdditionalData(user)
    }

    suspend fun processSignedUser(user: User) {
        updateUserAdditionalData(user)

        updateUserAvailability(true)
    }

    private suspend fun updateUserAdditionalData(user: User) {
        val additionalUserData = userApi.getUser(user.id)

        val updateUser = user.copy(location = Location(
            latitude = additionalUserData.latitude,
            longitude = additionalUserData.longitude
        ))

        updateUser(updateUser)
    }

    suspend fun updateUserAvailability(available: Boolean) = IOOperation {
        userRemoteDataSource.updateUserAvailability(user.id, available)
    }

}