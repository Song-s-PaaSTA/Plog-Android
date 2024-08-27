package com.kpaas.plog.presentation.auth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.Constants.UNKNOWN_ERROR
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kpaas.plog.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val loginState: StateFlow<UiState<String>> = _loginState
    private var accessToken: String? = null

    fun signInWithKakao(context: Context) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            val tokenResult = runCatching { loginWithKakao(context) }
            tokenResult.onSuccess { token ->
                accessToken = token.accessToken
                fetchKakaoUserInfo(context)
            }.onFailure {
                _loginState.value = UiState.Failure(it.localizedMessage ?: UNKNOWN_ERROR)
            }
        }
    }

    private suspend fun loginWithKakao(context: Context): OAuthToken {
        return suspendCancellableCoroutine { continuation ->
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    continuation.resume(token)
                }
            }
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }

    private fun fetchKakaoUserInfo(context: Context) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                _loginState.value = UiState.Failure(error.localizedMessage)
            } else if (user != null) {
                _loginState.value = UiState.Success("카카오계정 로그인 성공: ${accessToken}")
            }
        }
    }
}
