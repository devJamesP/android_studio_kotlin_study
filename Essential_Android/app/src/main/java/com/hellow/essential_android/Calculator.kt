package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.reflect.Type

class Calculator : AppCompatActivity() {
    // 연산자 버튼 변수 선언
    var btnOptArray = arrayOfNulls<Button>(6)
    lateinit var btno: Button
    var optId = listOf(
        R.id.removeOpt, R.id.plusOpt, R.id.minusOpt,
        R.id.multiOpt, R.id.divOpt, R.id.resultOpt
    )

    // 숫자 버튼 변수 선언
    var btnNumArray = arrayOfNulls<Button>(10)
    var btnNumId = listOf(
        R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour,
        R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine
    )

    // 결과값 저장 변수 (텍스트 뷰)
    lateinit var result: TextView
    lateinit var calResult: TextView

    // 피연산자 변수
    var resultNum: String = "0"
    var inputNum: String = ""

    // 연산자 변수
    var calOperator: String = ""

    fun initCal() {
        // 연산자 및 숫자 버튼 변수 생성
        for (i in optId.indices) {
            btnOptArray[i] = findViewById<Button>(optId[i])
        }

        for (i in btnNumId.indices) {
            btnNumArray[i] = findViewById<Button>(btnNumId[i])
        }

        // 결과 텍스트 변수 생성
        result = findViewById<TextView>(R.id.resultText)
        calResult = findViewById<TextView>(R.id.calResult)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // 선언한 변수 생성 함수
        initCal()

        // calculation 초기변수
        var cal = Calculation(0)

        // 버튼 클릭 리스너 정의
        for (i in optId.indices) {
            btnOptArray[i]!!.setOnClickListener {

                // visibility on
                calResult.visibility = View.VISIBLE


                when ( btnOptArray[i]!!.text.toString()) {
                    "CE" -> {
                        calResult.visibility = View.INVISIBLE
                        cal.resultNum = 0
                        inputNum = "0"
                        resultNum = "0"
                        result.text = "0"
                        calResult.text = "0"
                    }
                    "+" -> {
                        calOperator = "+"
                        // 연산
                        if (inputNum.length == 0)
                            inputNum = "0"

                        resultNum = cal.calPlus(inputNum.toInt()).resultNum.toString()

                        //  UI
                        calResult.text = resultNum + " + "
                        result.text = resultNum

                        // 더하기 연산 클릭 이후 inputNum 초기화
                        inputNum = ""

                    }
                    "-" -> {

                    }
                    "*" -> {

                    }
                    "/" -> {

                    }
                    "=" -> {
                        // 연산
                        if (calOperator == "+") {
                            resultNum = cal.calPlus(inputNum.toInt()).resultNum.toString()
                        }

                        // UI
                        calResult.text = calResult.text.toString() + inputNum + " = "
                        result.text = resultNum
                    }

                    else -> {
                    }
                }
            }
        }

        // 숫자 버튼 리스너 생성
        for (i in btnNumId.indices) {
            btnNumArray[i]!!.setOnClickListener {
                // inputNum, resultNum, result

                // 숫자 입력
                inputNum = inputNum + "$i"

                // UI
                result.text = inputNum
            }
        }

    }
}

class Calculation(num: Int) {
    var resultNum = num

    fun calPlus(inputNum: Int): Calculation {
        resultNum += inputNum
        return Calculation(resultNum)
    }

    fun calMinus(inputNum: Int) {
        resultNum -= inputNum
    }

    fun calMulti(inputNum: Int) {
        resultNum *= inputNum
    }

    fun calDivid(inputNum: Int) {
        resultNum /= inputNum
    }

}
