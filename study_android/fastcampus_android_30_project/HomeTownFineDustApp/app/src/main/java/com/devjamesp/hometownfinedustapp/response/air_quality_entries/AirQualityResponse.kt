package com.devjamesp.hometownfinedustapp.response.air_quality_entries


import com.google.gson.annotations.SerializedName

data class AirQualityResponse(
    @SerializedName("response")
    val response: Response?
)