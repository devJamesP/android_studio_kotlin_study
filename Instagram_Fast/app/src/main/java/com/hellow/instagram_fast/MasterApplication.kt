package com.hellow.instagram_fast

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// application이 activity보다 상위에 있음(manifests.xml파일 확인)
// 따라서 onCreate()메서드 역시 액티비티의 onCreate()메서드 보다 선행됨.
// 이렇게 하면 다른 activity에서도 retrofit을 가져다 쓸 수 있다.
// 또한 manifest.xml에 이런 클래스를 만들었다고 적어줘야 함.
class MasterApplication : Application() {
    lateinit var service: RetrofitService
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        createRetrofit()
    }

    fun createRetrofit() {
        // interceptor - 우리 폰에서 통신이 나갈때 가로채서 original변수에 잡아둠
        // 그리고 original변수를 수정한 후에 다시 통신함.
        val header = Interceptor {
            val original = it.request()
            if (checkIsLogin()) {
                getUserToken()?.let { token ->
                    val request = original.newBuilder()
                        .header("Authorization", "token $token")
                        .build()
                    it.proceed(request)
                }
            } else {
                it.proceed(original)
            }
        }

        // 모든 통신을 낚아채는데 stetho인터셉터가 낚아챔 그리고 화면으로 보여줌
        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }


    fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        return token != "null"
    }

    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if (token == "null") return null
        else return token
    }

}