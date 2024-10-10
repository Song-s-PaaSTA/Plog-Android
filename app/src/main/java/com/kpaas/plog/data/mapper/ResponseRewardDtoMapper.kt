package com.kpaas.plog.data.mapper

import com.kpaas.plog.data.dto.response.Reward
import com.kpaas.plog.domain.entity.RewardListEntity

fun Reward.toRewardListEntity() = RewardListEntity(
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    score = score,
)