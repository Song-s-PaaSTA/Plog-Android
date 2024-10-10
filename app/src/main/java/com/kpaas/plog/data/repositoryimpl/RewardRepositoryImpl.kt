package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.RewardDataSource
import com.kpaas.plog.data.dto.response.Reward
import com.kpaas.plog.data.mapper.toRewardListEntity
import com.kpaas.plog.domain.entity.RewardListEntity
import com.kpaas.plog.domain.repository.RewardRepository
import javax.inject.Inject

class RewardRepositoryImpl @Inject constructor(
    private val rewardDataSource: RewardDataSource
) : RewardRepository {
    override suspend fun getRewards(): Result<List<RewardListEntity>> {
        return runCatching {
            rewardDataSource.getRewards().message?.rewards?.map { it.toRewardListEntity() }
                ?: emptyList()
        }
    }
}