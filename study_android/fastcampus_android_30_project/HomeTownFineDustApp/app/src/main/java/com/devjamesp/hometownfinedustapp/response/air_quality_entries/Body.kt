package com.devjamesp.hometownfinedustapp.response.air_quality_entries


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val measureValues: List<MeasuredValue>?,
    @SerializedName("numOfRows")
    val numOfRows: Int?,
    @SerializedName("pageNo")
    val pageNo: Int?,
    @SerializedName("totalCount")
    val totalCount: Int?
)