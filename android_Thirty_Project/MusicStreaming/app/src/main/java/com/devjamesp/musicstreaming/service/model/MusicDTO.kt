package com.devjamesp.musicstreaming.service.model

import com.google.gson.annotations.SerializedName

data class MusicDTO(
    @SerializedName("musics") val musics: List<MusicEntity>
)
