package com.kpaas.plog.data.service

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseRewardDto
import com.kpaas.plog.data.service.ApiKeyStorage.API
import com.kpaas.plog.data.service.ApiKeyStorage.MEMBER_SERVICE
import com.kpaas.plog.data.service.ApiKeyStorage.REWARD
import com.kpaas.plog.data.service.ApiKeyStorage.REWARD_SERVICE
import com.kpaas.plog.data.service.ApiKeyStorage.V1
import retrofit2.http.GET

interface RewardApiService {
    @GET("/$MEMBER_SERVICE/$API/$V1/$REWARD")
    suspend fun getReward(): BaseResponse<ResponseRewardDto>
}