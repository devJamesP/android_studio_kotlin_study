package com.hellow.essential_android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class Intent2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent2)

        val value1 = intent.getIntExtra("number1", 0)
        val value2 = intent.getIntExtra("number2", 0)

        Log.d("value", "$value1")
        Log.d("value", "$value2")

        val resultBtn: Button = findViewById<Button>(R.id.btnRet)
        resultBtn.setOnClickListener {
            val result: Int = value1 + value2
            val resultIntent = Intent()
            resultIntent.putExtra("result", result)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }


    }
}