package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("LifeCycle", "call onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle", "call onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle", "call onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifeCycle", "call onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifeCycle", "call onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifeCycle", "call onDestroy()")
    }
}