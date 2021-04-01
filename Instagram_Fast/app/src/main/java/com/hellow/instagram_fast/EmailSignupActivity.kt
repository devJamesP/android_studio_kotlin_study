package com.hellow.instagram_fast

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailSignupActivity : AppCompatActivity() {

    lateinit var userNameView: EditText
    lateinit var userPassword1View: EditText
    lateinit var userPassword2View: EditText
    lateinit var btnRegister: TextView
    lateinit var btnLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ((application as MasterApplication).checkIsLogin()) {
            finish()
            startActivity(
                Intent(this@EmailSignupActivity, LoginActivity::class.java)
            )
        } else {
            setContentView(R.layout.activity_emailsignup)
            initView(this@EmailSignupActivity)
            setupListener(this)

        }


    }

    fun initView(activity: Activity) {
        userNameView = activity.findViewById(R.id.username_inputbox)
        userPassword1View = activity.findViewById(R.id.password1_inputbox)
        userPassword2View = activity.findViewById(R.id.password2_inputbox)
        btnRegister = activity.findViewById(R.id.register)
        btnLogin = activity.findViewById<TextView>(R.id.login)
    }

    fun setupListener(activity: Activity) {
        btnRegister.setOnClickListener {
            register(this@EmailSignupActivity)
        }
        btnLogin.setOnClickListener {
            startActivity(
                Intent(this@EmailSignupActivity, LoginActivity::class.java)
            )
        }
    }

    fun register(activity: Activity) {
        val username = getUserName()
        val password1 = getUserPassword1()
        val password2 = getUserPassword2()
        (application as MasterApplication).service.register(username, password1, password2)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(activity, "가입에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "가입에 성공 하였습니다.", Toast.LENGTH_SHORT).show()
                        val user = response.body()
                        val token = user!!.token!!
                        saveUserToken(token, activity)
                        (application as MasterApplication).createRetrofit()
                        startActivity(
                            Intent(this@EmailSignupActivity, OutStargramPostActivity::class.java)
                        )
                    }
                }
            })

    }

    fun saveUserToken(token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.commit()
    }

    fun getUserName(): String {
        return userNameView.text.toString()
    }

    fun getUserPassword1(): String {
        return userPassword1View.text.toString()
    }

    fun getUserPassword2(): String {
        return userPassword2View.text.toString()
    }
}