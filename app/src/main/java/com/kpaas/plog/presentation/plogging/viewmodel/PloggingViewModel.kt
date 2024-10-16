package com.kpaas.plog.presentation.plogging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.data.dto.request.RequestPloggingRouteDto
import com.kpaas.plog.domain.entity.LatLngEntity
import com.kpaas.plog.domain.repository.PloggingPreferencesRepository
import com.kpaas.plog.domain.repository.PloggingRepository
import com.kpaas.plog.util.Location
import com.kpaas.plog.util.UiState
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PloggingViewModel @Inject constructor(
    private val ploggingPreferencesRepository: PloggingPreferencesRepository,
    private val ploggingRepository: PloggingRepository
) : ViewModel() {
    private val _postPloggingProof = MutableStateFlow<UiState<String?>>(UiState.Empty)
    val postPloggingProof: StateFlow<UiState<String?>> = _postPloggingProof


    fun postPloggingProof(
        startRoadAddr: String,
        endRoadAddr: String,
        ploggingTime: String,
        file: File?
    ) = viewModelScope.launch {
        _postPloggingProof.value = UiState.Loading
        ploggingRepository.postPloggingProof(
            startRoadAddr = startRoadAddr,
            endRoadAddr = endRoadAddr,
            ploggingTime = ploggingTime,
            file = file
        ).fold(
            onSuccess = {
                _postPloggingProof.value = UiState.Success(it)
            },
            onFailure = {
                _postPloggingProof.value = UiState.Failure(it.message.toString())
            }
        )
    }

    fun saveAllPloggingData(
        buttonText: String,
        startTime: Long,
        start: Location?,
        destination: Location?,
        stopover: Location?,
        searchTextFieldVisible: Boolean,
        stopoverTextFieldVisible: Boolean
    ) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveAll(
                buttonText = buttonText,
                startTime = startTime,
                start = start,
                destination = destination,
                stopover = stopover,
                searchTextFieldVisible = searchTextFieldVisible,
                stopoverTextFieldVisible = stopoverTextFieldVisible
            )
        }
    }

    fun getButtonText() = ploggingPreferencesRepository.getButtonText()

    fun saveStopoverTextFieldVisible(visibility: Boolean) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveStopoverTextFieldVisible(visibility)
        }
    }

    fun saveStart(start: Location?) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveStart(start)
        }
    }

    fun getStart() = ploggingPreferencesRepository.getStart()

    fun saveDestination(destination: Location?) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveDestination(destination)
        }
    }

    fun getDestination() = ploggingPreferencesRepository.getDestination()

    fun saveStopover(stopover: Location?) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveStopover(stopover)
        }
    }

    fun getStopover() = ploggingPreferencesRepository.getStopover()

    fun getStartTime() = ploggingPreferencesRepository.getStartTime()
    fun getSearchTextFieldVisible() = ploggingPreferencesRepository.getSearchTextFieldVisible()
    fun getStopoverTextFieldVisible() = ploggingPreferencesRepository.getStopoverTextFieldVisible()

    fun saveRoute(route: List<LatLngEntity>) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveRoute(route)
        }
    }

    fun getRoute() = ploggingPreferencesRepository.getRoute()

    fun clear() {
        viewModelScope.launch {
            ploggingPreferencesRepository.clear()
        }
    }
}