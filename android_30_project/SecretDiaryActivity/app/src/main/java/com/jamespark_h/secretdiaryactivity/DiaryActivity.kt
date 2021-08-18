package com.jamespark_h.secretdiaryactivity

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    // 메인 쓰레드에 연결된 핸들러 생성
    private val handler = Handler(Looper.getMainLooper())

    val editScretDiary: EditText by lazy {
        findViewById<EditText>(R.id.editSecretDiary)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val detailPreferences =
                getSharedPreferences("detail", Context.MODE_PRIVATE)

        // 기존 작성 글 불러오기
        editScretDiary.setText(detailPreferences.getString("detail", ""))

        // 비동기 처리
        val runnable = Runnable {
            getSharedPreferences("detail", Context.MODE_PRIVATE).edit {
                putString("detail", editScretDiary.text.toString())
            }

            Log.d(TAG, "DiaryActivity :: SAVE!!!! ${editScretDiary.text.toString()}")
        }


        // editText 수정 시 동작
        editScretDiary.addTextChangedListener {

            Log.d(TAG, "TextChanged :: $it")

            // 이전 runnable이 존재하면 지움.
            handler.removeCallbacks(runnable)

            // 500ms에 한번씩 저장이 수행됨 (수정 후 0.5초 이후 변화가 없으면 postDelayed() 실행)
            handler.postDelayed(runnable,500)
        }
    }

    companion object {
        private const val TAG = "DiaryActivity"
    }
}