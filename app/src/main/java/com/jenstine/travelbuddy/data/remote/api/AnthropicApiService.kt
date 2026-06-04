package com.jenstine.travelKing.data.remote.api

import com.jenstine.travelKing.data.remote.model.AnthropicRequest
import com.jenstine.travelKing.data.remote.model.AnthropicResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AnthropicApiService {

    @POST("v1/messages")
    suspend fun createMessage(
        @Header("x-api-key") apiKey: String,
        @Body request: AnthropicRequest
    ): AnthropicResponse
}
