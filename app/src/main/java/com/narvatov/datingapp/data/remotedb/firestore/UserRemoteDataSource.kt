package com.narvatov.datingapp.data.remotedb.firestore

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.awaitUnit
import com.narvatov.datingapp.data.remotedb.mapUser
import com.narvatov.datingapp.data.remotedb.throwNoSuchUserException
import com.narvatov.datingapp.model.local.user.UserAuth
import com.narvatov.datingapp.model.remote.NewUserEntity
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class UserRemoteDataSource : FirestoreRemoteDataSource() {

    override val collectionName = Schema.USER_TABLE

    suspend fun getSignedUser(userAuth: UserAuth) = IOOperation {
        val user = collection
            .whereEqualTo(Schema.USER_EMAIL, userAuth.email)
            .get()
            .await()
            .map { it.mapUser() }
            .firstOrNull()

        return@IOOperation user ?: throwNoSuchUserException()
    }

    suspend fun getUsersByIds(userIds: List<String>) = IOOperation {
        collection
            .whereIn(FieldPath.documentId(), userIds)
            .get()
            .await()
            .documents
            .map { it.mapUser() }
    }

    fun getUserFlow(userId: String) = collection
        .document(userId)
        .snapshots()
        .map { it.mapUser() }


    suspend fun saveNewUser(newUserEntity: NewUserEntity) = IOOperation {
        collection.add(newUserEntity).await().id
    }


    suspend fun updateUserAvailability(userId: String, available: Boolean) = IOOperation {
        if (userId.isBlank()) return@IOOperation

        collection.document(userId).update(Schema.USER_AVAILABLE, available).awaitUnit()
    }

    suspend fun updateUserFCM(userId: String): String = IOOperation {
        val token = Firebase.messaging.token.await()

        collection.document(userId).update(Schema.USER_FCM_TOKEN, token).awaitUnit()

        token
    }


    suspend fun removeUserFCM(userId: String) = IOOperation {
        collection.document(userId).update(Schema.USER_FCM_TOKEN, FieldValue.delete()).awaitUnit()
    }

    suspend fun deleteUser(userId: String) = IOOperation {
        collection.document(userId).delete().awaitUnit()
    }

}