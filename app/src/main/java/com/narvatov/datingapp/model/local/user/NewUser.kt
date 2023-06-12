package com.narvatov.datingapp.model.local.user

data class NewUser(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val photoBase64: String,
)