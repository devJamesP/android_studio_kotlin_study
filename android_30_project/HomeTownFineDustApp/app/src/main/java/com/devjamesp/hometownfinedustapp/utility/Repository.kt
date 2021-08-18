package com.devjamesp.hometownfinedustapp.utility

import android.util.Log
import com.devjamesp.hometownfinedustapp.BuildConfig
import com.devjamesp.hometownfinedustapp.Url
import com.devjamesp.hometownfinedustapp.models.MeasuredValueModel
import com.devjamesp.hometownfinedustapp.models.MonitoringStationModel
import com.devjamesp.hometownfinedustapp.response.air_quality_entries.MeasuredValue
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object Repository {
    suspend fun getNearbyMonitoringStation(
        longitude: Double,
        latitude: Double,
    ): MonitoringStationModel? {
        try {
            val tmCoordinatesResponse = kakaoLocalApiService
                .getTmCoordinates(longitude, latitude)

            if (tmCoordinatesResponse.isSuccessful.not()) {
                Log.e("Error", "don`t response data")
            } else {
                val tmCoordinates = tmCoordinatesResponse.tmCoordinatesResponseToLocationModel()
                val tmX = tmCoordinates.x
                val tmY = tmCoordinates.y

                val monitoringStationsResponse = airKoreaApiService
                    .getNearbyMonitoringStation(
                        tmX!!,
                        tmY!!,
                        "json",
                        BuildConfig.AIRKOREA_SERVICE_KEY
                    )

                if (monitoringStationsResponse.isSuccessful.not()) {
                    Log.e("Error", "don`t response data2")
                } else {
                    return monitoringStationsResponse.monitoringStationsResponseToItemModel()
                }
            }
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
        return null
    }

    private val kakaoLocalApiService: KakaoLocalApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.KAKAO_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .client(buildOkHttpClient())
            .build()
            .create(KakaoLocalApiService::class.java)
    }

    private val airKoreaApiService: AirKoreaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.AIRKOREA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()
            .create(AirKoreaApiService::class.java)
    }

    suspend fun getLatestAirQualityData(stationName: String): MeasuredValueModel =
        airKoreaApiService
            .getRealTimeAirQualites(
                stationName,
                "DAILY",
                BuildConfig.AIRKOREA_SERVICE_KEY,
                "json",
                1.3f
            ).airQualityResponseToAirQualityModel()


    private fun buildOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
}