package com.devJamesP.tinderactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class LikeActivity : AppCompatActivity(), CardStackListener {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userDB: DatabaseReference

    private val adapter = CardItemAdapter()
    private val cardItems = mutableListOf<CardItem>()

    private val manager: CardStackLayoutManager by lazy {

        // 해당 클래스에서 인터페이스를 구현해서 사용하든지~~
//        CardStackLayoutManager(this@LikeActivity, carStackListener)
        CardStackLayoutManager(this@LikeActivity, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like)

        userDB = Firebase.database.reference.child(DBKey.USERS)
        val currentUserDB = userDB.child(getCurrentUserID())
        currentUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(DBKey.NAME).value == null) {
                    showNameInputPopup()
                    return
                }
                getUnSelectedUsers()
            }

            override fun onCancelled(error: DatabaseError) { }
        })
        initCardStackView()
        initSignOutButton()
        initMatchedListButton()
    }

    private fun initCardStackView() {
        val stackView = findViewById<CardStackView>(R.id.cardStackView)
        stackView.layoutManager = manager
        stackView.adapter = adapter
    }

    private fun initSignOutButton() {
        val signOutButton = findViewById<Button>(R.id.signOutButton)
        signOutButton.setOnClickListener {
            // 로그인을 해제 null로 두고, LoginActivity를 실행
            auth.signOut()
            startActivity(Intent(this@LikeActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun initMatchedListButton() {
        val matheListButton = findViewById<Button>(R.id.matchListButton)
        matheListButton.setOnClickListener {
            startActivity(Intent(this@LikeActivity, MatchedUserActivity::class.java))
        }
        // 현재 내 카드에 like or dislike한 유저 아이디를 보여줌.
    }

    private fun getUnSelectedUsers() {
        // child에서 발생하는 모든 이벤트가 여기로 전달
        userDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                /** 유저ID가 내가 아니고(즉, 다른유저) like또는 disLike를 하지 않은 경우
                 * 해당 유저ID를 불러와서 cardItems리스트에 이름과 함께 추가함.
                 */
                if (snapshot.child(DBKey.USER_ID).value != getCurrentUserID()
                    && snapshot.child(DBKey.LIKED_BY).child(DBKey.LIKE).hasChild(getCurrentUserID()).not()
                    && snapshot.child(DBKey.LIKED_BY).child(DBKey.DIS_LIKE).hasChild(getCurrentUserID()).not()
                ) {

                    val userId = snapshot.child(DBKey.USER_ID).value.toString()
                    var name = "unDecided"
                    if (snapshot.child(DBKey.NAME).value != null) {
                        name = snapshot.child(DBKey.NAME).value.toString()
                    }
                    cardItems.add(CardItem(userId, name))

                    // UI Update
                    adapter.submitList(cardItems)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                /** cardItems에 있는 유저의 name이 바뀌었을 때, 이를 감지하여
                 * 해당 ID의 name을 변경하고, UI를 업데이트 함.
                 */
                cardItems.find { it.userId == snapshot.key }?.let {
                    it.name = snapshot.child(DBKey.NAME).value.toString()
                }
                // UI Update
                adapter.submitList(cardItems)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getCurrentUserID(): String {
        if (auth.currentUser == null) {
            Toast.makeText(this, "로그인이 되어있지 않습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        return auth.currentUser?.uid.orEmpty()
    }

    private fun like() {
        Log.d("testt", "like")
        // 카드의 최상단 아이템을 의미(데이타셋은 0부터 시작이지만
        // 해당 라이브러리의 카드뷰는 1부터 시작
        val card = cardItems[manager.topPosition - 1]
        // like에 추가했으므로 카드도 데이타셋에서 삭제
        cardItems.removeFirst()
        /* 해당 카드의 userID(다른 유저의 카드ID)의 like 밑에
        내 Id와 true(키와 값)형태로 저장
         */
        userDB
            .child(card.userId)
            .child(DBKey.LIKED_BY)
            .child(DBKey.LIKE)
            .child(getCurrentUserID())
            .setValue(true)

        // 다른 유저가 나를 like or dislike 한 경우 결과를 매칭
        saveMatchIfOtherUserLikedMe(card.userId)

        Toast.makeText(this@LikeActivity, "${card.name}님을 like 하셨습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun disLike() {
        Log.d("testt", DBKey.DIS_LIKE)
        val card = cardItems[manager.topPosition - 1]
        cardItems.removeFirst()
        Log.d("testt", DBKey.DIS_LIKE)
        userDB
            .child(card.userId)
            .child(DBKey.LIKED_BY)
            .child(DBKey.DIS_LIKE)
            .child(getCurrentUserID())
            .setValue(true)

        Toast.makeText(this@LikeActivity, "${card.name}님을 disLike 하셨습니다.", Toast.LENGTH_SHORT).show()

    }

    private fun saveMatchIfOtherUserLikedMe(otherUserId : String) {
        // 나의 likedBy에 있는 (나를 like 또는 disLike한 유저의 아이디) 아이디
        val otherUserDB = userDB
            .child(getCurrentUserID())
            .child(DBKey.LIKED_BY)
            .child(DBKey.LIKE)
            .child(otherUserId)

        // 그에 해당하는 true or false or null 값
        otherUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == true) {
                    // match속성에 나를 like한 상대방 ID를 키로 값은 true를 저장
                    userDB
                        .child(getCurrentUserID())
                        .child(DBKey.LIKED_BY)
                        .child(DBKey.MATCH)
                        .child(otherUserId)
                        .setValue(true)

                    // 동일하게 상대방한테도 나의 ID를 저장?
                    userDB
                        .child(otherUserId)
                        .child(DBKey.LIKED_BY)
                        .child(DBKey.MATCH)
                        .child(getCurrentUserID())
                        .setValue(true)
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
        Log.d("testt", "swiped!")
        when (direction) {
            Direction.Right -> like()
            Direction.Left -> disLike()
            else -> { }
        }
    }

    override fun onCardRewound() {
        Log.d("testt", "onCardRewound!")
    }

    override fun onCardCanceled() {
        Log.d("testt", "onCardCanceled!")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        Log.d("testt", "onCardAppeared!")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        Log.d("testt", "onCardDisappeared!")
    }

    private fun showNameInputPopup() {
        val editText = EditText(this@LikeActivity)

        AlertDialog.Builder(this@LikeActivity)
            .setTitle(getString(R.string.wrtie_name))
            .setView(editText)
            .setPositiveButton(getString(R.string.store_text)) { _, _ ->
                if (editText.text.isEmpty()) showNameInputPopup()
                else saveUserName(editText.text.toString())
            }
            .setCancelable(false)
            .show()
    }

    private fun saveUserName(name: String) {
        val userId = getCurrentUserID()
        val currentUserDB = userDB.child(userId)
        val user = mutableMapOf<String, Any>()
        user[DBKey.USER_ID] = userId
        user[DBKey.NAME] = name
        currentUserDB.updateChildren(user)

        getUnSelectedUsers()
    }
}