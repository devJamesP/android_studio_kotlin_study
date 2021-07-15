package com.devjamesp.airbnbactivity.application

import android.app.Application
import com.devjamesp.airbnbactivity.R
import com.naver.maps.map.NaverMapSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(applicationContext).client =
            NaverMapSdk.NaverCloudPlatformClient(getString(R.string.naver_map_key))
    }
}