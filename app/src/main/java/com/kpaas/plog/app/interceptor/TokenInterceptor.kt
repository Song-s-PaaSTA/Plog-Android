package com.kpaas.plog.app.interceptor

import com.kpaas.plog.data.datasource.UserPreferencesDataSource
import com.kpaas.plog.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class TokenInterceptor @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
    private val provideAuthRepository: Provider<AuthRepository>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request()
        val url = request.url.toString()

        // 특정 URL에 대해 토큰을 추가하지 않고 바로 처리
        if (isUrlExemptFromToken(url)) {
            return@runBlocking chain.proceed(request)
        }

        // AccessToken 및 RefreshToken 가져오기
        var accessToken = userPreferencesDataSource.getUserAccessToken().first() ?: ""
        var refreshToken = userPreferencesDataSource.getUserRefreshToken().first() ?: ""

        // 새로운 요청 빌더 생성 및 헤더 추가
        val newRequestBuilder = buildRequestWithTokens(request, accessToken, refreshToken, url)

        // 응답 처리 및 401 에러 시 토큰 갱신 로직
        var response = chain.proceed(newRequestBuilder.build())
        if (response.code == 401 && response.code !in listOf(200, 500)) {
            response = handleTokenRenewal(chain, request, response)
        }

        response
    }

    private fun isUrlExemptFromToken(url: String): Boolean {
        return url.contains("member-service/api/v1/login/naver") ||
                url.contains("member-service/api/v1/login/kakao") ||
                url.contains("reward-service/api/v1/reward") ||
                url.contains("plogging-service/api/v1/plogging/route")
    }

    private fun buildRequestWithTokens(
        request: okhttp3.Request,
        accessToken: String,
        refreshToken: String,
        url: String
    ): okhttp3.Request.Builder {
        val newRequestBuilder = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")

        Timber.tag("interceptor").d("accessToken $accessToken")

        if (isRefreshTokenRequired(url)) {
            Timber.tag("interceptor").d("refreshToken: $refreshToken")
            newRequestBuilder.addHeader("RefreshToken", "Bearer $refreshToken")
        }

        return newRequestBuilder
    }

    private fun isRefreshTokenRequired(url: String): Boolean {
        return url.contains("member-service/api/v1/logout") ||
                url.contains("member-service/api/v1/signout") ||
                url.contains("member-service/api/v1/renew")
    }

    private suspend fun handleTokenRenewal(
        chain: Interceptor.Chain,
        request: okhttp3.Request,
        response: Response
    ): Response {
        response.close()

        val renewResult = provideAuthRepository.get().postRenew()
        if (renewResult.isSuccess) {
            val newAccessToken = renewResult.getOrNull()!!
            Timber.d("renew token : $newAccessToken")

            // 새로운 AccessToken 저장
            userPreferencesDataSource.saveUserAccessToken(newAccessToken)

            // 새로운 요청 생성 후 재시도
            val newRequest = request.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", "Bearer $newAccessToken")
                .build()

            return chain.proceed(newRequest)
        } else {
            Timber.e("token renew failure: ${response.code}")
        }

        return response
    }
}
