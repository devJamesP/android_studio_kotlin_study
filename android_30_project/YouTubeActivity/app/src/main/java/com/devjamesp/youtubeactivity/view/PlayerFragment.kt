package com.devjamesp.youtubeactivity.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devjamesp.youtubeactivity.R
import com.devjamesp.youtubeactivity.api.VideoService
import com.devjamesp.youtubeactivity.databinding.FragmentPlayerBinding
import com.devjamesp.youtubeactivity.dto.VideoDTO
import com.devjamesp.youtubeactivity.view.adapter.VideoAdapter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.abs

class PlayerFragment : Fragment(R.layout.fragment_player) {

    private var binding: FragmentPlayerBinding? = null
    private lateinit var fragmentVideoAdapter: VideoAdapter

    private var player: SimpleExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentPlayerBinding = FragmentPlayerBinding.bind(view)
        binding = fragmentPlayerBinding

        initMotionLayoutEvent(fragmentPlayerBinding)
        initRecyclerView(fragmentPlayerBinding)
        initPlayer(fragmentPlayerBinding)
        initControllerButton(fragmentPlayerBinding)

        getVideoList()
    }

    private fun initMotionLayoutEvent(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.playerMotionLayout.setTransitionListener(object :
            MotionLayout.TransitionListener {
            override fun onTransitionChange(
                motionLayout: MotionLayout,
                startId: Int,
                endId: Int,
                _progress: Float
            ) {
                binding?.let {
                    // 프래그먼트는 액티비티 없이 단독으로 존재 할 수 없고, 어떤 액티비티에 붙어있는지 알 수 없다.
                    (activity as MainActivity).also { mainActivity ->
                        mainActivity.findViewById<MotionLayout>(R.id.mainMotionLayout).progress =
                            abs(_progress)
                    }
                }
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })
    }

    private fun initRecyclerView(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentVideoAdapter = VideoAdapter(callback = { sources, title ->
            play(sources, title)
        })

        fragmentPlayerBinding.fragmentRecyclerView.apply {
            adapter = fragmentVideoAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initPlayer(fragmentPlayerBinding: FragmentPlayerBinding) {
        context?.let {
            player = SimpleExoPlayer.Builder(it).build()
        }

        fragmentPlayerBinding.playerView.player = player
        player?.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (isPlaying) {
                    fragmentPlayerBinding.bottomPlayerControlButton.setImageResource(R.drawable.exo_controls_pause)
                } else {
                    fragmentPlayerBinding.bottomPlayerControlButton.setImageResource(R.drawable.exo_controls_play)
                }
            }
        })

    }


    private fun initControllerButton(fragmentPlayerBinding: FragmentPlayerBinding) {
        fragmentPlayerBinding.bottomPlayerControlButton.setOnClickListener {
            val player = this.player ?: return@setOnClickListener

            if (player.isPlaying) {
                player.pause()
            } else {
                player.play()
            }
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
                            // 오류 처리
                        } else {
                            response.body()?.let { dto ->
                                fragmentVideoAdapter.submitList(dto.items)
                            }
                        }
                    }

                    override fun onFailure(call: Call<VideoDTO>, t: Throwable) {
                        // 오류 처리
                    }
                })
        }
    }


    fun play(url: String, title: String) {
        context?.let {
            val dataSourceFactory = DefaultDataSourceFactory(it)
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(url)))

            player?.setMediaSource(mediaSource)
            player?.prepare()
            player?.play()
        }



        binding?.let {
            it.playerMotionLayout.transitionToEnd()
            it.bottomTitleTextView.text = title
        }
    }


    override fun onStop() {
        super.onStop()

        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        player?.release()
    }
}