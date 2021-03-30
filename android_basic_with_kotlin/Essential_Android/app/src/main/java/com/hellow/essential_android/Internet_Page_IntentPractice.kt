package com.hellow.essential_android

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.material.internal.TextWatcherAdapter

class Internet_Page_IntentPractice : AppCompatActivity() {
    lateinit var editUrl: EditText
    lateinit var btnUrl: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet__page__intent_practice)

        // 위젯 변수 생성
        getViewObject()

        // 인터넷 주소 저장 변수
        var strUrl: String = ""

        // EditTextListener
        editUrl.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                // 입력이 끝날 때 작동
                Log.d("EditText", "afterTextChanged()")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 입력 전에 작동
                Log.d("EditText", "beforeTextChanged()")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 타이핑 되는 텍스트에 변화가 있으면 작동
                Log.d("EditText", "onTextChanged()")
            }
        })


        // 버튼 이벤트 생성
        btnUrl.setOnClickListener {
            // 암시적 인텐트 생성 및 URL 저장
            strUrl = editUrl.text.toString()
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUrl))
            startActivity(intent)
        }
    }

    private fun getViewObject() {
        editUrl = findViewById<EditText>(R.id.editUrl)
        btnUrl = findViewById<Button>(R.id.btnUrl)
    }
}