package com.devjamesp.tradingactivity.chatlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devjamesp.tradingactivity.DBKey
import com.devjamesp.tradingactivity.R
import com.devjamesp.tradingactivity.chatdetail.ChatRoomActivity
import com.devjamesp.tradingactivity.databinding.FragmentChatlistBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListFragment : Fragment(R.layout.fragment_chatlist) {
    private lateinit var chatListAdapter :  ChatListAdapter

    private lateinit var binding : FragmentChatlistBinding

    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }

    private val chatRoomList = mutableListOf<ChatListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentChatListBinding = FragmentChatlistBinding.bind(view)
        binding = fragmentChatListBinding

        chatListAdapter = ChatListAdapter(
            onItemClicked = { chatRoom ->
                context?.let {
                    Intent(it, ChatRoomActivity::class.java).also { intent ->
                        intent.putExtra("key", chatRoom.key)
                        startActivity(intent)
                    }
                }
            }
        )

        chatRoomList.clear()

        fragmentChatListBinding.chatListRecyclerView.adapter = chatListAdapter
        fragmentChatListBinding.chatListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (auth.currentUser == null) return

        val chatDB = Firebase.database.reference
            .child(DBKey.DB_USERS)
            .child(auth.currentUser!!.uid)
            .child(DBKey.CHILD_CHAT)

        chatDB.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val model = it.getValue(ChatListItem::class.java) ?: return
                    chatRoomList.add(model)
                }
                chatListAdapter.submitList(chatRoomList)
                chatListAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }

    override fun onResume() {
        super.onResume()
        chatListAdapter.notifyDataSetChanged()

    }
}