package com.kpaas.plog.presentation.plogging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.data.dto.request.RequestPloggingRouteDto
import com.kpaas.plog.domain.entity.LatLngEntity
import com.kpaas.plog.domain.repository.PloggingPreferencesRepository
import com.kpaas.plog.domain.repository.PloggingRepository
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
    private val _getPloggingRoute = MutableStateFlow<UiState<List<LatLngEntity>>>(UiState.Empty)
    val getPloggingRoute : StateFlow<UiState<List<LatLngEntity>>> = _getPloggingRoute

    private val _postPloggingProof = MutableStateFlow<UiState<String>>(UiState.Empty)
    val postPloggingProof : StateFlow<UiState<String>> = _postPloggingProof


    fun postPloggingProof(
        startRoadAddr: String,
        endRoadAddr: String,
        ploggingTime: String,
        proofImage: File
    ) = viewModelScope.launch {
        _postPloggingProof.value = UiState.Loading
        ploggingRepository.postPloggingProof(
            startRoadAddr = startRoadAddr,
            endRoadAddr = endRoadAddr,
            ploggingTime = ploggingTime,
            proofImage = proofImage
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
        start: String,
        destination: String,
        stopover: String,
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

    fun saveStart(start: String) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveStart(start)
        }
    }

    fun getStart() = ploggingPreferencesRepository.getStart()

    fun saveDestination(destination: String) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveDestination(destination)
        }
    }

    fun getDestination() = ploggingPreferencesRepository.getDestination()

    fun saveStopover(stopover: String) {
        viewModelScope.launch {
            ploggingPreferencesRepository.saveStopover(stopover)
        }
    }

    fun getStopover() = ploggingPreferencesRepository.getStopover()

    fun getStartTime() = ploggingPreferencesRepository.getStartTime()
    fun getSearchTextFieldVisible() = ploggingPreferencesRepository.getSearchTextFieldVisible()
    fun getStopoverTextFieldVisible() = ploggingPreferencesRepository.getStopoverTextFieldVisible()

    fun clear() {
        viewModelScope.launch {
            ploggingPreferencesRepository.clear()
        }
    }

    val mockRoutes = listOf(
        LatLng(37.5660645, 126.9826732),
        LatLng(37.5660294, 126.9826723),
        LatLng(37.5658526, 126.9826611),
        LatLng(37.5658040, 126.9826580),
        LatLng(37.5657697, 126.9826560),
        LatLng(37.5654413, 126.9825880),
        LatLng(37.5652157, 126.9825273),
        LatLng(37.5650560, 126.9824843),
        LatLng(37.5647789, 126.9824114),
        LatLng(37.5646788, 126.9823861),
        LatLng(37.5644062, 126.9822963),
        LatLng(37.5642519, 126.9822566),
        LatLng(37.5641517, 126.9822312),
        LatLng(37.5639965, 126.9821915),
        LatLng(37.5636536, 126.9820920),
        LatLng(37.5634424, 126.9820244),
        LatLng(37.5633241, 126.9819890),
        LatLng(37.5632772, 126.9819712),
        LatLng(37.5629404, 126.9818433),
        LatLng(37.5627733, 126.9817584),
        LatLng(37.5626694, 126.9816980),
        LatLng(37.5624588, 126.9815738),
        LatLng(37.5620376, 126.9813140),
        LatLng(37.5619426, 126.9812252),
        LatLng(37.5613227, 126.9814831),
        LatLng(37.5611995, 126.9815372),
        LatLng(37.5609414, 126.9816749),
        LatLng(37.5606785, 126.9817390),
        LatLng(37.5605659, 126.9817499),
        LatLng(37.5604892, 126.9817459),
        LatLng(37.5604540, 126.9817360),
        LatLng(37.5603484, 126.9816993),
        LatLng(37.5602092, 126.9816097),
        LatLng(37.5600048, 126.9814390),
        LatLng(37.5599702, 126.9813612),
        LatLng(37.5599401, 126.9812923),
        LatLng(37.5597114, 126.9807346),
        LatLng(37.5596905, 126.9806826),
        LatLng(37.5596467, 126.9805663),
        LatLng(37.5595203, 126.9801199),
        LatLng(37.5594901, 126.9800149),
        LatLng(37.5594544, 126.9798883),
        LatLng(37.5594186, 126.9797436),
        LatLng(37.5593948, 126.9796634),
        LatLng(37.5593132, 126.9793526),
        LatLng(37.5592831, 126.9792622),
        LatLng(37.5590904, 126.9788854),
        LatLng(37.5589081, 126.9786365),
        LatLng(37.5587088, 126.9784125),
        LatLng(37.5586699, 126.9783698),
    )
}