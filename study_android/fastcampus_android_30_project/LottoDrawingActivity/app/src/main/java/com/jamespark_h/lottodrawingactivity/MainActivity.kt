package com.jamespark_h.lottodrawingactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val btnAdd: Button by lazy {
        findViewById<Button>(R.id.btnAdd)
    }

    private val btnClear: Button by lazy {
        findViewById<Button>(R.id.btnClear)
    }

    private val btnRun: Button by lazy {
        findViewById<Button>(R.id.btnRun)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
                findViewById<TextView>(R.id.txNumber1),
                findViewById<TextView>(R.id.txNumber2),
                findViewById<TextView>(R.id.txNumber3),
                findViewById<TextView>(R.id.txNumber4),
                findViewById<TextView>(R.id.txNumber5),
                findViewById<TextView>(R.id.txNumber6)
        )
    }


    // 번호 갯수 체크 변수
    private var didRun = false

    // set
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 넘버 피커 범위 지정
        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        // run버튼 초기화
        initRunButton()

        // add버튼 초기화
        initAddButton()

        // clear버튼 초기화
        initClearButton()
    }

    private fun initRunButton() {
        btnRun.setOnClickListener {
            val list = getRamdomNumber()
            didRun = true

            list.forEachIndexed {index, number ->
                val textView = numberTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true

                // 백그라운드 추가
                setNumberBackground(textView)
            }

            Log.d("MainActivity", "$list")
        }
    }

    private fun initAddButton() {
        btnAdd.setOnClickListener {
            // 예외 처리
            // 로또 번호 추첨이 전부 되었을 떄
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택 할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Set에 선택한 번호가 있을 경우
            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "기존에 있는 번호입니다. 다른 번호를 선택해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 텍스트뷰에 추가
            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            // 백그라운드 추가
            setNumberBackground(textView)

            pickNumberSet.add(numberPicker.value)

        }
    }

    private fun initClearButton() {
        btnClear.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
            didRun = false
        }
    }

    private fun setNumberBackground(textView: TextView) {
        val number = Integer.parseInt(textView.text.toString())
        val backColor = listOf<Int>(
                R.drawable.circle_blue,
                R.drawable.circle_gray,
                R.drawable.circle_green,
                R.drawable.circle_red,
                R.drawable.circle_yello
        )

        textView.background = when(number) {
            in 1..9 -> ContextCompat.getDrawable(this, backColor[0])
            in 10..19 -> ContextCompat.getDrawable(this, backColor[1])
            in 20..29 -> ContextCompat.getDrawable(this, backColor[2])
            in 30..39 -> ContextCompat.getDrawable(this, backColor[3])
            else -> ContextCompat.getDrawable(this, backColor[4])
        }

    }

    // 랜덤 숫자 생성
    private fun getRamdomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
                .apply {
                    for (i in 1..45) {
                        if (pickNumberSet.contains(i)) {
                            continue
                        }
                        this.add(i)
                    }
                }

        // 번호 혼합 및 추출, 정렬
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)

        return newList.sorted()
    }
}
