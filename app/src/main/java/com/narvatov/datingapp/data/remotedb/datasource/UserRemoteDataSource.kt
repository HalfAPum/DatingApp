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

@Factory
class UserRemoteDataSource : DataSource() {

    private val db = Firebase.firestore


    suspend fun getUser(userAuth: UserAuth) = IOOperation {
        val data = db.collection(Schema.USER_TABLE)
            .whereEqualTo(Schema.USER_EMAIL, userAuth.email)
            .whereEqualTo(Schema.USER_PASSWORD, userAuth.password)
            .get()
            .await()


        val rawUser = data.documents.getOrNull(0) ?: throwNoSuchUserException(context)

        val email = rawUser.requestString(Schema.USER_EMAIL)
        val name = rawUser.requestString(Schema.USER_NAME)

        User(email, name)
    }

    suspend fun saveNewUser(newUserEntity: NewUserEntity) = IOOperation {
        db.collection(Schema.USER_TABLE).add(newUserEntity).await()
    }

}