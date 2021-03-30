package com.hellow.essential_android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val carList = ArrayList<Car>()
        for (i in 0 until 50) {
            carList.add(Car("${i}번째 자동차", "${i}번째 엔진"))
        }

        // 리사이클러뷰 어댑터 생성
        val adapter : RecyclerViewAdapter = RecyclerViewAdapter(carList, LayoutInflater.from(this@RecyclerViewActivity))

        // 리사이클러뷰 생성
        val recycler_view : RecyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        // 어댑터 바인딩
        recycler_view.adapter = adapter

        // 리니어 레이아웃 매니저 설정
        recycler_view.layoutManager = LinearLayoutManager(this@RecyclerViewActivity)

        // 그리드 레이아웃 매니저 설정
//        recycler_view.layoutManager = GridLayoutManager(this@RecyclerViewActivity, 2)

    }
}

class RecyclerViewAdapter(
    val itemList: ArrayList<Car>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carName: TextView
        val carEngine: TextView

        init {
            carName = itemView.findViewById(R.id.car_name)
            carEngine = itemView.findViewById(R.id.car_engine)

            itemView.setOnClickListener {
                val position : Int= adapterPosition
                val engineName = itemList[position].engine
                Log.d("engine", "$engineName")

            }
        }
    }

    // 뷰를 그려주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    // 뷰 리스트의 크기 리턴
    override fun getItemCount(): Int {
        return itemList.size
    }

    // 데이터 바인딩 역할
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.carName.text = itemList[position].name
        holder.carEngine.text = itemList[position].engine
    }
}