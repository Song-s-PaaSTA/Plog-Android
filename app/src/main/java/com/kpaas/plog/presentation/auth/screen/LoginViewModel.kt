package com.kpaas.plog.presentation.auth.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.Constants.UNKNOWN_ERROR
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kpaas.plog.domain.repository.UserPreferencesRepository
import com.kpaas.plog.util.UiState
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<List<String>>>(UiState.Empty)
    val loginState: StateFlow<UiState<List<String>>> = _loginState
    private var accessToken: String? = null
    private var refreshToken: String? = null

    fun signInWithKakao(context: Context) {
        viewModelScope.launch {
            _loginState.value = UiState.Loading
            val tokenResult = runCatching { loginWithKakao(context) }
            tokenResult.onSuccess { token ->
                accessToken = token.accessToken
                refreshToken = token.refreshToken
                fetchKakaoUserInfo(accessToken!!, refreshToken!!)
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

    private fun fetchKakaoUserInfo(accessToken: String, refreshToken: String) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                _loginState.value = UiState.Failure(error.localizedMessage)
            } else if (user != null) {
                _loginState.value = UiState.Success(listOf(accessToken, refreshToken))
            }
        }
    }

    fun signInWithNaver(context: Context) {
        viewModelScope.launch {
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    // 네이버 로그인 인증이 성공했을 때 수행할 코드
                    val accessToken = NaverIdLoginSDK.getAccessToken()
                    val refreshToken = NaverIdLoginSDK.getRefreshToken()
                    val expiresAt = NaverIdLoginSDK.getExpiresAt()
                    val tokenType = NaverIdLoginSDK.getTokenType()
                    val state = NaverIdLoginSDK.getState()

                   _loginState.value = UiState.Success(listOf(accessToken!!, refreshToken!!))
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    _loginState.value = UiState.Failure("네이버 로그인 실패: $errorCode, $errorDescription")
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }

            NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
        }
    }

    fun getUserAccessToken() = userPreferencesRepository.getUserAccessToken()

    fun saveUserAccessToken(accessToken: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserAccessToken(accessToken)
        }
    }

    fun getUserRefreshToken() = userPreferencesRepository.getUserRefreshToken()

    fun saveUserRefreshToken(refreshToken: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserRefreshToken(refreshToken)
        }
    }

    fun getCheckLogin() = userPreferencesRepository.getCheckLogin()

    fun saveCheckLogin(checkLogin: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveCheckLogin(checkLogin)
        }
    }

    fun clear() {
        viewModelScope.launch {
            userPreferencesRepository.clear()
        }
    }
}
