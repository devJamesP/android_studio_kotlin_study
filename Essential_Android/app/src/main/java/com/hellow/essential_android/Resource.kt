package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class Resource : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)

        // 1
        val ment = resources.getString(R.string.text)
        Log.d("ment", "" + ment)

        // 2
        val ment2 = getString(R.string.text)
        Log.d( "ment", "" + ment2)

        // 3
        val color = getColor(R.color.textView_Color)
        Log.d("color", "Color : $color")

    }
}