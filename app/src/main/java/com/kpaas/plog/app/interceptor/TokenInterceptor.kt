package com.kpaas.plog.app.interceptor

import com.kpaas.plog.data.datasource.UserPreferencesDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request()
        val url = request.url.toString()

        // 특정 URL 패턴에 대해서는 토큰을 추가하지 않음
        if (url.contains("member-service/api/v1/login/naver") ||
            url.contains("member-service/api/v1/login/kakao") ||
            url.contains("api/v1/signup") ||
            url.contains("api/v1/member")) {
            // 로그인 요청 등 토큰이 필요 없는 요청의 경우
            return@runBlocking chain.proceed(request)
        }

        // 토큰이 필요한 요청의 경우
        var accessToken = userPreferencesDataSource.getUserAccessToken().first()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        Timber.tag("interceptor").d("accessToken $accessToken")
        chain.proceed(newRequest)
    }
}