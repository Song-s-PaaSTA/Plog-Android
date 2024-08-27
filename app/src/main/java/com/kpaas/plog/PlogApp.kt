package com.kpaas.plog

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PlogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setTimber()
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(NAVER_MAP_CLIENT_ID)
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }

    private fun setTimber() {
        Timber.plant(Timber.DebugTree())
    }
}

const val NAVER_MAP_CLIENT_ID = "vr9dgbdwpt"