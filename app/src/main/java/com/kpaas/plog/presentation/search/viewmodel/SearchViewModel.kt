package com.kpaas.plog.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.data_local.entity.RecentKeywordEntity
import com.kpaas.plog.data_local.repository.RecentKeywordRepository
import com.kpaas.plog.domain.entity.SearchResultListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recentKeywordRepository: RecentKeywordRepository
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

    val mockSearchResults = listOf(
        SearchResultListEntity(
            id = 1,
            title = "늘푸른초등학교",
            address = "서울특별시",
            roadAddress = "서울시 노원구 덕릉로 459-21"
        ),
        SearchResultListEntity(
            id = 2,
            title = "숙명여자대학교 제 1캠퍼스",
            address = "서울특별시",
            roadAddress = "서울시 용산구 청파로47길 100"
        ),
        SearchResultListEntity(
            id = 3,
            title = "서울대학교",
            address = "서울특별시",
            roadAddress = "서울시 관악구 관악로 1"
        ),
        SearchResultListEntity(
            id = 4,
            title = "서울여자대학교",
            address = "서울특별시",
            roadAddress = "서울 노원구 화랑로 621 서울여자대학교"
        ),
        SearchResultListEntity(
            id = 5,
            title = "서울시립대학교",
            address = "서울특별시",
            roadAddress = "서울특별시 동대문구 서울시립대로 163"
        ),
        SearchResultListEntity(
            id = 6,
            title = "서울역 (고속철도)",
            address = "서울특별시",
            roadAddress = "서울용산구 한강대로 405"
        ),
    )
}