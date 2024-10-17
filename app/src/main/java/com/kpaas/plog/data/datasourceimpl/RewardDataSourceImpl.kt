package com.kpaas.plog.data.datasourceimpl

import com.kpaas.plog.data.datasource.RewardDataSource
import com.kpaas.plog.data.dto.BaseResponse
import com.kpaas.plog.data.dto.response.ResponseRewardDto
import com.kpaas.plog.data.service.RewardApiService
import javax.inject.Inject

class RewardDataSourceImpl @Inject constructor(
    private val rewardApiService: RewardApiService
) : RewardDataSource {
    override suspend fun getRewards(): BaseResponse<ResponseRewardDto> {
        return rewardApiService.getReward()
    }
}