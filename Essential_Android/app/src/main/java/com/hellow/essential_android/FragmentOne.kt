package com.hellow.essential_android

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

// 프래그먼트
class FragmentOne: Fragment() {

    // 꼭 class안에 있을 필요 없다.
    interface OnDataPassListener {
        fun onDataPass(data: String?)
    }

    lateinit var dataPassListener : OnDataPassListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("LifeCycle", "F call onAttach()")

        dataPassListener = context as OnDataPassListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("LifeCycle", "F call onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 프라그먼트가 인터페이스를 처음으로 그릴 때 호출된다.
        // inflater -> 뷰를 그려주는 역할
        // container -> 부모 뷰
        Log.d("LifeCycle", "F call onCreateView()")


        return inflater.inflate(R.layout.fragment_one, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("LifeCycle", "F call onViewCreated()")

        // Activity의 OnCreate에서 했던 data 전달 작업을 여기에서 한다.
        val btnPass : Button = view.findViewById<Button>(R.id.btnPass)
        btnPass.setOnClickListener {
            dataPassListener.onDataPass("Good Bye")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 해당 데이터는 정적 프래그먼트 생성 시 null임
        val data = arguments?.getString("Hello")
        Log.d("data", "$data")

        Log.d("LifeCycle", "F call onActivityCreated()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle", "F call onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle", "F call onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifeCycle", "F call onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifeCycle", "F call onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LifeCycle", "F call onDestroyView()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("LifeCycle", "F call onDetach()")
    }

}