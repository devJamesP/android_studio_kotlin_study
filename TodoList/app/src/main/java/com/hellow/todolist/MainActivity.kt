package com.hellow.todolist

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hellow.todolist.databinding.ActivityMainBinding
import com.hellow.todolist.databinding.ItemTodoBinding

class MainActivity : AppCompatActivity() {
    val RC_SIGN_IN = 1000
    private lateinit var binding: ActivityMainBinding

    //viewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 로그인 화면
        if (FirebaseAuth.getInstance().currentUser == null) {
            login()
        }

        binding.todoRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = TodoAdapter(
                emptyList<DocumentSnapshot>(),
                onClickDeleteIcon = {
                    viewModel.deleteTodo(it)
                    // ui 갱신 코드
//                    binding.todoRecyclerView.adapter?.notifyDataSetChanged()
                },
                onClickItem = {
                    viewModel.toggleTodo(it)
//                    binding.todoRecyclerView.adapter?.notifyDataSetChanged()
                }
            )
        }

        binding.button.setOnClickListener {
            // adapter에게 데이터가 변경된것을 알려줘야 함.
            val todo = Todo(binding.editText.text.toString())
            viewModel.addTodo(todo)
            binding.editText.setText("")
//            binding.todoRecyclerView.adapter?.notifyDataSetChanged()
        }

        // 관찰, 업데이트 UI
        // LiveData의 장점은 갱신하는 코드를 한쪽에서 담당해서 할 수 있음.
        // Observe 람다식에는 data가 바뀔때마다 해당 람다식이 실행됨.
        viewModel.todoLiveData.observe(this, Observer {
            (binding.todoRecyclerView.adapter as TodoAdapter).setData(it)
        })
    }

//    private fun toggleTodo(todo: Todo) {
//        todo.done = !todo.done
//        binding.todoRecyclerView.adapter?.notifyDataSetChanged()
//    }
//
//    private fun addTodo(todo: Todo) {
//        data.add(todo)
//        binding.todoRecyclerView.adapter?.notifyDataSetChanged()
//    }
//
//    private fun deleteTodo(todo: Todo) {
//        data.remove(todo)
//        binding.todoRecyclerView.adapter?.notifyDataSetChanged()
//    }

    // 로그인 완료 후에 수신
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                viewModel.fetchData()
                // ...
            } else {
                // 로그인 실패
                finish()
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    fun login() {
        // Choose authentication providers
        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // 로그아웃을 성공하고 이후에 어떻게 할 건지 작성
                login()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    fun onGroupItemClick(item: MenuItem) {
        when (item.itemId) {
            R.id.action_log_out -> {
                logout()
            }
        }
    }
}

// 할 일 객체
/*
의식의 흐름에 따라 우리가 할일을 작성하면 해당 할일은 itemView에 나와야 하므로 String타입의 어트리뷰트와
할 일을 추가한 이후에 수정되면 그것에 따라 바뀌어야 하므로 boolean타입의 어트리뷰트가 필요하다. 우선 여기까지
class 앞에 data키워드를 붙이면 클래스 내부의 자동 getter, setter가 구현되는 클래스이며 String재정의를
하지 않아도 모듈 클래스로 사용할 수 있다.
 */

data class Todo(
    val text: String,
    var done: Boolean = false
)

class TodoAdapter(
    private var todoDataSet: List<DocumentSnapshot>,
    val onClickDeleteIcon: (todo: DocumentSnapshot) -> Unit,
    val onClickItem: (todo: DocumentSnapshot) -> Unit
) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(var binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_todo, viewGroup, false)
        return TodoViewHolder(ItemTodoBinding.bind(view))
    }

    // draw recyclerview
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: TodoViewHolder, position: Int) {
        val todo = todoDataSet[position]
        viewHolder.binding.todoText.text = todoDataSet[position].getString("text")
        viewHolder.binding.deleteImageView.setOnClickListener {
            // 함수 실행 시 invoke()메서드로 실행하고 상단에서 정의한 todo인자를 넣어줌
            onClickDeleteIcon.invoke(todo)
        }

        viewHolder.binding.root.setOnClickListener {
            onClickItem.invoke(todo)
        }

        if (todo.getBoolean("done") == true) {
            viewHolder.binding.todoText.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                    setTypeface(null, Typeface.ITALIC)
            }
        } else {
            viewHolder.binding.todoText.apply {
                paintFlags = 0
//                    setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = todoDataSet.size

    // newData update 갱신 메서드
    fun setData(newData: List<DocumentSnapshot>) {
        todoDataSet = newData
        // ui 갱신
        notifyDataSetChanged()
    }
}

// viewModel을 보면
// 이대로 실행하면 자동회전 시 안드로이드 라이프사이클에 의해 생성된 목록들이 전부 사라져 있음.
// 데이터를 ViewModel이 관리하도록 작성
class MainViewModel : ViewModel() {
    // liveData는 UI 변경 시 viewModel과 함께 쓰면 좋음
    // 변경 가능한 관찰 가능한 인스턴스
    val todoLiveData = MutableLiveData<List<DocumentSnapshot>>()

    private val data = ArrayList<QueryDocumentSnapshot>()

    // Access a Cloud Firestore instance from your Activity
    val db = Firebase.firestore

    init {
        fetchData()
    }

    fun fetchData() {
        // 유저
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Read db
            db.collection(user.uid)
                .addSnapshotListener { value, e ->
                    if (e != null) {
                        Log.w("test", "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    value!!.documents?.let{
                        todoLiveData.value = value.documents
                    }
                }

//            .get()
//            // result = 쿼리 스냅샷 인자
//            .addOnSuccessListener { result ->
//                data.clear()
//                for (document in result) {
//                    val todo = Todo(
//                        document.data["text"] as String,
//                        document.data["done"] as Boolean
//                    )
//                    data.add(todo)
//                }
//            }
//                .addOnFailureListener { exception ->
//                }
            // 갱신
            todoLiveData.value = data
        }
    }

    fun toggleTodo(todo: DocumentSnapshot) {
        FirebaseAuth.getInstance().currentUser?.let{user->
            val isDone = todo.getBoolean("done") ?: false
            db.collection(user.uid).document(todo.id)
                .update("done", !isDone)

        }
    }

    fun addTodo(todo: Todo) {
        // 해당 유저의 정보가 null이 아니라면 let 이하 실행
        FirebaseAuth.getInstance().currentUser?.let {user->
            db.collection(user.uid).add(todo)
        }
    }

    fun deleteTodo(todo: DocumentSnapshot) {
        FirebaseAuth.getInstance().currentUser?.let{user->
            db.collection(user.uid).document(todo.id)
                .delete()
                .addOnFailureListener { e -> Log.w("test", "Error deleting document", e) }
        }

    }
}


// viewBinding없이 커스텀 리사이클러뷰 클래스
//class TodoAdapter(private val dataSet: ArrayList<Todo>) :
//        RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textView: TextView
//
//        init {
//            // Define click listener for the ViewHolder's View.
//            textView = itemView.findViewById(R.id.textView)
//        }
//    }
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        // Create a new view, which defines the UI of the list item
//        val view = LayoutInflater.from(viewGroup.context)
//                .inflate(R.layout.item_todo, viewGroup, false)
//
//        return ViewHolder(view)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//
//        // Get element from your dataset at this position and replace the
//        // contents of the view with that element
//        viewHolder.textView.text = dataSet[position].text
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount() = dataSet.size
//}
