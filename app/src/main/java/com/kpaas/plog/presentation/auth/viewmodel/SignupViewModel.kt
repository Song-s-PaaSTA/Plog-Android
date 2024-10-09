package com.kpaas.plog.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.domain.repository.AuthRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _patchSignUpState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val patchSignUpState : StateFlow<UiState<Unit>> = _patchSignUpState

    fun patchSignUp(nickname: String, profileImage: File) = viewModelScope.launch {
        _patchSignUpState.emit(UiState.Loading)
        authRepository.patchSignUp(nickname, profileImage).fold(
            onSuccess = {
                _patchSignUpState.emit(UiState.Success(it))
            },
            onFailure = {
                _patchSignUpState.emit(UiState.Failure(it.message.toString()))
                Timber.e("patchSignUp error: ${it.message}")
            }
        )
    }
}