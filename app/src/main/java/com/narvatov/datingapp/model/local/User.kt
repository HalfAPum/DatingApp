package com.narvatov.datingapp.model.local

data class User(
    val email: String,
    val name: String,
) {

    companion object {
        val emptyUser = User("", "")
    }

}
