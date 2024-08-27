package com.kpaas.plog.presentation.auth.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kpaas.plog.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val loginState: StateFlow<UiState<String>> = _loginState

    fun loginWithKakao() {
        viewModelScope.launch {
            _loginState.value = UiState.Loading

            val context = getApplication<Application>().applicationContext

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        // 카카오톡으로 로그인 실패 시 처리
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            _loginState.value = UiState.Failure("카카오톡 로그인 취소")
                            return@loginWithKakaoTalk
                        } else {
                            _loginState.value = UiState.Failure("카카오톡 로그인 실패: ${error.message}")
                            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                            loginWithKakaoAccount(context)
                        }
                    } else if (token != null) {
                        // 카카오톡으로 로그인 성공 시 처리
                        _loginState.value = UiState.Success("카카오톡 로그인 성공: ${token.accessToken}")
                        Timber.d("카카오톡 로그인 성공: ${token.accessToken}")
                    }
                }
            } else {
                // 카카오톡이 설치되지 않은 경우, 카카오계정으로 로그인
                loginWithKakaoAccount(context)
            }
        }
    }

    private fun loginWithKakaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
            if (error != null) {
                // 카카오계정으로 로그인 실패 시 처리
                _loginState.value = UiState.Failure("카카오계정 로그인 실패: ${error.message}")
            } else if (token != null) {
                // 카카오계정으로 로그인 성공 시 처리
                _loginState.value = UiState.Success("카카오계정 로그인 성공: ${token.accessToken}")
                Timber.d("카카오계정 로그인 성공: ${token.accessToken}")
            }
        }
    }
}
