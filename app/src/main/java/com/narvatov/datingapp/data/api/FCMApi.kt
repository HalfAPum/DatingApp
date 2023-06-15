package com.narvatov.datingapp.data.api

import com.narvatov.datingapp.model.remote.SendNotification
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FCMApi {

    @POST("send")
    @Headers(
        "Authorization: key=AAAA1R_oyb0:APA91bHQ9MSkcD5cC96MwDSMbbpI3I3yFJPOOskCUFqdShhriOH7nY0MzCxklW8jCjBMRzj0v1gmpFuHx7infCiXZ3NEM9D_bOYDjmuOKwiZZAuBFPzkbUjwFcLSV6SwRKzdhkeR6f9d",
        "Content-type: application/json"
    )
    suspend fun sendNotification(@Body sendNotification: SendNotification)

}