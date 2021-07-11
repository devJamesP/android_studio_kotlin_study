package com.devjamesp.tradingactivity.chatdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.devjamesp.tradingactivity.DBKey
import com.devjamesp.tradingactivity.databinding.ActivityChatroomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatRoomActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChatroomBinding

    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }

    private var chatDB: DatabaseReference? = null

    private lateinit var chatItemAdapter : ChatItemAdapter
    private val chatList = mutableListOf<ChatItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatroomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatItemAdapter = ChatItemAdapter()
        binding.chatItemRecyclerView.adapter = chatItemAdapter
        binding.chatItemRecyclerView.layoutManager = LinearLayoutManager(this)

        val chatKey = intent.getLongExtra("key", -1L)

        chatDB = Firebase.database.reference
            .child(DBKey.DB_CHATS)
            .child("$chatKey")

        chatDB!!.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatItem::class.java) ?: return

                chatList.add(chatItem)
                chatItemAdapter.submitList(chatList)
                chatItemAdapter.notifyDataSetChanged()
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { }
            override fun onChildRemoved(snapshot: DataSnapshot) { }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }
            override fun onCancelled(error: DatabaseError) { }
        })

        binding.sendButton.setOnClickListener {
            val chatItem = ChatItem(
                senderId = auth.currentUser!!.uid,
                message = binding.messageEditText.text.toString()
            )
            chatDB!!.push().setValue(chatItem)
            binding.messageEditText.text.clear()
        }
    }
}