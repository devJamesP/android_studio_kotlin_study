package com.devjamesp.hometownfinedustapp.utility

import com.devjamesp.hometownfinedustapp.BuildConfig
import com.devjamesp.hometownfinedustapp.Url
import com.devjamesp.hometownfinedustapp.response.tm_position_entries.TmCoordinatesResponse
import retrofit2.Response

import retrofit2.http.GET

import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoLocalApiService {
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET(Url.GET_COORD_URL)
    suspend fun getTmCoordinates (
        @Query("x") longitude: Double = 0.0,
        @Query("y") latitude: Double = 0.0,
        @Query("output_coord") outputCoord: String = "TM",
    ) : Response<TmCoordinatesResponse>
}