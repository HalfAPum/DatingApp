package com.narvatov.datingapp.model.local.user

data class UserAuth(
    val email: String,
    val password: String,
    val id: String? = null
) {

    val isEmpty: Boolean
        get() = email.isBlank() || password.isBlank()

    companion object {
        fun NewUser.toUserAuth() = UserAuth(email, password)

        fun User.toUserAuth() = UserAuth(email, password, id)

        val emptyUser = UserAuth("", "")
    }

}
