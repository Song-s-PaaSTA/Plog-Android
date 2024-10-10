package com.kpaas.plog.data.datasource

import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseRewardDto

interface RewardDataSource {
    suspend fun getRewards(): BaseResponse<ResponseRewardDto>
}