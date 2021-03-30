package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Calculator2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator2)

        // 이전 입력값, 현재 입력 값
        var new = "0"
        var old = "0"

        // 결과값 변수 생성
        var result = findViewById<TextView>(R.id.result)

        // 숫자, 더하기, 초기화 변수 생성
        var one = findViewById<TextView>(R.id.one)
        var two = findViewById<TextView>(R.id.two)
        var three = findViewById<TextView>(R.id.three)
        var four = findViewById<TextView>(R.id.four)
        var five = findViewById<TextView>(R.id.five)
        var six = findViewById<TextView>(R.id.six)
        var seven = findViewById<TextView>(R.id.seven)
        var eight = findViewById<TextView>(R.id.eight)
        var nine = findViewById<TextView>(R.id.nine)
        var zero = findViewById<TextView>(R.id.zero)

        var clear = findViewById<TextView>(R.id.clear)
        var plus = findViewById<TextView>(R.id.plus)

        // 각각의 textView 변수 이벤트 리스너 작성
        one.setOnClickListener {
            // 숫자 입력
            new = new + "1"
            // UI
            result.text = new

        }
        two.setOnClickListener {
            new = new + "2"
            result.text = new
        }
        three.setOnClickListener {
            new = new + "3"
            result.text = new
        }
        four.setOnClickListener {
            new = new + "4"
            result.text = new
        }
        five.setOnClickListener {
            new = new + "5"
            result.text = new
        }
        six.setOnClickListener {
            new = new + "6"
            result.text = new
        }
        seven.setOnClickListener {
            new = new + "7"
            result.text = new
        }
        eight.setOnClickListener {
            new = new + "8"
            result.text = new
        }
        nine.setOnClickListener {
            new = new + "9"
            result.text = new
        }
        zero.setOnClickListener {
            new = new + "0"
            result.text = new
        }

        clear.setOnClickListener {
            old = "0"
            new = "0"
            result.text = "0"
        }
        plus.setOnClickListener {
            old = (old.toInt() + new.toInt()).toString()
            new = "0"
            result.text = old
        }




    }
}