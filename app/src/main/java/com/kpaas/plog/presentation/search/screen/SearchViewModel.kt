package com.kpaas.plog.presentation.search.screen

import androidx.lifecycle.ViewModel
import com.kpaas.plog.domain.entity.SearchResultListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _start = MutableStateFlow<String?>(null)
    val start: MutableStateFlow<String?> get() = _start

    private val _destination = MutableStateFlow<String?>(null)
    val destination: MutableStateFlow<String?> get() = _destination

    fun updateStart(start: String) {
        _start.value = start
    }

    fun updateDestination(destination: String) {
        _destination.value = destination
    }

    fun deleteStart() {
        _start.value = null
    }

    fun deleteDestination() {
        _destination.value = null
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