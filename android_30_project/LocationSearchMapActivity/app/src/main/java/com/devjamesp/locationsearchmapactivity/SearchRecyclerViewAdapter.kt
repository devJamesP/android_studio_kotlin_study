package com.devjamesp.locationsearchmapactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.devjamesp.locationsearchmapactivity.databinding.ViewholderSearchResultItemBinding
import com.devjamesp.locationsearchmapactivity.model.SearchResultEntity

class SearchRecyclerViewAdapter : RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchResultItemViewHolder>() {
    private var searchResultList: List<SearchResultEntity> = emptyList()
    private lateinit var searchResultClickListener : (SearchResultEntity) -> Unit

    inner class SearchResultItemViewHolder(
        private val binding: ViewholderSearchResultItemBinding,
        val searchResultClickListener: (SearchResultEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: SearchResultEntity) = with(itemView) {
            binding.titleTextView.text = data.name
            binding.subTitleTextView.text = data.fullAddress
        }

        fun bindViews(data: SearchResultEntity) {
            binding.root.setOnClickListener {
                searchResultClickListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultItemViewHolder {
        val view = ViewholderSearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultItemViewHolder(view, searchResultClickListener)
    }

    override fun onBindViewHolder(holder: SearchResultItemViewHolder, position: Int) {
        holder.bindData(searchResultList[position])
        holder.bindViews(searchResultList[position])
    }

    override fun getItemCount(): Int = searchResultList.size

    fun setSearchResultList(searchResultList: List<SearchResultEntity>, searchResultClickListener: (SearchResultEntity) -> Unit) {
        this.searchResultList = searchResultList
        this.searchResultClickListener = searchResultClickListener

        this.notifyDataSetChanged()
    }
}