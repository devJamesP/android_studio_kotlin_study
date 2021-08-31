package com.hellow.instagram_fast

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class OutStargramUserInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_stargram_user_info)

        val allList : TextView = findViewById<TextView>(R.id.all_list)
        val myList : TextView = findViewById(R.id.my_list)
        val upload : TextView = findViewById(R.id.upload)

        allList.setOnClickListener {
            startActivity(
                Intent(this, OutStargramPostActivity::class.java)
            )
        }

        myList.setOnClickListener {
            startActivity(
                Intent(this, OutStargramMyPostActivity::class.java)
            )
        }

        upload.setOnClickListener {
            startActivity(
                Intent(this, OutStargramUploadActivity::class.java)
            )
        }

        val logout = findViewById<TextView>(R.id.logout)
        logout.setOnClickListener {
            val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("login_sp", "null")
            editor.commit()
            (application as MasterApplication).createRetrofit()
            finish()
            startActivity(
                Intent(this, LoginActivity::class.java))
        }


    }
}