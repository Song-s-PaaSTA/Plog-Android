package com.kpaas.plog.data.repositoryimpl

import com.kpaas.plog.data.datasource.RewardDataSource
import com.kpaas.plog.data.dto.response.Reward
import com.kpaas.plog.domain.repository.RewardRepository
import javax.inject.Inject

class RewardRepositoryImpl @Inject constructor(
    private val rewardDataSource: RewardDataSource
) : RewardRepository {
    override suspend fun getRewards(): Result<List<Reward>> {
        return runCatching {
            rewardDataSource.getRewards().message?.rewards ?: emptyList()
        }
    }
}