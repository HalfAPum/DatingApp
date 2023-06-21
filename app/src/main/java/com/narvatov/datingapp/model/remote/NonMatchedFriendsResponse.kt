package com.narvatov.datingapp.model.remote

import com.google.gson.annotations.SerializedName

data class NonMatchedFriendsResponse(
    @SerializedName("id")
    val id: String,
)
