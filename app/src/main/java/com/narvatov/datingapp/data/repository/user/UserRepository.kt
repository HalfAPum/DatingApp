package com.narvatov.datingapp.data.repository.user

import com.narvatov.datingapp.data.remotedb.datasource.UserRemoteDataSource
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.model.local.user.User
import org.koin.core.annotation.Single

@Single
class UserRepository(private val userRemoteDataSource: UserRemoteDataSource) : Repository() {

    suspend fun getAllUsers() = userRemoteDataSource.getAllUsers()

    suspend fun getUser(userId: String) = getAllUsers().getOrDefault(userId, User.emptyUser)

    fun getUserFlow(userId: String) = userRemoteDataSource.getUserFlow(userId)

}