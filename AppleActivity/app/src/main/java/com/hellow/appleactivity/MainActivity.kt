package com.hellow.appleactivity

import android.app.Activity
import android.media.Image
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MasterApplication).service.getSongList()
            .enqueue(object : Callback<ArrayList<Song>> {
                override fun onFailure(call: Call<ArrayList<Song>>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<ArrayList<Song>>,
                    response: Response<ArrayList<Song>>
                ) {
                    if (response.isSuccessful) {
                        val songList = response.body()
                        val adapter = MelonAdapter(
                            songList!!,
                            LayoutInflater.from(this@MainActivity),
                            Glide.with(this@MainActivity),
                            this@MainActivity
                        )
                        val song_list = findViewById<RecyclerView>(R.id.song_List)
                        song_list.adapter = adapter
                    }
                }
            })
    }

    override fun onPause() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onPause()
    }

    inner class MelonAdapter(
        val songList: ArrayList<Song>,
        val inflater: LayoutInflater,
        var glide: RequestManager,
        val activity: Activity
    ) : RecyclerView.Adapter<MelonAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView
            val thumbnail: ImageView
            val play: ImageView

            init {
                title = itemView.findViewById<TextView>(R.id.song_title)
                thumbnail = itemView.findViewById<ImageView>(R.id.song_img)
                play = itemView.findViewById<ImageView>(R.id.song_play)

                play.setOnClickListener {
                    val position : Int = adapterPosition
                    val path = songList[position].song

                    // 음악 재생
                    try {
                        mediaPlayer?.stop()
                        // 음악 리소스 점유를 해제하는 코드
                        mediaPlayer?.release()
                        mediaPlayer = null
                        mediaPlayer = MediaPlayer.create(
                            activity,
                            Uri.parse(path)
                        )
                        mediaPlayer?.start()
                    } catch (e : Exception) {

                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.song_itemview, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return songList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.title.text = songList[position].title
            glide.load(songList[position].thumbnail).into(holder.thumbnail)
        }
    }

}

