package com.example.nexttogo.data

import com.example.nexttogo.network.NextToGoApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val nextToGoRepository: NextToGoRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl =
        "https://api.neds.com.au/rest/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: NextToGoApiService by lazy {
        retrofit.create(NextToGoApiService::class.java)
    }

    override val nextToGoRepository: NextToGoRepository by lazy {
        NetworkNextToGoRepository(retrofitService)
    }

}