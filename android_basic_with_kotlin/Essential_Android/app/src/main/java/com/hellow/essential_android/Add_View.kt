package com.hellow.essential_android

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import java.util.zip.Inflater

class Add_View : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__view)
        val carList = ArrayList<Car>()

        for(i in 0 until 10) {
            carList.add(Car("${i}번째 자동차", "${i}번째 엔진"))
        }

        // 컨테이너와 인플레이터 생성 addViewContainer
        val container : LinearLayout = findViewById<LinearLayout>(R.id.addviewContainer)
        val inflater: LayoutInflater = this@Add_View.layoutInflater

        for(i in 0 until 10) {
            val itemView = inflater.inflate(R.layout.item_view, container, false)
            val carName = itemView.findViewById<TextView>(R.id.car_name)
            val carEngine = itemView.findViewById<TextView>(R.id.car_engine)

            carName.text = carList[i].name
            carEngine.text = carList[i].engine

            container.addView(itemView)
        }
    }
}

class Car(val name: String, val engine: String) {

}