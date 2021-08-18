package com.devjamesp.imagesearchapp.data

import com.devjamesp.imagesearchapp.BuildConfig
import com.devjamesp.imagesearchapp.data.models.PhotoModel
import com.devjamesp.imagesearchapp.data.models.mapToPhototDataList
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {
    val unsplashApiService: UnsplashApiService by lazy {
        getRetrofitUtil().create(UnsplashApiService::class.java)
    }

    suspend fun getRandomPhotos(query: String?): List<PhotoModel>? =
        unsplashApiService.getRandomPhoto(query).body()?.mapToPhototDataList()

    private fun getRetrofitUtil(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Url.UNSPLASH_BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .client(buildOkHttpClient())
            .build()

    private fun buildOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
}