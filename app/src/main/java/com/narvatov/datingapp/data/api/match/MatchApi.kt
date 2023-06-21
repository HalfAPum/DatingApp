package com.narvatov.datingapp.data.api.match

import com.narvatov.datingapp.model.remote.AddMatchRequest
import com.narvatov.datingapp.model.remote.NonMatchedFriendsRequest
import com.narvatov.datingapp.model.remote.NonMatchedFriendsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface MatchApi {

    @POST("match/add")
    suspend fun addMatch(@Body addMatchRequest: AddMatchRequest)

    @DELETE("match/non-matched-friends")
    suspend fun getNonMatchedFriends(
        @Body nonMatchedFriendsRequest: NonMatchedFriendsRequest
    ) : List<NonMatchedFriendsResponse>

}