package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class LibraryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        // 이미지 뷰 변수 생성
        val imgView: ImageView = findViewById<ImageView>(R.id.glide)
        val imgView2: ImageView = findViewById<ImageView>(R.id.glide2)

        // OpenSource glide
        Glide.with(this@LibraryActivity)
            .load("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
            .centerCrop()
            .into(imgView);

        Glide.with(this@LibraryActivity)
            .load("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
            .fitCenter()
            .into(imgView2);
    }
}