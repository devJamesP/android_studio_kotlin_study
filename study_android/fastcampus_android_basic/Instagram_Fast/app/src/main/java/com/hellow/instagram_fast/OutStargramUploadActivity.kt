package com.hellow.instagram_fast

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class OutStargramUploadActivity : AppCompatActivity() {

    lateinit var filePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_stargram_upload)

        val view_pictures = findViewById<TextView>(R.id.viewPictures)
        view_pictures.setOnClickListener {
            getPicture()
        }

        val btnUpload = findViewById<TextView>(R.id.upload_post)
        btnUpload.setOnClickListener {
            uploadPost()
        }

        val all_list = findViewById<TextView>(R.id.all_list)
        all_list.setOnClickListener {
            startActivity(Intent(
                this@OutStargramUploadActivity,
                OutStargramPostActivity::class.java)
            )
        }

        val my_list = findViewById<TextView>(R.id.my_list)
        my_list.setOnClickListener {
            startActivity(Intent(
                this@OutStargramUploadActivity,
                OutStargramMyPostActivity::class.java)
            )
        }

        val userinfo = findViewById<TextView>(R.id.userinfo)
        userinfo.setOnClickListener {
            startActivity(Intent(
                this@OutStargramUploadActivity,
                OutStargramUserInfo::class.java)
            )
        }
    }

    fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val uri: Uri = data!!.data!!
            filePath = getImageFilePath(uri)
        }
    }

    // 절대 경로를 찾는다.
    fun getImageFilePath(contentUri: Uri): String {
        var columnIndex = 0
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    fun uploadPost() {
        if (!this::filePath.isInitialized) {
            Toast.makeText(this@OutStargramUploadActivity, "사진을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        val file = File(filePath)
        val fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("image", file.name, fileRequestBody)
        val content = RequestBody.create(MediaType.parse("text/plain"), getContent())
        (application as MasterApplication).service.uploadPost(
            part, content
        ).enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("test", "fail")
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Log.d("test", "test2")
                    finish()
                    startActivity(
                        Intent(
                            this@OutStargramUploadActivity,
                            OutStargramPostActivity::class.java)
                    )
                }
            }
        })
    }

    fun getContent(): String {
        val content_input = findViewById<TextView>(R.id.content_input)
        return content_input.text.toString()
    }
}