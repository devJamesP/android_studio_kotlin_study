package com.hellow.instagram_fast

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnRegister: TextView = findViewById<TextView>(R.id.register)
        btnRegister.setOnClickListener{
            val intent = Intent(this, EmailSignupActivity::class.java)
            startActivity(intent)
        }

        val btnLogin : TextView = findViewById<TextView>(R.id.login)
        val userName : EditText = findViewById(R.id.username_inputbox)
        val userPassword : EditText = findViewById(R.id.password_inputbox)

        btnLogin.setOnClickListener {
            val txUserName = userName.text.toString()
            val txPassword = userPassword.text.toString()
            (application as MasterApplication).service.login(
                txUserName, txPassword
            ).enqueue(object: Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {

                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        val token = user!!.token!!
                        saveUserToken(token , this@LoginActivity)
                        (application as MasterApplication).createRetrofit()
                        Toast.makeText(this@LoginActivity, "로그인 하셨습니다.", Toast.LENGTH_SHORT).show()

                        startActivity(
                            Intent(this@LoginActivity, OutStargramPostActivity::class.java)
                        )
                    }

                }
            })
        }
    }

    fun saveUserToken(token: String, activity: Activity) {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("login_sp", token)
        editor.commit()
    }
}