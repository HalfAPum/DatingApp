package com.narvatov.datingapp.model.remote

import com.google.gson.annotations.SerializedName

data class AddMatchRequest(
    @SerializedName("matcherId")
    val matcherId: String,
    @SerializedName("responderId")
    val responderId: String,
)
