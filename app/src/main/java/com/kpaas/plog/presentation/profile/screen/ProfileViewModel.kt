package com.kpaas.plog.presentation.profile.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kpaas.plog.data.dto.response.ResponseSignUpDto
import com.kpaas.plog.domain.repository.AuthRepository
import com.kpaas.plog.domain.repository.ProfileRepository
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _logoutState = MutableStateFlow<UiState<String?>>(UiState.Empty)
    val logoutState: StateFlow<UiState<String?>> = _logoutState

    private val _signOutState = MutableStateFlow<UiState<String?>>(UiState.Empty)
    val signOutState: StateFlow<UiState<String?>> = _signOutState

    private val _getProfileState = MutableStateFlow<UiState<ResponseSignUpDto>>(UiState.Empty)
    val getProfileState: StateFlow<UiState<ResponseSignUpDto>> = _getProfileState

    fun deleteLogout() = viewModelScope.launch {
        _logoutState.emit(UiState.Loading)
        authRepository.deleteLogout().fold(
            onSuccess = {
                _logoutState.emit(UiState.Success(it))
            },
            onFailure = {
                _logoutState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }

    fun deleteSignOut() = viewModelScope.launch {
        _signOutState.emit(UiState.Loading)
        authRepository.deleteSignOut().fold(
            onSuccess = {
                _signOutState.emit(UiState.Success(it))
            },
            onFailure = {
                _signOutState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }

    fun getProfile() = viewModelScope.launch {
        _getProfileState.emit(UiState.Loading)
        profileRepository.getProfile().fold(
            onSuccess = {
                _getProfileState.emit(UiState.Success(it))
            },
            onFailure = {
                _getProfileState.emit(UiState.Failure(it.message.toString()))
            }
        )
    }
}