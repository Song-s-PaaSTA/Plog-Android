package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseMyPloggingListDto
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.PLOGGING
import com.kpaas.plog.data.service.ApiKeyStorage.PROFILE
import com.kpaas.plog.data.service.ApiKeyStorage.V1
import retrofit2.http.GET

interface ProfileApiService {
    @GET("/$API/$V1/$PROFILE")
    suspend fun getProfile(): BaseResponse<ResponseSignUpDto>

    @GET("/$API/$V1/$PLOGGING")
    suspend fun getPlogging(): BaseResponse<ResponseMyPloggingListDto>
}