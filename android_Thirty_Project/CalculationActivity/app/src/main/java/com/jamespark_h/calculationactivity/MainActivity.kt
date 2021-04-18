package com.jamespark_h.calculationactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.room.Room
import com.jamespark_h.calculationactivity.model.History
import java.lang.Exception
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    // 계산식 텍스트뷰
    private val expressionTextView: TextView by lazy {
        findViewById<TextView>(R.id.expressionTextView)
    }

    // 결과창
    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.resultTextView)
    }

    // 계산 기록 뷰
    private val historyLayout: View by lazy {
        findViewById<View>(R.id.histroyLayout)
    }

    private val historyLinearLayout: LinearLayout by lazy {
        findViewById<LinearLayout>(R.id.historyLinearLayout)
    }

    // 데이터베이스 변수 선언
    lateinit var db : AppDatabase

    // 연산자 중복, 여부 체크 변수
    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // db 할당
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()

    }

    // 이것을 구현 하면 XML파일에서 onClick속성을 사용해서 해당 메서드를 호출 (view를 인자로 받아야 함)
    fun buttonClicked(v: View) {

        // 숫자 버튼 및 연산자 정의
        when (v.id) {
            R.id.btnZero -> numberButtonClicked("0")
            R.id.btnOne -> numberButtonClicked("1")
            R.id.btnTwo -> numberButtonClicked("2")
            R.id.btnThree -> numberButtonClicked("3")
            R.id.btnFour -> numberButtonClicked("4")
            R.id.btnFive -> numberButtonClicked("5")
            R.id.btnSix -> numberButtonClicked("6")
            R.id.btnSeven -> numberButtonClicked("7")
            R.id.btnEight -> numberButtonClicked("8")
            R.id.btnNine -> numberButtonClicked("9")

            R.id.btnPlus -> operationButtonClicked("+")
            R.id.btnMinus -> operationButtonClicked("-")
            R.id.btnMultiply -> operationButtonClicked("*")
            R.id.btnDivide -> operationButtonClicked("/")
            R.id.btnModule -> operationButtonClicked("%")
        }
    }

    // 숫자를 클릭 시 expressionTextView에 텍스트를 출력
    private fun numberButtonClicked(number: String) {

        //구분 :: 연산자가 입력된 후 숫자가 입력된 경우
        if (isOperator) {
            expressionTextView.append(" ")
        }
        isOperator = false

        // 해당 식을 연산자와 숫자 사이에 공백을 두어 구분
        val expressionText = expressionTextView.text.split(" ")

        // 예외처리 :: 숫자와 연산자가 존재하고, 길이가 15를 넘어가는 경우
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15 자리 까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        // 예외처리 :: 숫자가 0이 가장 처음으로 나오는 경우
        else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 처음에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // UI :: 버튼 클릭 시 해당 숫자를 textView에 추가
        expressionTextView.append(number)

        // TODO resultTextView 실시간으로 계산 결과를 넣어야 하는 기능
        resultTextView.text = calculateExpression()

    }

    private fun operationButtonClicked(operator: String) {
        // 예외 처리 :: 계산식 창에 어떤한 피연산자(숫자)가 없는 경우
        if (expressionTextView.text.isEmpty()) {
            return
        }

        // 예외 처리 :: 중복, 이중 연산자 입력 처리
        when {
            // 이중 연산자 처리 :: 이전 연산자를 이후 입력된 연산자로 대체
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator
            }
            // 중복 연산자 처리 :: 중복으로 연산자 입력 불가
            hasOperator -> {
                Toast.makeText(this, "연산자는 한 번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                // 연산자 추가 :: 연산자가 입력되지 않은 경우
                expressionTextView.append(" $operator")

                isOperator = true
                hasOperator = true

            }
        }

        // 해당 연산자(가장 마지막에 있기 때문)의 색깔 지정
        val ssb = SpannableStringBuilder(expressionTextView.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            expressionTextView.text.length - 1,
            expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        expressionTextView.text = ssb

    }

    // 초기화 메서드
    fun clearButtonClicked(v: View) {
        expressionTextView.text = ""
        resultTextView.text = ""

        isOperator = false
        hasOperator = false

    }

    // 버튼 클릭 시 활성화
    fun historyButtonClicked(v: View) {
        historyLayout.isVisible = true

        // 일종의 clear
        historyLinearLayout.removeAllViews()

        Thread(Runnable {
            // TODO 디비에서 모든 기록 가져오기
            // 최신 데이타가 나중에 저장되므로 뒤집어서 꺼냄.
            db.historyDao().getAll().reversed().forEach{

                // TODO 뷰에 모든 기록 할당하기
                // UI 쓰레드
                runOnUiThread {
                    val historyView = LayoutInflater.from(this)
                        .inflate(R.layout.history_row, null)
                    historyView.findViewById<TextView>(R.id.expressionTextView).text = it.expression
                    historyView.findViewById<TextView>(R.id.resultTextView).text = "= ${it.result}"

                    historyLinearLayout.addView(historyView)
                }
            }
        }).start()

    }

    // 닫기 버튼 클릭 시 비활성화
    fun closeHistoryButtonClicked(v: View) {
        historyLayout.isVisible = false
    }

    fun historyClearButtonClicked(v: View) {
        // TODO 뷰에서 모든 기록 삭제
        historyLinearLayout.removeAllViews()

        // TODO 디비에서 모든 기록 삭제
        Thread(Runnable {
            db.historyDao().deleteAll()
        }).start()

    }


    fun resultButtonClicked(v: View) {
        val expressionTexts = expressionTextView.text.split(" ")

        // 예외처리 :: 비어있거나 오퍼랜드 1개만 있는 경우
        if (expressionTextView.text.isEmpty() || expressionTexts.size == 1) {
            return
        }

        // 예외처리 :: 오퍼랜드 1개와 연산자 1개만 있는 경우
        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(this, "아직 완성되지 않은 연산식입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // 예외처리 :: 중간 코드작성이 잘못된 경우
        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO DB저장을 위한 스트링 변수
        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()

        // TODO 디비에 넣어주는 부분
        // * 상단에 expressionText와 resultText를 미리 저장한 이유는
        // * 메인쓰레드가 먼저 실행될지 생성된 쓰레드가 먼저 생성될 지 장담 할 수 없기에
        // * 미리 저장함.
        Thread(Runnable {
            db.historyDao().insertHistory(
                History(
                    null,
                    expressionText,
                    resultText
                )
            )
        }).start()



        resultTextView.text = ""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false

    }

    // 계산 결과를 반환하는 메서드
    private fun calculateExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")

        // 예외 처리 :: 연산자가 없고 계산식이 성립되지 않은 경우
        // 예외 처리 :: 오퍼랜드가 없는 경우
        if (hasOperator.not() || expressionTexts.size != 3) {
            return " "
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return " "
        }

        // 계산 및 결과 반환
        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "*" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> "none"
        }
    }

    // 해당 함수가 없는 경우 확장 함수를 만들어서 활용(가장 마지막에 작성)
    private fun String.isNumber(): Boolean {
        return try {
            this.toBigInteger()
            true
        } catch (e: Exception) {
            false
        }
    }
}