package com.kpaas.plog.presentation.report.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.entity.ReportContentEntity
import com.kpaas.plog.domain.entity.ReportListEntity
import com.kpaas.plog.domain.repository.ReportRepository
import com.kpaas.plog.util.ChipState
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    var filterChipStates = mutableStateListOf<ChipState>()
        private set

    var regionChipStates = mutableStateListOf<ChipState>()
        private set

    var progressChipStates = mutableStateListOf<ChipState>()
        private set

    private var initialChips = listOf("지역", "최신순", "인기순", "상태")
    private val regionChips = listOf(
        "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "울산광역시",
        "세종특별자치시", "경기도", "강원특별자치도", "충청북도", "충청남도", "전북특별자치도", "전라남도",
        "경상북도", "경상남도", "제주특별자치도"
    )
    private val progressChips = listOf("청소 시작 전", "청소 중", "청소 완료")

    private val _getReportsState = MutableStateFlow<UiState<List<ReportListEntity>>>(UiState.Empty)
    val getReportsState: StateFlow<UiState<List<ReportListEntity>>> = _getReportsState

    private val _getReportDetailState = MutableStateFlow<UiState<ReportContentEntity>>(UiState.Empty)
    val getReportDetailState: StateFlow<UiState<ReportContentEntity>> = _getReportDetailState

    private val _postBookmarkState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val postBookmarkState: StateFlow<UiState<Unit>> = _postBookmarkState

    init {
        filterChipStates.addAll(
            initialChips.map { chipText ->
                ChipState(chipText, mutableStateOf(false))
            }
        )
        regionChipStates.addAll(
            regionChips.map { chipText ->
                ChipState(chipText, mutableStateOf(false))
            }
        )
        progressChipStates.addAll(
            progressChips.map { chipText ->
                ChipState(chipText, mutableStateOf(false))
            }
        )
    }

    fun filterChipSelection(index: Int) {
        val chip = filterChipStates[index]
        chip.isSelected.value = !chip.isSelected.value
    }

    fun deselectChip(index: Int) {
        val chip = filterChipStates[index]
        chip.isSelected.value = false // 해당 칩을 비선택
    }

    fun regionChipSelection(index: Int) {
        val chip = regionChipStates[index]
        chip.isSelected.value = !chip.isSelected.value
    }

    fun progressChipSelection(index: Int) {
        val chip = progressChipStates[index]
        chip.isSelected.value = !chip.isSelected.value
    }

    fun updateFilterChips() {
        val selectedRegions = regionChipStates.filter { it.isSelected.value }.map { it.text }
        if (selectedRegions.isNotEmpty()) {
            filterChipStates[0] =
                ChipState(selectedRegions.joinToString(", "), mutableStateOf(true))
        } else {
            filterChipStates[0] = ChipState("지역", mutableStateOf(false))
        }

        val selectedProgress = progressChipStates.filter { it.isSelected.value }.map { it.text }
        if (selectedProgress.isNotEmpty()) {
            filterChipStates[3] =
                ChipState(selectedProgress.joinToString(", "), mutableStateOf(true))
        } else {
            filterChipStates[3] = ChipState("상태", mutableStateOf(false))
        }
    }

    fun getReports() = viewModelScope.launch {
        _getReportsState.emit(UiState.Loading)
        val sort = when {
            filterChipStates[1].isSelected.value -> "date"
            filterChipStates[2].isSelected.value -> "popularity"
            else -> null // 기본값 설정 또는 예외 처리
        }

        reportRepository.getReports(
            region = regionChipStates.filter { it.isSelected.value }.map { it.text },
            sort = sort,
            status = progressChipStates.filter { it.isSelected.value }.map { it.text }
        ).fold(
            onSuccess = {
                _getReportsState.emit(UiState.Success(it))
            },
            onFailure = {
                _getReportsState.value = UiState.Failure(it.message.toString())
            }
        )

    }

    fun getReportDetail(reportId: Long) = viewModelScope.launch {
        _getReportDetailState.emit(UiState.Loading)
        reportRepository.getReportDetail(reportId).fold(
            onSuccess = {
                _getReportDetailState.emit(UiState.Success(it))
            },
            onFailure = {
                _getReportDetailState.value = UiState.Failure(it.message.toString())
            }
        )
    }

    fun postBookmark(reportId: Long) = viewModelScope.launch {
        _postBookmarkState.emit(UiState.Loading)
        reportRepository.postBookmark(reportId).fold(
            onSuccess = {
                _postBookmarkState.emit(UiState.Success(it))
            },
            onFailure = {
                _postBookmarkState.value = UiState.Failure(it.message.toString())
            }
        )
    }
}