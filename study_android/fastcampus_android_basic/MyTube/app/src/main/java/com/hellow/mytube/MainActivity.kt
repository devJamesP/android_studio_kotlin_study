package com.hellow.mytube

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var glide : RequestManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MasterApplication).service.getYoutubeList()
                .enqueue( object: Callback<ArrayList<Youtube>>{
                override fun onFailure(call: Call<ArrayList<Youtube>>, t: Throwable) {
                    Log.d("test", "test")
                }

                override fun onResponse(
                    call: Call<ArrayList<Youtube>>,
                    response: Response<ArrayList<Youtube>>
                ) {
                    if (response.isSuccessful) {
                        glide = Glide.with(this@MainActivity)
                        val youtubeList = response.body()
                        val adapter = MyTubeAdapter(
                            youtubeList!!,
                            LayoutInflater.from(this@MainActivity),
                            glide,
                            this@MainActivity
                        )

                        val youtube_list_recycler = findViewById<RecyclerView>(R.id.youtube_list_recycler)
                        youtube_list_recycler.adapter = adapter

                    }
                }
            }
        )
    }
}

class MyTubeAdapter(
    val youtubeList: ArrayList<Youtube>,
    val inflater: LayoutInflater,
    var glide : RequestManager,
    val activity: Activity
) : RecyclerView.Adapter<MyTubeAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val youtubeTitle: TextView
        val youtubeImage: ImageView
        val youtubeContent: TextView

        init {
            youtubeTitle = itemView.findViewById<TextView>(R.id.youtube_title)
            youtubeImage = itemView.findViewById<ImageView>(R.id.youtube_thumbnail)
            youtubeContent = itemView.findViewById<TextView>(R.id.youtube_content)

            itemView.setOnClickListener {
                val position : Int = adapterPosition
                val intent = Intent(activity, MyTubeDetailActivity::class.java)
                intent.putExtra("video_url", youtubeList[position].video)
                activity.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.youtube_itemview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return youtubeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.youtubeTitle.text = youtubeList[position].title
        holder.youtubeContent.text = youtubeList[position].content
        glide.load(youtubeList[position].thumbnail).into(holder.youtubeImage)
    }
}