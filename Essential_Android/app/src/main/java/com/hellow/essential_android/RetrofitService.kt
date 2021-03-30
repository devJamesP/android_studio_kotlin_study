package com.hellow.essential_android

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    // annotation 문법 = @
    // bsdeURL 뒷부분 작성
    // 해석 : 해당 주소에 GET request를 하는데 reponse가 ArrayList<PersonFromServer>이다.
    // 우리가 받는 response를 해당 타입으로 받는것을 의미함.
    @GET("/json/students/")
    fun getStudentsList(): Call<ArrayList<PersonFromServer>>

    @POST("/json/students/")
    fun createStudent(
        // Key는 String타입이고 Value는 다양한 타입이 올 수 있으므로
        @Body params : HashMap<String, Any>) : Call<PersonFromServer>

    @POST("/json/students/")
    fun createStudentEasy(
        @Body person : PersonFromServer
    ) : Call<PersonFromServer>
}