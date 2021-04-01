package com.hellow.essential_android

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import java.lang.Exception

class AsyncActivity : AppCompatActivity() {
    // asyncTask 생성
    var task: BackgroundAsyncTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async)



        var progressbar = findViewById<ProgressBar>(R.id.progressbar)
        var progressText = findViewById<TextView>(R.id.asyncText)


        val btnStart: Button = findViewById<Button>(R.id.btnStart).apply {
            setOnClickListener {
                task = BackgroundAsyncTask(progressbar, progressText)
                task?.execute()
            }
        }

        val btnStop: Button = findViewById<Button>(R.id.btnStop).apply {
            setOnClickListener {
//                task?.cancel(true)
                startActivity(Intent(this@AsyncActivity, Intent2::class.java))
            }
        }
    }

    override fun onPause() {
        task?.cancel(true)
        super.onPause()
    }
}

class BackgroundAsyncTask(
    val progressbar: ProgressBar,
    val progressText: TextView
) : AsyncTask<Int, Int, Int>() {
    // params -> doInBackground에서 사용할 타입
    // progress -> onProgressUpdate에서 사용할 타입
    // result -> onPostExcute에서 사용할 타입
    var percent: Int = 0

    // 작업 전
    override fun onPreExecute() {
        percent = 0
        progressbar.progress = 0
    }

    // 쓰레드 작업
    override fun doInBackground(vararg param: Int?): Int {
        // 취소되지 않았으면 동작함
        while (!isCancelled) {
            percent++
            Log.d("async", "async : $percent")
            if (percent > 100) {
                break
            } else {
                // 중간중간 MainThread로 들어갈 때 현재 percent값을 갖고 들어감-> onProgressUpdate의 인자
                publishProgress(percent)
            }

            try {
                Thread.sleep(100)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return percent
    }

    // 작업 중간중간
    override fun onProgressUpdate(vararg values: Int?) {
        progressbar.setProgress(values[0] ?: 0)
        progressText.text = "퍼센트 : ${values[0]}"
        super.onProgressUpdate(*values)
    }

    // 작업 끝, doInBackground() return값이 인자로 들어옴
    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)
        progressText.text = "작업이 완료되었습니다."
    }

    override fun onCancelled() {
        progressbar.setProgress(0)
        progressText.text = "작업이 취소되었습니다."
    }
}