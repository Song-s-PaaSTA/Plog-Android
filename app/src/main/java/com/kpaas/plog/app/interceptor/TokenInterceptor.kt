package com.kpaas.plog.app.interceptor

import com.kpaas.plog.data.datasource.AuthDataSource
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

        // 특정 URL 패턴에 대해서는 토큰을 추가하지 않음
        if (url.contains("member-service/api/v1/login/naver") ||
            url.contains("member-service/api/v1/login/kakao") ||
            url.contains("reward-service/api/v1/reward")
        ) {
            // 로그인 요청 등 토큰이 필요 없는 요청의 경우
            return@runBlocking chain.proceed(request)
        }

        // 토큰이 필요한 요청의 경우
        var accessToken = userPreferencesDataSource.getUserAccessToken().first()
        var refreshToken = userPreferencesDataSource.getUserRefreshToken().first()

        val newRequestBuilder = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
        Timber.tag("interceptor").d("accessToken $accessToken")

        if (url.contains("member-service/api/v1/logout") ||
            url.contains("member-service/api/v1/signout") ||
            url.contains("member-service/api/v1/renew")
        ) {
            Timber.tag("interceptor").d("refreshToken: $refreshToken")
            newRequestBuilder.addHeader("RefreshToken", "Bearer $refreshToken")
        }

        var response = chain.proceed(newRequestBuilder.build())
        if (response.code == 401) {
            // 토큰 만료 시 토큰 재발급 요청
            response.close()
            val renewResult = provideAuthRepository.get().postRenew()
            if (renewResult.isSuccess) {
                val newAccessToken = renewResult.getOrNull()!!
                Timber.d("renew token : $newAccessToken")
                userPreferencesDataSource.saveUserAccessToken(newAccessToken)
                val newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer $newAccessToken")
                    .build()
                response = chain.proceed(newRequest)
            }
        } else {
            Timber.e("token renew failure: ${response.code}")
        }
        response
    }
}