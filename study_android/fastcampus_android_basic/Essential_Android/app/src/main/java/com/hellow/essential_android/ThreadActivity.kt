package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class ThreadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)


        val runnable = object : Runnable {
            override fun run() {
                Log.d("thread-1", "Thread is made")
            }
        }

        // 쓰레드 생성
        val thread: Thread = Thread(runnable)

        val btnThread: Button = findViewById<Button>(R.id.btnThread).apply {
            setOnClickListener {
                Thread.sleep(3000)
                setBackgroundColor(getColor(R.color.textView_Color))
                thread.start()
            }
        }


        // 웬만하면 서브 쓰레드를 생성해서 작업하는 경우는 거의 없다.
        // 관리하기도 힘들기 때문이다. 또한 View쪽을 다루는 것은 MainThread만 작업 할 수 있으므로
        // runOnUiThread() 메서드를 사용하면 서브 스레드를 동작하다가 View쪽은 MainThread에서 작업이 이루어진다.
        Thread(Runnable {
            Thread.sleep(2000)
            Log.d("thread-1", "Thread is made")
            //
            runOnUiThread {
                btnThread.setBackgroundColor(getColor(R.color.textView_Color))
            }
        }).start()



    }
}