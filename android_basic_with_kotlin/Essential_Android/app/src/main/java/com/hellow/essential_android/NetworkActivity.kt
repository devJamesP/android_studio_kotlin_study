package com.hellow.essential_android

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class NetworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        // 리사이클러뷰 변수 생성
        val personRecyclerView : RecyclerView = findViewById<RecyclerView>(R.id.personRecyclerView)

        // networkTast 실행
        NetworkTask(
            personRecyclerView,
            LayoutInflater.from(this@NetworkActivity)).execute()
    }
}

class NetworkTask(
    val recyclerView : RecyclerView,
    val inflater: LayoutInflater
) : AsyncTask<Any?, Any?, Array<PersonFromServer>?>() {
    override fun onPostExecute(result: Array<PersonFromServer>?) {

        // 리사이클러뷰 어댑터 생성
        val adapter = PersonRecyclerViewAdapter(result, inflater)

        // 리사이클러뷰 어댑터 설정
        recyclerView.adapter = adapter

        // 리니어 레이아웃 매니저 설정은 XML에서
        super.onPostExecute(result)
    }
    override fun doInBackground(vararg p0: Any?): Array<PersonFromServer>? {
        val urlString: String = "http://mellowcode.org/json/students/"
        val url: URL = URL(urlString)

        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json")

        var buffer = ""
        if (connection.responseCode == HttpsURLConnection.HTTP_OK) {
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )
            buffer = reader.readLine()
        }

        // Json Parsing support
        // Json데이터를 읽으면 Array형태로 받는데 gson은 객체로 받기때문에 이를 Array에 담아주면됨.
        val data = Gson().fromJson(buffer, Array<PersonFromServer>::class.java)
        return data
    }
}

class PersonRecyclerViewAdapter(
    val personList: Array<PersonFromServer>?,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personId: TextView
        val personName: TextView
        val personAge: TextView
        val personIntro: TextView

        init {
            personId = itemView.findViewById(R.id.personId)
            personName = itemView.findViewById(R.id.personName)
            personAge = itemView.findViewById(R.id.personAge)
            personIntro = itemView.findViewById(R.id.personIntro)

            // 아이템 선택 이벤트 리스너
            itemView.setOnClickListener {
                val position : Int= adapterPosition
            }
        }
    }

    // 뷰를 그려주는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.person_itemview, parent, false)
        return ViewHolder(view)
    }

    // 뷰 리스트의 크기 리턴
    override fun getItemCount(): Int {
        return personList?.size ?: -1
    }

    // 데이터 바인딩 역할
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.personId.text = personList!![position].id.toString() ?: ""
        holder.personName.text = personList!![position].name ?: ""
        holder.personAge.text = personList!![position].age.toString() ?: ""
        holder.personIntro.text = personList!![position].intro ?: ""
    }

}