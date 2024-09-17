package com.kpaas.plog.presentation.plogging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.entity.ExampleEntity
import com.kpaas.plog.domain.repository.ExampleRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    val exampleRepository: ExampleRepository
) : ViewModel() {
    private val _getExampleState = MutableSharedFlow<UiState<List<ExampleEntity>>>(replay = 1)
    val getExampleState: SharedFlow<UiState<List<ExampleEntity>>> = _getExampleState

    fun getUsers(page: Int) = viewModelScope.launch {
        _getExampleState.emit(UiState.Loading)
        exampleRepository.getUsers(page).fold(
            {
                if (it.isNotEmpty()) _getExampleState.emit(UiState.Success(it)) else _getExampleState.emit(UiState.Failure("400"))
            },
            { _getExampleState.emit(UiState.Failure(it.message.toString())) }
        )
    }
}