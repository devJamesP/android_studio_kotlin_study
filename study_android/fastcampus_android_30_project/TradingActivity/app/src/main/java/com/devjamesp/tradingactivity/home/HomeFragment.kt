package com.devjamesp.tradingactivity.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.devjamesp.tradingactivity.DBKey
import com.devjamesp.tradingactivity.R
import com.devjamesp.tradingactivity.chatlist.ChatListItem
import com.devjamesp.tradingactivity.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(R.layout.fragment_home) {
    // RealTime DB
    private lateinit var articleDB: DatabaseReference
    private lateinit var userDB: DatabaseReference

    // ListAdapter
    private lateinit var articleAdapter: ArticleAdapter

    // ArticleDataSet
    private val articleList = mutableListOf<ArticleModel>()

    // DB-Child listener
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            // 데이터 클래스 통째로 넘길 수 있음.
            val articleModel = snapshot.getValue(ArticleModel::class.java) ?: return

            // Data리스트에 추가 및 UI Update :: ViewModel 적용
            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "값을 읽어오는데 실패했습니다.", error.toException())
        }
    }

    // binding obj
    private var binding: FragmentHomeBinding? = null

    // current user auth
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        articleList.clear()
        // DB 생성
        articleDB = Firebase.database.reference.child(DBKey.DB_ARTICLES)
        articleDB.addChildEventListener(listener)

        userDB = Firebase.database.reference.child(DBKey.DB_USERS)

        articleAdapter = ArticleAdapter(
            onItemClicked = { articleModel ->
                val user = auth.currentUser
                if (user != null) {
                    // 로그인 한 상태
                    if (user.uid != articleModel.sellerId) {
                        val chatRoom = ChatListItem(
                            buyerId = user.uid,
                            sellerId = articleModel.sellerId,
                            itemTitle = articleModel.title,
                            key = System.currentTimeMillis()
                        )

                        userDB.child(user.uid)
                            .child(DBKey.CHILD_CHAT)
                            .push()
                            .setValue(chatRoom)

                        userDB.child(articleModel.sellerId)
                            .child(DBKey.CHILD_CHAT)
                            .push()
                            .setValue(chatRoom)

                        Snackbar.make(view, "채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.", Snackbar.LENGTH_LONG).show()

                    } else {
                        Snackbar.make(view, "내가 올린 아이템 입니다.", Snackbar.LENGTH_LONG).show()
                    }
                } else {
                    Snackbar.make(view, "로그인 후 사용해주세요.", Snackbar.LENGTH_LONG).show()
                }
            }
        )

        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter

        // Floating Button
        fragmentHomeBinding.addFloatingButton.setOnClickListener {
            if (auth.currentUser != null) {
            Intent(requireContext(), AddArticleActivity::class.java).also { intent ->
                startActivity(intent)
            }
            } else {
                Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        articleDB.removeEventListener(listener)
    }

    companion object {
        private const val TAG = "HomeFragment"
    }

}