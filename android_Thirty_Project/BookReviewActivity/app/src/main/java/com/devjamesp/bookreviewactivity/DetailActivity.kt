package com.devjamesp.bookreviewactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.devjamesp.bookreviewactivity.databinding.ActivityDetailBinding
import com.devjamesp.bookreviewactivity.model.Book
import com.devjamesp.bookreviewactivity.model.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import java.lang.NullPointerException

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = getAppDatabase(this)

        val model = intent.getParcelableExtra<Book>("BookInfo")
        model?.let { book ->
            binding.titleTextView.text = book.title
            binding.descriptionTextView.text = book.description

            Glide
                .with(binding.coverImageView.context)
                .load(book.coverSmallUrl)
                .into(binding.coverImageView)

            binding.saveButton.setOnClickListener {
                CoroutineScope(Dispatchers.Default).launch {
                    db.reviewDao().saveReview(
                        Review(
                            book.id.toInt(),
                            binding.reviewEditText.text.toString()
                        )
                    )
                }
            }

            CoroutineScope(Dispatchers.Default).launch {
                val review: Review? = db.reviewDao().getOneReview(book.id.toInt())

                CoroutineScope(Dispatchers.Main).launch {
                    binding.reviewEditText.setText(review?.review.orEmpty())
                }
            }


        } ?: throw IllegalStateException("Not passing Parcelable Object")


    }
}