package com.hellow.mytube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class MyTubeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_tube_detail)

        val url = intent.getStringExtra("video_url")

        val mediaController = MediaController(this@MyTubeDetailActivity)

        val video_view = findViewById<VideoView>(R.id.video_view)
        video_view.setVideoPath(url)
        video_view.requestFocus()
        video_view.start()
        mediaController.show()

        // Exoplayer로 영상을 좀 더 전문적으로 다룸
        // drm -> digital right management => 아무나 이 영상을 사용하지 못하게 하려고

    }
}