package com.narvatov.datingapp.model.remote

import com.google.gson.annotations.SerializedName

data class ServerUser(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
)