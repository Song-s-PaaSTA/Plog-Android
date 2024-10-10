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
import java.io.File
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

    private val _postReportState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val postReportState: StateFlow<UiState<Unit>> = _postReportState

    private val _patchReportState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val patchReportState: StateFlow<UiState<Unit>> = _patchReportState

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

    fun postReport(
        reportDesc: String,
        roadAddr: String,
        reportStatus: String,
        reportImgFile: File
    ) = viewModelScope.launch {
        _postReportState.emit(UiState.Loading)
        reportRepository.postReport(reportDesc, roadAddr, reportStatus, reportImgFile).fold(
            onSuccess = {
                _postReportState.emit(UiState.Success(it))
            },
            onFailure = {
                _postReportState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }

    fun patchReport(
        reportId: Long,
        reportStatus: String,
        reportDesc: String,
        existingImgUrl: String,
        reportImgFile: File? = null
    ) = viewModelScope.launch {
        _patchReportState.emit(UiState.Loading)
        reportRepository.patchReport(
            reportId,
            reportStatus,
            reportDesc,
            existingImgUrl,
            reportImgFile
        ).fold(
            onSuccess = {
                _patchReportState.emit(UiState.Success(it))
            },
            onFailure = {
                _patchReportState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }
}