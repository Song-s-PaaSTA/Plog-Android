package com.kpaas.plog.presentation.plogging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.data.dto.response.PloggingList
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
    private val _getPloggingState = MutableStateFlow<UiState<List<PloggingList>>>(UiState.Empty)
    val getPloggingState: StateFlow<UiState<List<PloggingList>>> = _getPloggingState

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

    val mockMyPloggingList = listOf(
        MyPloggingListEntity(
            start = "서울시 종로구 종로 1",
            destination = "서울시 종로구 종로 2",
            timeDifference = 1
        ),
        MyPloggingListEntity(
            start = "서울 노원구 동일로 1058",
            destination = "2층 젠카즈 공릉본점",
            timeDifference = 2
        ),
        MyPloggingListEntity(
            start = "서울시 종로구 종로 2",
            destination = "서울시 종로구 종로 3",
            timeDifference = 3
        ),
        MyPloggingListEntity(
            start = "경기도 고양시 월드고양로 19",
            destination = "이마트 더타운몰 고양점",
            timeDifference = 1
        ),
        MyPloggingListEntity(
            start = "서울시 종로구 종로 3",
            destination = "서울시 종로구 종로 4",
            timeDifference = 4
        ),
        MyPloggingListEntity(
            start = "서울시 종로구 종로 4",
            destination = "서울시 종로구 종로 5",
            timeDifference = 5
        ),
        MyPloggingListEntity(
            start = "서울시 종로구 종로 5",
            destination = "서울시 종로구 종로 6",
            timeDifference = 6
        ),
        MyPloggingListEntity(
            start = "서울시 종로구 종로 6",
            destination = "서울시 종로구 종로 7",
            timeDifference = 7
        ),
        MyPloggingListEntity(
            start = "서울시 종로구 종로 7",
            destination = "서울시 종로구 종로 8",
            timeDifference = 8
        ),
    )
}