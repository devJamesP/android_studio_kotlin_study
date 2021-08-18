package com.devjamesp.hometownfinedustapp.utility

import com.devjamesp.hometownfinedustapp.models.MonitoringStationModel
import com.devjamesp.hometownfinedustapp.models.LocationModel
import com.devjamesp.hometownfinedustapp.models.MeasuredValueModel
import com.devjamesp.hometownfinedustapp.response.air_quality_entries.AirQualityResponse
import com.devjamesp.hometownfinedustapp.response.air_quality_entries.Grade
import com.devjamesp.hometownfinedustapp.response.air_quality_entries.MeasuredValue
import com.devjamesp.hometownfinedustapp.response.monitoring_station_entries.MonitoringStationsResponse
import com.devjamesp.hometownfinedustapp.response.tm_position_entries.TmCoordinatesResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Response

fun Response<TmCoordinatesResponse>.tmCoordinatesResponseToLocationModel(): LocationModel {
    val tmCoordinates = this.body()?.documents?.firstOrNull()
    return LocationModel(tmCoordinates?.x, tmCoordinates?.y)
}

fun Response<MonitoringStationsResponse>.monitoringStationsResponseToItemModel(): MonitoringStationModel {
    val monitoringStation =
        this.body()?.response?.body?.items?.minByOrNull { it.tm ?: Double.MAX_VALUE }
    return MonitoringStationModel(
        monitoringStation?.addr,
        monitoringStation?.stationName
    )
}

fun Response<AirQualityResponse>.airQualityResponseToAirQualityModel(): MeasuredValueModel {
    val measuredValue = this.body()?.response?.body?.measureValues?.firstOrNull()
    return MeasuredValueModel(
        measuredValue?.khaiGrade,
        measuredValue?.so2Grade,
        measuredValue?.so2Value,
        measuredValue?.coGrade,
        measuredValue?.coValue,
        measuredValue?.o3Grade,
        measuredValue?.o3Value,
        measuredValue?.no2Grade,
        measuredValue?.no2Value,
        measuredValue?.pm10Grade,
        measuredValue?.pm10Value,
        measuredValue?.pm25Grade,
        measuredValue?.pm25Value
    )
}
