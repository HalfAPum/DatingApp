package com.narvatov.datingapp.data.api.user

import com.narvatov.datingapp.model.local.user.Location
import com.narvatov.datingapp.model.remote.ServerUser
import com.narvatov.datingapp.model.remote.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("user/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest)

    @DELETE("user/delete-account/{id}")
    suspend fun deleteAccount(@Path("id") id: String)

    @POST("user/update-location/{id}")
    suspend fun updateLocation(
        @Path("id") id: String,
        @Body location: Location,
    )

    @GET("user/{id}")
    suspend fun getUser(@Path("id") id: String) : ServerUser

}