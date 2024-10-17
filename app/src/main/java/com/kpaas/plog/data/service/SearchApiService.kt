package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseSearchDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.PLACE
import com.kpaas.plog.data.service.ApiKeyStorage.PLOGGING_SERVICE
import com.kpaas.plog.data.service.ApiKeyStorage.V1
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("/$PLOGGING_SERVICE/$API/$V1/$PLACE")
    suspend fun getPlace(
        @Query("query") query: String
    ): BaseResponse<ResponseSearchDto>
}