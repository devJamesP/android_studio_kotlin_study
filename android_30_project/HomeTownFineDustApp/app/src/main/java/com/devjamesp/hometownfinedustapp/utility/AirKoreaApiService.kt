package com.devjamesp.hometownfinedustapp.utility

import com.devjamesp.hometownfinedustapp.Url
import com.devjamesp.hometownfinedustapp.response.air_quality_entries.AirQualityResponse
import com.devjamesp.hometownfinedustapp.response.monitoring_station_entries.MonitoringStationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AirKoreaApiService {
    @GET(Url.GET_MONITORING_STATION_URL)
    suspend fun getNearbyMonitoringStation(
        @Query("tmX") tmX: Double,
        @Query("tmY") tmY: Double,
        @Query("returnType") returnType: String,
        @Query("serviceKey") serviceKey: String
    ): Response<MonitoringStationsResponse>

    @GET(Url.GET_STATION_AIR_QUALITY_UTL)
    suspend fun getRealTimeAirQualites(
        @Query("stationName") stationName: String,
        @Query("dataTerm") dataTime: String,
        @Query("serviceKey") serviceKey: String,
        @Query("returnType") returnType: String,
        @Query("ver") ver: Float
    ) : Response<AirQualityResponse>
}