package com.hellow.instagram_fast

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutStargramPostActivity : AppCompatActivity() {

    lateinit var glide : RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_stargram_post)

        glide = Glide.with(this)

        (application as MasterApplication).service.getAllPosts().enqueue(
            object: Callback<ArrayList<Post>> {
                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if (response.isSuccessful) {
                        val postList = response.body()
                        val post_recyclerView = findViewById<RecyclerView>(R.id.post_recyclerView)
                        val adapter = PostAdapter(
                            postList!!,
                            LayoutInflater.from(this@OutStargramPostActivity),
                            glide
                        )
                        post_recyclerView.adapter = adapter
                        post_recyclerView.layoutManager = LinearLayoutManager(this@OutStargramPostActivity)


                    }
                }
            }
        )
        val userInfo : TextView = findViewById<TextView>(R.id.userinfo)
        val myList : TextView = findViewById(R.id.my_list)
        val upload : TextView = findViewById(R.id.upload)

        myList.setOnClickListener {
            startActivity(
                Intent(this@OutStargramPostActivity, OutStargramMyPostActivity::class.java)
            )
        }

        upload.setOnClickListener {
            startActivity(
                Intent(this@OutStargramPostActivity, OutStargramUploadActivity::class.java)
            )
        }

        userInfo.setOnClickListener {
            startActivity(
                Intent(this@OutStargramPostActivity, OutStargramUserInfo::class.java)
            )
        }
    }
}

class PostAdapter(
    val postList: ArrayList<Post>,
    val inflater: LayoutInflater,
    var glide : RequestManager
) : RecyclerView.Adapter<PostAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val postOwner : TextView
        val postImage : ImageView
        val postContent : TextView
        init {
            postOwner = itemView.findViewById<TextView>(R.id.post_owner)
            postImage = itemView.findViewById<ImageView>(R.id.post_img)
            postContent = itemView.findViewById<TextView>(R.id.post_content)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.outstargram_itemview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.postOwner.text = postList[position].owner
        holder.postContent.text = postList[position].content
        glide.load(postList[position].image).into(holder.postImage)
    }
}