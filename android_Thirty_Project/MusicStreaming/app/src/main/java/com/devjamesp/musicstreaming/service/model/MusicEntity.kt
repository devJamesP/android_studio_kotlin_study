package com.devjamesp.musicstreaming.service.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET


data class MusicEntity(
    @SerializedName("track") val track: String,
    @SerializedName("streamUrl") val streamUrl: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("coverUrl") val coverUrl: String
)
