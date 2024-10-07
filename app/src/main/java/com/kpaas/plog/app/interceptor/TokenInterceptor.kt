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

        // 토큰이 필요한 요청의 경우
        val accessToken = userPreferencesDataSource.getUserAccessToken().first()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        Timber.tag("interceptor").d("accessToken $accessToken")
        chain.proceed(newRequest)
    }
}