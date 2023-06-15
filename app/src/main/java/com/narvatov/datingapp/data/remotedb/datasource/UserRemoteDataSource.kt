package com.narvatov.datingapp.data.remotedb.datasource

import com.google.firebase.firestore.ktx.snapshots
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.awaitUnit
import com.narvatov.datingapp.data.remotedb.mapUser
import com.narvatov.datingapp.data.remotedb.throwNoSuchUserException
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.model.local.user.UserAuth
import com.narvatov.datingapp.model.remote.NewUserEntity
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class UserRemoteDataSource : RemoteDataSource() {

    override val collectionName = Schema.USER_TABLE

    //TODO ALL USERS IS TEMP IMPLEMENTATION REMOVE THIS SHIT LATER
    private var allUsers: Map<String, User> = emptyMap()

    suspend fun getAllUsers(): Map<String, User> = IOOperation {
        if (allUsers.isNotEmpty()) return@IOOperation allUsers

        val data = collection.get().await()

        data.documents.associate { rawUser ->
            val id = rawUser.id
            val user = rawUser.mapUser()

            id to user
        }.apply {
            allUsers = this
        }
    }

    fun clearAllUsers() {
        allUsers = emptyMap()
    }

    suspend fun getUser(userAuth: UserAuth) = IOOperation {
        val user = if (userAuth.id == null) {
            getAllUsers().values.firstOrNull { it.email == userAuth.email }
        } else {
            getAllUsers()[userAuth.id]
        }

        return@IOOperation user ?: throwNoSuchUserException(context)
    }

    fun getUserFlow(userId: String) = collection
        .document(userId)
        .snapshots()
        .map { it.mapUser() }

    suspend fun saveNewUser(newUserEntity: NewUserEntity) = IOOperation {
        collection.add(newUserEntity).awaitUnit()
    }

    suspend fun updateUserAvailability(userId: String, available: Boolean) = IOOperation {
        if (userId.isBlank()) return@IOOperation

        collection.document(userId).update(Schema.USER_AVAILABLE, available).awaitUnit()
    }

}