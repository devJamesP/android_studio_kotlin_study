package com.devjamesp.youtubeactivity.api

import com.devjamesp.youtubeactivity.dto.VideoDTO
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {

    @GET("53f24129-3c73-4c72-a98f-b847a7c43444")
    fun listVideos(): Call<VideoDTO>
}