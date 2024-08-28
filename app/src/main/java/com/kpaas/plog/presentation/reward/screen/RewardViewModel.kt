package com.kpaas.plog.presentation.reward.screen

import androidx.lifecycle.ViewModel
import com.kpaas.plog.domain.entity.RewardListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor() : ViewModel() {
    val mockRewards = listOf(
        RewardListEntity(
            rank = 1,
            nickname = "김플로깅",
            score = 45
        ),
        RewardListEntity(
            rank = 2,
            nickname = "이플로깅",
            score = 40
        ),
        RewardListEntity(
            rank = 3,
            nickname = "박플로깅",
            score = 39
        ),
        RewardListEntity(
            rank = 4,
            nickname = "최플로깅",
            score = 24
        ),
        RewardListEntity(
            rank = 5,
            nickname = "정플로깅",
            score = 20
        ),
        RewardListEntity(
            rank = 6,
            nickname = "홍플로깅",
            score = 16
        ),
        RewardListEntity(
            rank = 7,
            nickname = "이플로깅",
            score = 12
        ),
        RewardListEntity(
            rank = 8,
            nickname = "김플로깅",
            score = 8
        ),
        RewardListEntity(
            rank = 9,
            nickname = "박플로깅",
            score = 4
        ),
        RewardListEntity(
            rank = 10,
            nickname = "최플로깅",
            score = 2
        ),
    )
}