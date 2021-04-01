package com.hellow.instagram_fast

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    // annotation 문법 = @
    // baseURL 뒷부분 작성
    // 해석 : 해당 주소에 GET request를 하는데 reponse가 ArrayList<PersonFromServer>이다.
    // 우리가 받는 response를 해당 타입으로 받는것을 의미함.
    @GET("/json/students/")
    fun getStudentsList(): Call<ArrayList<PersonFromServer>>

    @POST("/json/students/")
    fun createStudent(
        @Body params : HashMap<String, Any>) : Call<PersonFromServer>

    @POST("/json/students/")
    fun createStudentEasy(
        @Body person : PersonFromServer
    ) : Call<PersonFromServer>

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String
    ): Call<User>

    // 포스트맨으로 확인해보면 API 요청시 토큰을 응답하는데
    // User타입으로 선택적(token만)으로 응답 받을 수 있음.
    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("/instagram/post/list/all/")
    fun getAllPosts():Call<ArrayList<Post>>

    @Multipart
    @POST("/instagram/post/")
    fun uploadPost(
        @Part image : MultipartBody.Part,
        @Part ("content")requestBody: RequestBody
    ) : Call<Post>

    @GET("instagram/post/list/")
    fun getUserPostList():Call<ArrayList<Post>>
}