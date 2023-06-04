package com.narvatov.datingapp.data.remotedb.datasource

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.requestString
import com.narvatov.datingapp.data.remotedb.throwNoSuchUserException
import com.narvatov.datingapp.model.local.User
import com.narvatov.datingapp.model.local.UserAuth
import com.narvatov.datingapp.model.remote.NewUserEntity
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single

@Single
class UserRemoteDataSource : DataSource() {

    private val db = Firebase.firestore

    private var allUsers: Map<String, User> = emptyMap()

    suspend fun getAllUsers(): Map<String, User> = IOOperation {
        if (allUsers.isNotEmpty()) return@IOOperation allUsers

        val data = db.collection(Schema.USER_TABLE)
            .get()
            .await()

        data.documents.associate { rawUser ->
            val id = rawUser.id
            val email = rawUser.requestString(Schema.USER_EMAIL)
            val name = rawUser.requestString(Schema.USER_NAME)

            id to User(id, email, name)
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

    suspend fun saveNewUser(newUserEntity: NewUserEntity) = IOOperation {
        db.collection(Schema.USER_TABLE).add(newUserEntity).await()
    }

}