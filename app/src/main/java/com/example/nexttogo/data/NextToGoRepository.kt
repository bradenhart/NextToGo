package com.example.nexttogo.data

import com.example.nexttogo.model.NextToGo
import com.example.nexttogo.network.NextToGoApiService

interface NextToGoRepository {
    suspend fun getNextToGo(): NextToGo
}

class NetworkNextToGoRepository(
    private val nextToGoApiService: NextToGoApiService
) : NextToGoRepository {
    override suspend fun getNextToGo(): NextToGo  = nextToGoApiService.getNextToGo()
}