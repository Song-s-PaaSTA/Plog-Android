package com.kpaas.plog.presentation.auth.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.Constants.UNKNOWN_ERROR
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kpaas.plog.domain.entity.LoginEntity
import com.kpaas.plog.domain.repository.AuthRepository
import com.kpaas.plog.domain.repository.UserPreferencesRepository
import com.kpaas.plog.util.UiState
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _kakaoLoginState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val kakaoLoginState: StateFlow<UiState<String>> = _kakaoLoginState

    private val _naverLoginState = MutableStateFlow<UiState<String>>(UiState.Empty)
    val naverLoginState: StateFlow<UiState<String>> = _naverLoginState

    private val _postLoginState = MutableStateFlow<UiState<LoginEntity>>(UiState.Empty)
    val postLoginState: StateFlow<UiState<LoginEntity>> = _postLoginState

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken: StateFlow<String?> = _accessToken

    private val _refreshToken = MutableStateFlow<String?>(null)
    val refreshToken: StateFlow<String?> = _refreshToken

    fun signInWithKakao(context: Context) {
        viewModelScope.launch {
            _kakaoLoginState.value = UiState.Loading
            val tokenResult = runCatching { loginWithKakao(context) }
            tokenResult.onSuccess { token ->
                _accessToken.value = token.accessToken
                _refreshToken.value = token.refreshToken
                fetchKakaoUserInfo(accessToken.value!!, refreshToken.value!!)
            }.onFailure {
                _kakaoLoginState.value = UiState.Failure(it.localizedMessage ?: UNKNOWN_ERROR)
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
                _kakaoLoginState.value = UiState.Failure(error.localizedMessage)
            } else if (user != null) {
                _kakaoLoginState.value = UiState.Success(accessToken)
                Timber.d("fetchKakaoUserInfo: $accessToken")
            }
        }
    }

    fun signInWithNaver(context: Context) {
        viewModelScope.launch {
            _naverLoginState.value = UiState.Loading
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    // 네이버 로그인 인증이 성공했을 때 수행할 코드
                    _accessToken.value = NaverIdLoginSDK.getAccessToken()
                    accessToken.value?.let { _naverLoginState.value = UiState.Success(it) }
                    Timber.d("naver accessToken: ${accessToken.value}")
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    _naverLoginState.value =
                        UiState.Failure("네이버 로그인 실패: $errorCode, $errorDescription")
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

    fun getCheckLogin() = userPreferencesRepository.getCheckLogin()

    fun saveCheckLogin(checkLogin: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveCheckLogin(checkLogin)
        }
    }

    fun saveUserRefreshToken(refreshToken: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveUserRefreshToken(refreshToken)
        }
    }

    fun getUserRefreshToken() = userPreferencesRepository.getUserRefreshToken()

    fun clear() {
        viewModelScope.launch {
            userPreferencesRepository.clear()
        }
    }

    fun postLogin(provider: String, code: String) = viewModelScope.launch {
        _postLoginState.emit(UiState.Loading)
        authRepository.postLogin(provider, code).fold(
            onSuccess = {
                _postLoginState.emit(UiState.Success(it))
            },
            onFailure = {
                _postLoginState.emit(UiState.Failure(it.localizedMessage ?: UNKNOWN_ERROR))
                Timber.e("Post Login failed: $it")
            }
        )
    }

}
