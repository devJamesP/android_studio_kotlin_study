package com.devjamesp.bookreviewactivity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.devjamesp.bookreviewactivity.adapter.BookAdapter
import com.devjamesp.bookreviewactivity.adapter.HistoryAdapter
import com.devjamesp.bookreviewactivity.api.BookService
import com.devjamesp.bookreviewactivity.databinding.ActivityMainBinding
import com.devjamesp.bookreviewactivity.model.BestSellerDTO
import com.devjamesp.bookreviewactivity.model.History
import com.devjamesp.bookreviewactivity.model.SearchBookDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: BookAdapter
    private lateinit var historyAdapter: HistoryAdapter

    private lateinit var bookService: BookService

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBookRecyclerView()
        initHistoryRecyclerView()
        initBookService()
        initDB()
    }

    private fun getBestSellerBooks() {
        bookService.getBestSellerBooks(getString(R.string.INTERPARK_APIKEY))
            .enqueue(object : Callback<BestSellerDTO> {
                override fun onResponse(
                    call: Call<BestSellerDTO>,
                    response: Response<BestSellerDTO>
                ) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "response not success!!")
                        return
                    }

                    response.body()?.let {
                        it.books.forEach { book ->
                        }
                        bookAdapter.submitList(it.books) // submitList는 adapter에서 currentList입니다.
                    }
                }

                override fun onFailure(call: Call<BestSellerDTO>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }
            })
    }

    private fun initBookService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)

        // 초기 베스트셀러 목록 출력
        getBestSellerBooks()

        // 엔터키 입력(다운) 시 책 키워드 검색
        initSearchEditText()
    }

    private fun search(keyWord: String) {
        bookService.getBookByName(getString(R.string.INTERPARK_APIKEY), keyWord)
            .enqueue(object : Callback<SearchBookDTO> {
                override fun onResponse(
                    call: Call<SearchBookDTO>,
                    response: Response<SearchBookDTO>
                ) {
                    hideHistoryView()
                    savedSearchKeword(keyWord)

                    if (response.isSuccessful.not()) {
                        throw Exception("response not Success!!!")
                    }

                    /** 기존의 리사이클러뷰 리스트를 제거하고 새로운 books(리스트)를 받아서 초기화한다.
                     * 리사이클러뷰의 리스트로 전달하여 리사이클러뷰를 갱신한다.
                     */
                    bookAdapter.submitList(response.body()?.books.orEmpty())
                    response.body()?.books.orEmpty().forEach {
                        Log.d(TAG, it.toString())
                    }
                }

                override fun onFailure(call: Call<SearchBookDTO>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                    hideHistoryView()
                }
            })
    }

    private fun savedSearchKeword(keyword: String) {
        Log.d(ttt, "키워드를 db에 저장")
        CoroutineScope(Dispatchers.Default).launch {
            db.historyDao().insertHistory(History(null, keyword))
            Log.d(ttt, "저장된 dbSize : ${db.historyDao().getAll().size}")
        }
    }

    private fun deleteSearchKeyword(keyWord: String) {
        Log.d(ttt, "검색 키워드를 db에서 제거 및 히스토리뷰를 보여줌")
        CoroutineScope(Dispatchers.IO).launch {
            db.historyDao().delete(keyWord)
            showHistoryView()
        }
    }

    private fun showHistoryView() {
        CoroutineScope(Dispatchers.Default).launch {
            Log.d(ttt, "모든 db의 검색어를 조회(reverse로 :: 마지막 검색어가 처음에 나와야 하므로")
            val keywords = db.historyDao().getAll().reversed()

            CoroutineScope(Dispatchers.Main).launch {
                Log.d(ttt, "히스토리 뷰를 visible로 바꾸고, 히스토리 리사이클러뷰 갱신")
                binding.historyRecyclerView.isVisible = true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }
        binding.historyRecyclerView.isVisible = true
    }

    private fun hideHistoryView() {
        Log.d(ttt, "검색 완료 후, 히스토리 리사이클러뷰 감춤")
        binding.historyRecyclerView.isVisible = false
    }

    private fun initBookRecyclerView() {
        bookAdapter = BookAdapter(itemClickedListener = { book ->
            Intent(this@MainActivity, DetailActivity::class.java).also { intent ->
                intent.putExtra("BookInfo", book)
                startActivity(intent)
            }
        })

        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = bookAdapter
    }

    private fun initHistoryRecyclerView() {
        historyAdapter = HistoryAdapter(
            searchHistoryClickedListener = { keyword ->
                search(keyword)
            },
            historyDeleteClickedListener = { keyword ->
            deleteSearchKeyword(keyword)
        })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initSearchEditText() {
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                search(binding.searchEditText.text.toString())

                // 엔터 클릭 시 editText를 포커싱하고 있는 가상 키보드를 닫음
                val i = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                i.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        binding.searchEditText.setOnTouchListener(View.OnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
                // true를 주면 action_down이 처리가 되버려서 터치가 되는 과정이 일어나지 않으며로 true를 return하지 않음
            }
            return@OnTouchListener false
        })
    }

    private fun initDB() {
        db = getAppDatabase(this)
    }


    companion object {
        private const val TAG = "MainActivity"
        private const val ttt = "Coroutine"
    }

}