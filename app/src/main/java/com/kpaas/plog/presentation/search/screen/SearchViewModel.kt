package com.kpaas.plog.presentation.search.screen

import androidx.lifecycle.ViewModel
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
}