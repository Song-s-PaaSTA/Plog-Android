package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.ExampleBaseResponse
import com.kpaas.plog.data.dto.response.ResponseExampleDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.USERS
import retrofit2.http.GET
import retrofit2.http.Query

interface ExampleApiService {
    @GET("/$API/$USERS")
    suspend fun getUsers(
        @Query("page") page: Int
    ): ExampleBaseResponse<List<ResponseExampleDto>>
}