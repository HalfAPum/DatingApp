package com.narvatov.datingapp.model.remote

import com.google.gson.annotations.SerializedName

data class PhotoUploadResponse(
    @SerializedName("image")
    val image: Image,
)

data class Image(
    @SerializedName("url")
    val url: String,
)