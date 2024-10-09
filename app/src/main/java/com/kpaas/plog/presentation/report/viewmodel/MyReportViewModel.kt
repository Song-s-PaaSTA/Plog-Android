package com.kpaas.plog.presentation.report.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.entity.MyReportListEntity
import com.kpaas.plog.domain.repository.ReportRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    private val _getMyReportsState =
        MutableStateFlow<UiState<List<MyReportListEntity>>>(UiState.Empty)
    val getMyReportsState: StateFlow<UiState<List<MyReportListEntity>>> = _getMyReportsState

    private val _deleteReportState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val deleteReportState: StateFlow<UiState<Unit>> = _deleteReportState

    fun getMyReports() = viewModelScope.launch {
        _getMyReportsState.emit(UiState.Loading)
        reportRepository.getMyReports().fold(
            onSuccess = {
                _getMyReportsState.emit(UiState.Success(it))
            },
            onFailure = {
                _getMyReportsState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }

    fun deleteReportState(reportId: Long) = viewModelScope.launch {
        _deleteReportState.emit(UiState.Loading)
        reportRepository.deleteReport(reportId).fold(
            onSuccess = {
                _deleteReportState.emit(UiState.Success(it))
            },
            onFailure = {
                _deleteReportState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }
}