package com.hellow.instagram_fast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class OutStargramMyPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_stargram_my_post)

        val userInfo : TextView = findViewById(R.id.userinfo)
        val upload : TextView = findViewById(R.id.upload)

        upload.setOnClickListener {
            startActivity(
                Intent(this, OutStargramUploadActivity::class.java)
            )
        }

        userInfo.setOnClickListener {
            startActivity(
                Intent(this, OutStargramUserInfo::class.java)
            )
        }
    }
}