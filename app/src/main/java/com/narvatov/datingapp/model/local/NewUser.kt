package com.narvatov.datingapp.model.local

data class NewUser(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)