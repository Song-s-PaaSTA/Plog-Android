package com.kpaas.plog.presentation.reward.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.entity.RewardListEntity
import com.kpaas.plog.domain.repository.RewardRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    private val rewardRepository: RewardRepository
) : ViewModel() {
    private val _getRewardState = MutableStateFlow<UiState<List<RewardListEntity>>>(UiState.Empty)
    val getRewardState: StateFlow<UiState<List<RewardListEntity>>> = _getRewardState

    fun getRewards() = viewModelScope.launch {
        _getRewardState.emit(UiState.Loading)
        rewardRepository.getRewards().fold(
            onSuccess = {
                _getRewardState.emit(UiState.Success(it))
            },
            onFailure = {
                _getRewardState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }
}