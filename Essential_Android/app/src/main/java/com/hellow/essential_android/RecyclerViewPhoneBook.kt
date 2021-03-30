package com.hellow.essential_android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPhoneBook : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_phone_book)

        // 전화번호 50명의 리스트 생성
        val phoneNumberList = createRecyclerPhoneBook(50)

        // 리사이클러뷰 어댑터 생성
        val adapter = RecyclerPhoneBookAdapter(phoneNumberList, LayoutInflater.from(this@RecyclerViewPhoneBook), this)

        // 리사이클러뷰 컨테이너 생성
        val recyclerContainer : RecyclerView = findViewById<RecyclerView>(R.id.recyclerContainerView)
        recyclerContainer.adapter = adapter

        // 리니어 레이아웃 매니저 설정
        recyclerContainer.layoutManager = LinearLayoutManager(this@RecyclerViewPhoneBook)
    }
    // 생성할 사람의 수를 입력하면 해당 수만큼 RecyclerPerson 인스턴스를 List에 추가, List를 반환
    fun createRecyclerPhoneBook(personSize: Int = 40,
                                phoneBook: RecyclerPhoneBook = RecyclerPhoneBook()) : RecyclerPhoneBook{
        for (i in 0 until personSize) {
            phoneBook.addPerson(
                RecyclerPerson("${i}번째 사람", "010-1234-567,${i}")
            )
        }
        return phoneBook
    }
}

class RecyclerPhoneBookAdapter(
    val itemList : RecyclerPhoneBook,
    val inflater : LayoutInflater,
    val activity : Activity
) : RecyclerView.Adapter<RecyclerPhoneBookAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personName : TextView
        val personNumber: TextView

        init{
            personName = itemView.findViewById<TextView>(R.id.txHeadName)
            personNumber = itemView.findViewById<TextView>(R.id.txPhoneNumber)

            // 아이템 뷰 클릭 시 상세페이지 액티비티 실행
            itemView.setOnClickListener {
                // 인텐트 생성
                val intent = Intent(itemView.context, DetailPhoneBook::class.java)

                intent.putExtra("Name", itemList.personList[adapterPosition].name)
                intent.putExtra("Number", itemList.personList[adapterPosition].number)

                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.phonebook_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.personList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.personName.text = itemList.personList[position].name
        holder.personNumber.text = itemList.personList[position].number
    }
}

// recyclerPerson인스턴스 List를 가진 RecyclerPhoneBook 클래스 선언
class RecyclerPhoneBook() {
    val personList = ArrayList<RecyclerPerson>()
    fun addPerson(person: RecyclerPerson) {
        personList.add(person)
    }
}

// 이름과 전화번호를 가진 RecyclerPerson 클래스 선언
class RecyclerPerson(val name: String, val number: String) {
}
