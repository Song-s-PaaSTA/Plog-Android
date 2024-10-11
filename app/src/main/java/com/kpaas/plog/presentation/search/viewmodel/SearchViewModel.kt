package com.kpaas.plog.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.data.dto.request.RequestPloggingRouteDto
import com.kpaas.plog.data_local.entity.RecentKeywordEntity
import com.kpaas.plog.data_local.repository.RecentKeywordRepository
import com.kpaas.plog.domain.entity.LatLngEntity
import com.kpaas.plog.domain.entity.SearchResultListEntity
import com.kpaas.plog.domain.repository.PloggingRepository
import com.kpaas.plog.domain.repository.SearchRepository
import com.kpaas.plog.util.Location
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recentKeywordRepository: RecentKeywordRepository,
    private val searchRepository: SearchRepository,
    private val ploggingRepository: PloggingRepository
) : ViewModel() {
    private val _start = MutableStateFlow<Location?>(null)
    val start: MutableStateFlow<Location?> get() = _start

    private val _destination = MutableStateFlow<Location?>(null)
    val destination: MutableStateFlow<Location?> get() = _destination

    private val _reportAddress = MutableStateFlow<String?>(null)
    val reportAddress: MutableStateFlow<String?> get() = _reportAddress

    private val _stopoverAddress = MutableStateFlow<Location?>(null)
    val stopoverAddress: MutableStateFlow<Location?> get() = _stopoverAddress

    private var _recentKeywords = MutableStateFlow<List<RecentKeywordEntity>?>(null)
    val recentKeywords: MutableStateFlow<List<RecentKeywordEntity>?> get() = _recentKeywords

    private val _getPlaceState =
        MutableStateFlow<UiState<List<SearchResultListEntity>>>(UiState.Empty)
    val getPlaceState: StateFlow<UiState<List<SearchResultListEntity>>> = _getPlaceState

    private val _postPloggingRouteState = MutableStateFlow<UiState<List<LatLngEntity>>>(UiState.Empty)
    val postPloggingRouteState : StateFlow<UiState<List<LatLngEntity>>> = _postPloggingRouteState

    init {
        getSearchKeywords()
    }

    fun updateStart(name: String, longitude: Double, latitude: Double) {
        _start.value = Location(name, longitude, latitude)
    }

    fun updateDestination(name: String, longitude: Double, latitude: Double) {
        _destination.value = Location(name, longitude, latitude)
    }

    fun updateReportAddress(reportAddress: String) {
        _reportAddress.value = reportAddress
    }

    fun updateStopover(name: String, longitude: Double, latitude: Double) {
        _stopoverAddress.value = Location(name, longitude, latitude)
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

    fun insertSearchKeyword(name: String, longitude: Double, latitude: Double) {
        viewModelScope.launch {
            recentKeywordRepository.insertRecentKeyword(
                RecentKeywordEntity(
                    name = name,
                    longitude = longitude,
                    latitude = latitude
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

    fun postPloggingRoute() = viewModelScope.launch {
        _postPloggingRouteState.emit(UiState.Loading)
        ploggingRepository.postPloggingRoute(requestPloggingRouteDto = RequestPloggingRouteDto(
            startX = start.value!!.longitude,
            startY = start.value!!.latitude,
            endX = destination.value!!.longitude,
            endY = destination.value!!.latitude,
            passX = stopoverAddress.value?.longitude,
            passY = stopoverAddress.value?.latitude,
            reqCoordType = "WGS84GEO",
            startName = start.value!!.name,
            endName = destination.value!!.name,
            resCoordType = "WGS84GEO"
        )).fold(
            onSuccess = {
                _postPloggingRouteState.emit(UiState.Success(it))
            },
            onFailure = {
                _postPloggingRouteState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }
}