package com.hellow.essential_android

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class Intent1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        var change_activity = findViewById<Button>(R.id.change_activity)

        change_activity.setOnClickListener {
//            var intent = Intent(this, Intent2::class.java)
//            // key, value -> key와 value를 쌍으로 만들어 보낸다. -> Dictionary
//            intent.putExtra("number1", 1)
//            intent.putExtra("number2", 2)
//
//            startActivity(intent)

//            // 코드의 가시성을 위해서 다음과 같이 코드를 작성하는 것이 좋다.
//            val intent2 = Intent(this@Intent1, Intent2::class.java)
//            intent2.apply {
//                this.putExtra("number1", 1)
//                this.putExtra("number2", 2)
//            }
//            startActivityForResult( intent2, 200)


            // 암시적 인텐트
            // ACTION_VIEW는 뒤의 파라미터를 뷰로 보여줘라를 의미
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"))
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200) {
            val result = data?.getIntExtra("result", 0)
            Log.d("value", "result = ${result}")
        }
    }
}