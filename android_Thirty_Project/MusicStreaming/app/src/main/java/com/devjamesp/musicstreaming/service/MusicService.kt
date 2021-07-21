package com.devjamesp.musicstreaming.service

import com.devjamesp.musicstreaming.service.model.MusicDTO
import retrofit2.Call
import retrofit2.http.GET

interface MusicService {
    @GET("3e94fb85-4b59-4080-9351-8e8e28164c02")
    fun listMusics() : Call<MusicDTO>
}