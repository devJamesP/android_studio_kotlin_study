package com.devjamesp.musicstreaming.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devjamesp.musicstreaming.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, PlayerFragment.newInstance())
            .commit()
    }
}