package com.devjamesp.youtubeactivity.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devjamesp.youtubeactivity.R
import com.devjamesp.youtubeactivity.dto.VideoDTO
import com.devjamesp.youtubeactivity.api.VideoService
import com.devjamesp.youtubeactivity.view.adapter.VideoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainVideoAdapter : VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            addToBackStack("...")
            replace(R.id.fragmentContainerView, PlayerFragment())
        }
        getVideoList()

        mainVideoAdapter = VideoAdapter(callback = { sources, title ->
            supportFragmentManager.fragments.find { it is PlayerFragment }?.let {
                (it as PlayerFragment).play(sources, title)
            }

        })
        findViewById<RecyclerView>(R.id.mainRecyclerView).apply {
            adapter = mainVideoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }


    private fun getVideoList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also {

            it.listVideos()
                .enqueue(object : Callback<VideoDTO> {
            override fun onResponse(call: Call<VideoDTO>, response: Response<VideoDTO>) {
                if (response.isSuccessful.not()) {
                    Toast.makeText(this@MainActivity, "비디오 데이터를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    return
                } else {
                    response.body()?.let { dto ->
                        mainVideoAdapter.submitList(dto.items)
                    }
                }
            }

                    override fun onFailure(call: Call<VideoDTO>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "비디오 데이터를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }



}