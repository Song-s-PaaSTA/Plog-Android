package com.kpaas.plog.domain.repository

import com.kpaas.plog.data.dto.response.Reward

interface RewardRepository {
    suspend fun getRewards(): Result<List<Reward>>
}