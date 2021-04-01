package com.hellow.essential_android

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SharedPreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)


        // SharedPreference
//        val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)

        // MODE
        // - MODE_PRIVATE : 생성한 application에서만 사용 가능

        // 잘 안사용함.
        // - MODE_WORLD_READABLE : 다른 application 사용 가능 (읽기만)
        // - MODE_WORLD_WRITEABLE : 다른 application 사용 가능 (읽기 + 쓰기)
        // - MODE_MULTI_PROCESS : 이미 호출되어 사용중인지 체크
        // - MODE_APPEND : 기존 preference에 신규로 추가

        // editor를 생성해서 데이터를 넣어줌
//        val editor = sharedPreference.edit()
//        editor.putString("Name", "James Park")
//        editor.putString("Age", "26")
//
//        // 커밋을 꼭 해야함
//        editor.commit()

        // sharedpreference가 개별적일 경우 서로 다른 데이터임.
        // sp1 -> Sharedpreference
        // (Key, Value) -> ("Name", "James Park")

        // sp2 -> Sharedpreference
        // (Key, Value) -> ("Name", "James Park2")

        val btnStore : Button = findViewById<Button>(R.id.btnStore).apply {
            // SharedPreference에 값을 저장하는 방법
            setOnClickListener {
                val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("Name", "James Park")
                editor.putString("Age", "26")

                // 커밋을 꼭 해야함
                editor.commit()
            }
        }

        val btnLoad : Button = findViewById<Button>(R.id.btnLoad).apply {
            // SharedPreference에 값을 불러오는 방법
            setOnClickListener {
                val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
                val value : String? = sharedPreference.getString("Name", "이름 데이터 없음")
                val value2 : String? = sharedPreference.getString("Age", "나이 데이터 없음")
                Log.d("key-value", "$value")
                Log.d("key-value", "$value2")

            }
        }

        val btnDelete : Button = findViewById<Button>(R.id.btnDelete).apply {
            // SharedPreference에 일부 선택 값을 제거하는 방법
            setOnClickListener {
                val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()

                // 키값을 넣어서 해당 데이터 제거
                editor.remove("Name")
                editor.commit()
            }
        }

        val btnAllDelete : Button = findViewById<Button>(R.id.btnAllDelete).apply {
            // SharedPreference에 모든 값을 제거하는 방법
            setOnClickListener {
                val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()

                editor.clear()
                editor.commit()
            }
        }

    }
}