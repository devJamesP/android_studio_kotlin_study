package com.devjamesp.bookreviewactivity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devjamesp.bookreviewactivity.databinding.ItemBookBinding
import com.devjamesp.bookreviewactivity.model.Book

class BookAdapter(private val itemClickedListener : (Book) -> Unit)
    : ListAdapter<Book, BookAdapter.BookItemViewHolder>(diffUtil) {
    inner class BookItemViewHolder(private val binding : ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookModel : Book) {
            binding.textViewItem.text = bookModel.title
            binding.descriptionTextView.text = bookModel.description
            binding.root.setOnClickListener {
                itemClickedListener(bookModel)
            }

            // 쉽게 이미지를를 로드 할 수 있는 lide앱!
            Glide
                .with(binding.coverImageView.context)
                .load(bookModel.coverSmallUrl)
                .into(binding.coverImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val view = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        // DiffUtil은 RecyclerView가 view의 position이 변경되었을 때 같은 값이 올라오면 굳이 다시 새롭게 할당 할 필요가 없기
        // 때문에 이를 판단해주는 역할이 DiffUtil
        val diffUtil = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                 return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}