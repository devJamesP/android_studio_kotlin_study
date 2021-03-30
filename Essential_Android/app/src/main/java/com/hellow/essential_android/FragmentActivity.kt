package com.hellow.essential_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction

class FragmentActivity : AppCompatActivity(), FragmentOne.OnDataPassListener {
    override fun onDataPass(data: String?) {
        Log.d("pass", "" + data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        Log.d("LifeCycle", "call onCreate()")


        val fragment : Fragment = FragmentOne()

        // 프라그먼트에 data를 넣어주는 방법
        val bundle : Bundle = Bundle()
        bundle.putString("Hello", "hello")

        // 번들(데이터) 할당
        fragment.arguments = bundle

        val btnOpen : Button = findViewById<Button>(R.id.btnOpenFM)
        btnOpen.setOnClickListener {
            // 프라그먼트를 동적으로 작동하는 방법
            // supportFragmentManager는 Activity가 가지고있음
            val fragmentManager : FragmentManager = supportFragmentManager

            // Transaction
            // 작업의 단위 -> 시작과 끝이 있다
            // fragmentManager.beginTransaction() 시작
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()

            // 프라그먼트를 붙이는 방법 replace/add
            // view자리에 fragment로 바꾼다는 의미
            // fragmentTransaction.replace는 작업을 의미
            fragmentTransaction.replace(R.id.container, fragment)
            // 작업의 끝을 의미
            // commit -> 시간 될 때 해 (좀더 안정적)
            // commitnow -> 지금 당장 해
            fragmentTransaction.commit()
        }

        val btnClose : Button = findViewById<Button>(R.id.btnCloseFM)
        btnClose.setOnClickListener {

            // 프라그먼트 remove/detach 하는 방법
            val fragmentManager: FragmentManager = supportFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.remove(fragment)

            fragmentTransaction.commit()
        }


    }

    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle", "call onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle", "call onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifeCycle", "call onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifeCycle", "call onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifeCycle", "call onDestroy()")
    }
}