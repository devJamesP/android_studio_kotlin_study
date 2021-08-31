package com.devjamesp.hometownfinedustapp.response.air_quality_entries


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("body")
    val body: Body?,
    @SerializedName("header")
    val header: Header?
)