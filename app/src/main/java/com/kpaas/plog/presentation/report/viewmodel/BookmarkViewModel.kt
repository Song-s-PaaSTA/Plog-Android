package com.kpaas.plog.presentation.report.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.entity.BookmarkEntity
import com.kpaas.plog.domain.repository.ReportRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : ViewModel() {
    private val _getBookmarkState = MutableStateFlow<UiState<List<BookmarkEntity>>>(UiState.Empty)
    val getBookmarkState: StateFlow<UiState<List<BookmarkEntity>>> = _getBookmarkState

    fun getMyBookmarks() = viewModelScope.launch {
        _getBookmarkState.emit(UiState.Loading)
        reportRepository.getMyBookmarks().fold(
            onSuccess = {
                _getBookmarkState.emit(UiState.Success(it))
            },
            onFailure = {
                _getBookmarkState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }
}
