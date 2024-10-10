package com.kpaas.plog.presentation.map.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.entity.MarkerEntity
import com.kpaas.plog.domain.repository.TrashRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val trashRepository: TrashRepository
) : ViewModel() {
    private val _getTrashState = MutableStateFlow<UiState<List<MarkerEntity>>>(UiState.Empty)
    val getTrashState : StateFlow<UiState<List<MarkerEntity>>> = _getTrashState

    fun getTrash() = viewModelScope.launch {
        _getTrashState.emit(UiState.Loading)
        trashRepository.getTrash().fold(
            onSuccess = {
                _getTrashState.emit(UiState.Success(it))
            },
            onFailure = {
                _getTrashState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }
}