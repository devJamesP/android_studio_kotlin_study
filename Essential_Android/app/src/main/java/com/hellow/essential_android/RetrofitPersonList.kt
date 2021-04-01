package com.hellow.essential_android

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPersonList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_person_list)

        // 리사이클러뷰 변수 생성
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.retrofitRecyclerView)
        RetrofitTask(
            recyclerView,
            LayoutInflater.from(this@RetrofitPersonList)
        ).retrofitService()
    }
}

class RetrofitTask(
    val recyclerView: RecyclerView,
    val inflater: LayoutInflater
) {
    fun retrofitService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 레트로핏 서비스 생성
        val service = retrofit.create(RetrofitPersonListService::class.java)

        // GET 요청
        service.getStudentsList().enqueue(object : Callback<Array<PersonFromServer>> {
            override fun onFailure(call: Call<Array<PersonFromServer>>, t: Throwable) {
                Log.d("retfPersonList", "통신 오류")
            }

            override fun onResponse(
                call: Call<Array<PersonFromServer>>,
                response: Response<Array<PersonFromServer>>
            ) {
                if (response.isSuccessful) {
                    // 데이터 받아옴
                    val personList: Array<PersonFromServer>? = response.body()

                    // 어댑터 설정
                    val adapter = RetrofitRecyclerViewAdapter(
                        personList!!,
                        inflater
                    )
                    recyclerView.adapter = adapter
                }
            }
        })

    }
}

class RetrofitRecyclerViewAdapter(
    val retfPersonList: Array<PersonFromServer>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<RetrofitRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personId: TextView
        val personName: TextView
        val personAge: TextView
        val personIntro: TextView

        init {
            personId = itemView.findViewById<TextView>(R.id.retfpersonId)
            personName = itemView.findViewById<TextView>(R.id.retfpersonName)
            personAge = itemView.findViewById<TextView>(R.id.retfpersonAge)
            personIntro = itemView.findViewById<TextView>(R.id.retfpersonIntro)

            // 아이템 선택 이벤트 리스너
            itemView.setOnClickListener {
                val position: Int = adapterPosition
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.retrofitperson_itemview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return retfPersonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.personId.text = retfPersonList[position]?.id.toString() ?: ""
        holder.personName.text = retfPersonList[position]?.name ?: ""
        holder.personAge.text = retfPersonList[position]?.age.toString() ?: ""
        holder.personIntro.text = retfPersonList[position]?.intro ?: ""
    }
}