package com.kpaas.plog.presentation.plogging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.entity.MyPloggingListEntity
import com.kpaas.plog.domain.repository.ProfileRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPloggingViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _getPloggingState =
        MutableStateFlow<UiState<List<MyPloggingListEntity>>>(UiState.Empty)
    val getPloggingState: StateFlow<UiState<List<MyPloggingListEntity>>> = _getPloggingState

    fun getPlogging() = viewModelScope.launch {
        _getPloggingState.value = UiState.Loading
        profileRepository.getPlogging().fold(
            onSuccess = {
                _getPloggingState.value = UiState.Success(it)
            },
            onFailure = {
                _getPloggingState.value = UiState.Failure(it.message.toString())
            }
        )
    }
}