package com.jamespark_h.electronicframeactivity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()

    // timerPostion
    private var currentPosition = 0

    private var timer : Timer? = null

    private val photoImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.photoImageView)
    }

    private val backgroundPhotoImageView: ImageView by lazy {
        findViewById<ImageView>(R.id.backgroundPhotoImageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoframe)

        // accept a photoUri from intent
        getPhotoUriFromIntent()

    }

    private fun getPhotoUriFromIntent() {

        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0 until size) {
            intent.getStringExtra("photo$i")?.let {
                // .toUri()
                photoList.add(Uri.parse(it))
            }
        }
    }

    // Method :: Swap about Timer
    private fun startTimer() {
        // run every 5000ms
        timer = timer(period = 5 * 1000) {
            // UI Task
            runOnUiThread {
                // current Position -> next Position logic
                // photo cycle
                val current = currentPosition
                val next = if (photoList.size <= current + 1) 0 else current + 1

                backgroundPhotoImageView.setImageURI(photoList[current])

                // alpha setting
                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                currentPosition = next
            }
        }
    }


    override fun onStop() {
        super.onStop()
        // The activity is no longer visible
        timer?.cancel()

    }

    override fun onStart() {
        super.onStart()

        // retry
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()

        timer?.cancel()
    }

}