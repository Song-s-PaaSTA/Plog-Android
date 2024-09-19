package com.kpaas.plog.presentation.plogging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.repository.PloggingPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PloggingViewModel @Inject constructor(
    private val ploggingPreferencesRepository: PloggingPreferencesRepository
) : ViewModel() {

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
}