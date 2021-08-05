package com.devjamesp.imagesearchapp.data

import com.devjamesp.imagesearchapp.BuildConfig
import com.devjamesp.imagesearchapp.data.entities.PhotoResponse
import com.devjamesp.imagesearchapp.data.models.PhotoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {
    //    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
    @GET(Url.UNSPLASH_GET_PHOTO_RANDOM_URL)
    suspend fun getRandomPhoto(
        @Query("query") query: String?,
        @Query("client_id") clientId: String = BuildConfig.UNSPLASH_ACCESS_KEY,
        @Query("count") count: Int = 30
    ) : Response<List<PhotoResponse>>
}