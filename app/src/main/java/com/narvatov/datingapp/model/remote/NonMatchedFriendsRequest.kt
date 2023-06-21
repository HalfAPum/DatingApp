package com.narvatov.datingapp.model.remote

import com.google.gson.annotations.SerializedName

data class NonMatchedFriendsRequest(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("limit")
    val limit: Int,
)
