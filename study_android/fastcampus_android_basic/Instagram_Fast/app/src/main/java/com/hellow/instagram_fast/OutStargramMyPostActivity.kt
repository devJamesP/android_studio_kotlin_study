package com.hellow.instagram_fast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutStargramMyPostActivity : AppCompatActivity() {

    lateinit var myPostRecyclerView : RecyclerView
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_stargram_my_post)

        val btnuserInfo : TextView = findViewById(R.id.userinfo)
        val btnupload : TextView = findViewById(R.id.upload)
        val btnAllList : TextView = findViewById(R.id.all_list)

        btnAllList.setOnClickListener {
            startActivity(
                Intent(this@OutStargramMyPostActivity, OutStargramPostActivity::class.java)
            )
        }

        btnupload.setOnClickListener {
            startActivity(
                Intent(this@OutStargramMyPostActivity, OutStargramUploadActivity::class.java)
            )
        }

        btnuserInfo.setOnClickListener {
            startActivity(
                Intent(this@OutStargramMyPostActivity, OutStargramUserInfo::class.java)
            )
        }

        myPostRecyclerView = findViewById(R.id.mypost_recyclerView)
        glide = Glide.with(this@OutStargramMyPostActivity)
        createList()
    }

    fun createList() {
        (application as MasterApplication).service.getUserPostList().enqueue(
            object: Callback<ArrayList<Post>> {
                override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<ArrayList<Post>>,
                    response: Response<ArrayList<Post>>
                ) {
                    if (response.isSuccessful) {
                        val myPostList = response.body()
                        val adapter = MyPostAdapter(
                            myPostList!!,
                            LayoutInflater.from(this@OutStargramMyPostActivity),
                            glide
                        )
                        myPostRecyclerView.adapter = adapter
                        myPostRecyclerView.layoutManager = LinearLayoutManager(this@OutStargramMyPostActivity)
                    }
                }
            }
        )
    }
}

class MyPostAdapter(
    val postList: ArrayList<Post>,
    val inflater: LayoutInflater,
    var glide : RequestManager
) : RecyclerView.Adapter<MyPostAdapter.ViewHolder>(){
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
