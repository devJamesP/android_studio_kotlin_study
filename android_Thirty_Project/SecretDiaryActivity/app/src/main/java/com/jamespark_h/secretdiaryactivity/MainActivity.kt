package com.jamespark_h.secretdiaryactivity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {
    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
                .apply {
                    minValue = 0
                    maxValue = 9
                }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
                .apply {
                    minValue = 0
                    maxValue = 9
                }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
                .apply {
                    minValue = 0
                    maxValue = 9
                }
    }

    private val numberPicker4: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker4)
                .apply {
                    minValue = 0
                    maxValue = 9
                }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btnOpen)
    }

    private val changeButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btnChange)
    }

    // 비밀번호 변경 동작 모드
    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // view가 생성된 이후에 각각의 컴포넌트를 초기화해주어야 하므로 작성해줌.
        numberPicker1
        numberPicker2
        numberPicker3
        numberPicker4

        openButton.setOnClickListener {
            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호를 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 어떤 가벼운 데이터를 저장하는 용도로 비휘발성으로 사용
            val passwordPreferences =
                    getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}${numberPicker4.value}"

            if (passwordPreferences.getString("password", "0000").equals(passwordFromUser)) {
                // password일치
                Toast.makeText(this, "패스워드 일치", Toast.LENGTH_SHORT).show()
                // TODO 다이어리 페이지 작성 후에 넘겨주어야 함.
                startActivity(
                        Intent(this, DiaryActivity::class.java)
                )
            } else {
                // 실패
                // Error 팝업 처리
                showErrorAlertDialog()
            }
        }

        changeButton.setOnClickListener {
            val passwordPreferences =
                    getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}${numberPicker4.value}"

            if (changePasswordMode) {
                // 넘버피커에서 변경한 번호를 저장 모드
                // 내부 commit()를 호출하거나 edit()의 commit값을 true로 해줌
                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                    // commit()
                }

                changePasswordMode = false
                changeButton.setBackgroundColor(Color.BLACK)

            } else {
                // 아닐경우 모드를 활성화해줌 :: 비밀번호가 맞는지를 체크

                if (passwordPreferences.getString("password", "0000").equals(passwordFromUser)) {
                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()

                    // 변경모드 : 버튼의 색깔을 변경
                    changeButton.setBackgroundColor(Color.RED)
                } else {
                    // Error 팝업 처리
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
                .setTitle("실패!")
                .setMessage("비밀번호가 잘못되었습니다.")
                .setPositiveButton("확인") { _, _ -> }
                .create()
                .show()
    }
}