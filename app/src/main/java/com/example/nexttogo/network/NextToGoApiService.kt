package com.example.nexttogo.network

import com.example.nexttogo.model.NextToGo
import retrofit2.http.GET
import retrofit2.http.Query

interface NextToGoApiService {
    @GET("racing/")
    suspend fun getNextToGo(@Query("method") method: String = "nextraces", @Query("count") count: String = "10"): NextToGo

}