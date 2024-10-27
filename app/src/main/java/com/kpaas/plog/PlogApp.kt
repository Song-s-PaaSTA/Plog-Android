package com.kpaas.plog

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kpaas.plog.BuildConfig.KAKAO_API_KEY
import com.kpaas.plog.BuildConfig.NAVER_CLIENT_ID
import com.kpaas.plog.BuildConfig.NAVER_CLIENT_SECRET
import com.kpaas.plog.BuildConfig.NAVER_MAP_CLIENT_ID
import com.naver.maps.map.NaverMapSdk
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class PlogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setTimber()
        initNaverMapSdk()
        initKakaoSdk()
        initNaverIdLoginSdk()
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, KAKAO_API_KEY)
    }

    private fun initNaverIdLoginSdk() {
        NaverIdLoginSDK.initialize(
            this,
            NAVER_CLIENT_ID,
            NAVER_CLIENT_SECRET,
            getString(R.string.app_name)
        )
    }

    private fun initNaverMapSdk() {
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(NAVER_MAP_CLIENT_ID)
    }

    private fun setTimber() {
        Timber.plant(Timber.DebugTree())
    }
}