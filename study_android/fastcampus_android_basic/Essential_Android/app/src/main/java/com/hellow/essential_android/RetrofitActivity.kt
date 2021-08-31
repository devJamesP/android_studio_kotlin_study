package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.zip.Inflater

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

// 서버 도메인 주소 : http://mellowcode.org/ <- 변하지 않는 주소
// http://mellowcode.org/json/students/
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RetrofitService::class.java)

//            var buffer = ""
//            if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
//                val reader = BufferedReader(
//                    InputStreamReader(
//                        connection.inputStream,
//                        "UTF-8"
//                    )
//                )
//                buffer = reader.readLine()
//            }
//            해당 코드와 동일
//            if (response.isSuccessful) {
//                val personList = response.body()
//            }

        // enqueue()는 대기줄에 올려놓는다는 의미로 해당 통신을 대기줄에 올려놓은거임.
        // GET요청
        service.getStudentsList().enqueue(object : Callback<ArrayList<PersonFromServer>> {
            override fun onFailure(call: Call<ArrayList<PersonFromServer>>, t: Throwable) {
                // 통신 실패시
                Log.d("retrofitt", "ERROR")
            }

            override fun onResponse(
                call: Call<ArrayList<PersonFromServer>>,
                response: Response<ArrayList<PersonFromServer>>
            ) {
                // 통신이 잘 되어서 응답 시  isSuccessful -> code >= 200 && code < 300
                if (response.isSuccessful) {
                    val personList = response.body()

                    Log.d("retrofitt", "res : ${personList?.get(0)?.age}")

                    // 해당 status code값
                    val code = response.code()
                    Log.d("retrofitt", "$code")

                    // 200 외에 다른 204, 205 등이 올수도 있음. 그것을 사용자에게 정보를 주기 위해서 사용
                    val error = response.errorBody()
                    Log.d("retrofitt", "$error")

                    // 데이터에 대한 인증 정보, 요약
                    val header = response.headers()
                    Log.d("retrofitt", "$header")
                }
            }
        })

        // POST요청
//        val params = HashMap<String, Any>()
//        params.put("name", "김정은")
//        params.put("age", 20)
//        params.put("intro", "핵탄두 미사일 날래 쏘라우!")
//        service.createStudent(params).enqueue(object: Callback<PersonFromServer> {
//            override fun onFailure(call: Call<PersonFromServer>, t: Throwable) {
//                Log.d("retrofitt", "ERROR")
//            }
//
//            override fun onResponse(
//                call: Call<PersonFromServer>,
//                response: Response<PersonFromServer>
//            ) {
//                if (response.isSuccessful) {
//                    val person = response.body()
//                    Log.d("retrofitt", "name : " + person?.name)
//                }
//            }
//        })

        // POST2요청 id는 사용자가 입력하지 않음
        val person = PersonFromServer(name = "text", age = 20, intro = "text" )
        service.createStudentEasy(person).enqueue(object: Callback<PersonFromServer> {
            override fun onFailure(call: Call<PersonFromServer>, t: Throwable) {
                Log.d("retrofitt", "ERROR")
            }

            override fun onResponse(
                call: Call<PersonFromServer>,
                response: Response<PersonFromServer>
            ) {
                if (response.isSuccessful) {
                    val person = response.body()
                    Log.d("retrofitt", "name : " + person?.name)
                }
            }
        })

    }
}

