package com.narvatov.datingapp.model.remote

import com.google.gson.annotations.SerializedName


data class SignUpRequest(
    @SerializedName("id")
    val id: String
)