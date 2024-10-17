package com.kpaas.plog.domain.repository

import com.kpaas.plog.domain.entity.RewardListEntity

interface RewardRepository {
    suspend fun getRewards(): Result<List<RewardListEntity>>
}