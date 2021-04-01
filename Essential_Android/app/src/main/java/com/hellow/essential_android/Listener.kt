package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class Listener : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listener)

        val textView: TextView = findViewById<TextView>(R.id.hello)
        val imgView: ImageView = findViewById<ImageView>(R.id.imgView)

        // 일반적인 함수를 작성하고 이벤트 리스너를 호출
        val click = object: View.OnClickListener{
            override fun onClick(p0: View?) {
                Log.d("click", "Click TextVIew")
            }
        }

        textView.setOnClickListener(click)

        // 익명함수로 작성
        textView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                Log.d("click", "Click TextVIew")
            }
        })

        // 람다식으로 작성
        textView.setOnClickListener{
            Log.d("click", "Click TextVIew")
            textView.setText("닥치세요")
            imgView.setImageResource(R.drawable.gradient)
        }

        // 뷰를 조작 하는 함수들
        textView.setText("닥치세요")
    }
}