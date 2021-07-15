package com.devjamesp.airbnbactivity.api

import com.devjamesp.airbnbactivity.model.HouseDTO
import retrofit2.Call
import retrofit2.http.GET

interface HouseService {
    @GET("/v3/c5e5b467-7b46-481f-aa57-628663dfc59b")
    fun getHouseList() : Call<HouseDTO>
}