package com.narvatov.datingapp.data.remotedb.datasource

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
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

    suspend fun getSignedUser(userAuth: UserAuth) = IOOperation {
        val user = getAllUsers().values.firstOrNull { it.email == userAuth.email }

        return@IOOperation user ?: throwNoSuchUserException()
    }

    fun getUserFlow(userId: String) = collection
        .document(userId)
        .snapshots()
        .map { it.mapUser() }

    suspend fun getNewFriends(user: User, limit: Long) = IOOperation {
        collection
            .limit(limit)
            .get()
            .await()
            .documents
            .map {
                println("FUCK GET NEW FRIENDS WTF ${it.id}")
                it.mapUser() }
    }

    suspend fun updateMatch(userId: String, friendId: String) = IOOperation {
        collection.document(userId).update(friendId, true).awaitUnit()

        collection.document(friendId).update(userId, true).awaitUnit()
    }


    suspend fun updateUserFCM(userId: String): String = IOOperation {
        val token = Firebase.messaging.token.await()

        collection.document(userId).update(Schema.USER_FCM_TOKEN, token).awaitUnit()

        token
    }

    //TODO concat with user availability
    suspend fun removeUserFCM(userId: String) = IOOperation {
        collection.document(userId).update(Schema.USER_FCM_TOKEN, FieldValue.delete()).awaitUnit()
    }

    suspend fun deleteUser(userId: String) = IOOperation {
        collection.document(userId).delete().awaitUnit()
    }

    suspend fun saveNewUser(newUserEntity: NewUserEntity) = IOOperation {
        collection.add(newUserEntity).awaitUnit()
    }

    suspend fun updateUserAvailability(userId: String, available: Boolean) = IOOperation {
        if (userId.isBlank()) return@IOOperation

        collection.document(userId).update(Schema.USER_AVAILABLE, available).awaitUnit()
    }

    companion object {
        private const val FIREBASE_NOT_IN_ARRAY_LIMIT = 10
    }
}