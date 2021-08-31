package com.devjamesp.hometownfinedustapp.models

import com.devjamesp.hometownfinedustapp.response.air_quality_entries.Grade
import com.google.gson.annotations.SerializedName

data class MeasuredValueModel(
    val khaiGrade: Grade?,
    val so2Grade: Grade?,
    val so2Value: String?,
    val coGrade: Grade?,
    val coValue: String?,
    val o3Grade: Grade?,
    val o3Value: String?,
    val no2Grade: Grade?,
    val no2Value: String?,
    val pm10Grade: Grade?,
    val pm10Value: String?,
    val pm25Grade: Grade?,
    val pm25Value: String?
)
