package com.narvatov.datingapp.model.remote

import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.putAll
import com.narvatov.datingapp.model.local.NewUser

class NewUserEntity : HashMap<String, String>() {

    companion object {
        fun NewUser.toNewUserEntity(): NewUserEntity = NewUserEntity().putAll(
            Pair(Schema.USER_EMAIL, email),
            Pair(Schema.USER_PASSWORD, password),
            Pair(Schema.USER_NAME, "$firstName $lastName")
        )
    }

}