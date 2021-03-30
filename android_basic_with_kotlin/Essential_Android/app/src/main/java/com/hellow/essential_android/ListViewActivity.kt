package com.hellow.essential_android

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        val carList = ArrayList<Car>()
        for(i in 0 until 50) {
            carList.add(Car("${i}번째 자동차", "${i}번째 엔진"))
        }

        // listView Adapter
        val adapter = ListViewAdapter(carList, this@ListViewActivity as Context)

        // listView 변수 생성
        val listView : ListView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val carName = (adapter.getItem(i) as Car).name
            val carEngine = (adapter.getItem(i) as Car).engine

            Toast.makeText(this@ListViewActivity, "$carName $carEngine", Toast.LENGTH_SHORT).show()
        }
    }
}

class ListViewAdapter(
    val carForList: ArrayList<Car>,
    val context: Context
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // 안드로이드 개발 권장 방식
        val view : View
        val holder : ViewHolder
        val layoutInflater = LayoutInflater.from(context)

        if (convertView == null) {
            Log.d("log", "1")
            view = layoutInflater.inflate(R.layout.item_view, null)
            holder = ViewHolder()

            holder.carName = view.findViewById<TextView>(R.id.car_name)
            holder.carEngine = view.findViewById<TextView>(R.id.car_engine)

            view.tag = holder
        } else {
            Log.d("log", "2")
            holder = convertView.tag as ViewHolder
            view = convertView
        }
        holder.carName?.text = carForList[position].name
        holder.carEngine?.text = carForList[position].engine

        return view
    }

    override fun getItem(p0: Int): Any {
        // 그리고자 하는 아이템 리스트의 하나(포지션에 해당하는)
        return carForList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        // 각 리스트 아이템별로 ID를 부여해주는 부분이며 ID를 p0로 함
        return p0.toLong()
    }

    override fun getCount(): Int {
        // 그리고자 하는 아이템 리스트의 전체 갯수
        return carForList.size
    }
}

// view, layoutInflater의 경우 findViewById()메소드가 리소스를 많이 먹는 메서드이다보니 개선할 필요가 있음.
// 중복이기도 하고 (아이템뷰의 갯수가 많아지면 굉장히 비효율적임)
class ViewHolder{
    var carName : TextView? = null
    var carEngine : TextView? = null
}