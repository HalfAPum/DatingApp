package com.narvatov.datingapp.model.remote

import com.narvatov.datingapp.data.remotedb.Schema
import com.narvatov.datingapp.data.remotedb.putAll
import com.narvatov.datingapp.model.local.NewUser

class NewUserEntity : HashMap<String, String>() {

    companion object {
        fun NewUser.toNewUserEntity(): NewUserEntity = NewUserEntity().putAll(
            Schema.USER_EMAIL to email,
            Schema.USER_PASSWORD to password,
            Schema.USER_NAME to "$firstName $lastName",
            Schema.USER_PHOTO_BASE_64 to photoBase64,
        )
    }

}