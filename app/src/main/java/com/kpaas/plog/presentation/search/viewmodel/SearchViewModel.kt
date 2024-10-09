package com.kpaas.plog.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.data_local.entity.RecentKeywordEntity
import com.kpaas.plog.data_local.repository.RecentKeywordRepository
import com.kpaas.plog.domain.entity.SearchResultListEntity
import com.kpaas.plog.domain.repository.SearchRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recentKeywordRepository: RecentKeywordRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _start = MutableStateFlow<String?>(null)
    val start: MutableStateFlow<String?> get() = _start

    private val _destination = MutableStateFlow<String?>(null)
    val destination: MutableStateFlow<String?> get() = _destination

    private val _reportAddress = MutableStateFlow<String?>(null)
    val reportAddress: MutableStateFlow<String?> get() = _reportAddress

    private val _stopoverAddress = MutableStateFlow<String?>(null)
    val stopoverAddress: MutableStateFlow<String?> get() = _stopoverAddress

    private var _recentKeywords = MutableStateFlow<List<RecentKeywordEntity>?>(null)
    val recentKeywords: MutableStateFlow<List<RecentKeywordEntity>?> get() = _recentKeywords

    private val _getPlaceState =
        MutableStateFlow<UiState<List<SearchResultListEntity>>>(UiState.Empty)
    val getPlaceState: StateFlow<UiState<List<SearchResultListEntity>>> = _getPlaceState

    init {
        getSearchKeywords()
    }

    fun updateStart(start: String) {
        _start.value = start
    }

    fun updateDestination(destination: String) {
        _destination.value = destination
    }

    fun updateReportAddress(reportAddress: String) {
        _reportAddress.value = reportAddress
    }

    fun updateStopover(stopover: String) {
        _stopoverAddress.value = stopover
    }

    fun deleteStart() {
        _start.value = null
    }

    fun deleteDestination() {
        _destination.value = null
    }

    fun deleteReportAddress() {
        _reportAddress.value = null
    }

    fun deleteStopover() {
        _stopoverAddress.value = null
    }

    fun insertSearchKeyword(input: String, address: String, roadAddress: String) {
        viewModelScope.launch {
            recentKeywordRepository.insertRecentKeyword(
                RecentKeywordEntity(
                    keyword = input,
                    address = address,
                    roadAddress = roadAddress
                )
            )
            getSearchKeywords()
        }
    }

    private fun getSearchKeywords() {
        viewModelScope.launch {
            _recentKeywords.value = recentKeywordRepository.getRecentKeywords()
        }
    }

    fun deleteSearchKeyword(recentKeywordEntity: RecentKeywordEntity) {
        viewModelScope.launch {
            recentKeywordRepository.deleteRecentKeyword(recentKeywordEntity)
            getSearchKeywords()
        }
    }

    fun deleteAllSearchKeywords() {
        viewModelScope.launch {
            recentKeywordRepository.deleteAllRecentKeywords()
            getSearchKeywords()
        }
    }

    fun getPlace(query: String) = viewModelScope.launch {
        _getPlaceState.emit(UiState.Loading)
        searchRepository.getPlace(query).fold(
            onSuccess = {
                _getPlaceState.emit(UiState.Success(it))
            },
            onFailure = {
                _getPlaceState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }

}