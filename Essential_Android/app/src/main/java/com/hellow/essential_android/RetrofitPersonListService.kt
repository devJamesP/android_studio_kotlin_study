package com.hellow.essential_android

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitPersonListService {
    @GET("/json/students/")
    fun getStudentsList(): Call<Array<PersonFromServer>>
}