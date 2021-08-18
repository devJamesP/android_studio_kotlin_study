package com.devjamesp.hometownfinedustapp.response.monitoring_station_entries

data class Body(
    val items: List<Item>?,
    val numOfRows: Int?,
    val pageNo: Int?,
    val totalCount: Int?
)