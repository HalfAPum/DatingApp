package com.narvatov.datingapp.model.local

data class UserAuth(
    val email: String,
    val password: String,
) {

    val isEmpty: Boolean
        get() = email.isBlank() || password.isBlank()

    companion object {
        fun NewUser.toUserAuth() = UserAuth(email, password)

        val emptyUser = UserAuth("", "")
    }

}
