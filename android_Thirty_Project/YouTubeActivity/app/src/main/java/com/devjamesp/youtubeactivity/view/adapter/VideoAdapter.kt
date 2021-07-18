package com.devjamesp.youtubeactivity.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devjamesp.youtubeactivity.databinding.MainVideoItemBinding
import com.devjamesp.youtubeactivity.model.VideoModel

class VideoAdapter(
    val callback: (String, String) -> Unit
) : ListAdapter<VideoModel, VideoAdapter.ItemViewHolder>(diffUtil) {
    inner class ItemViewHolder(private val binding: MainVideoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(videoModel: VideoModel) {
            binding.logoImageView

            Glide.with(binding.thumbnailImageView.context)
                .load(videoModel.thumb)
                .into(binding.thumbnailImageView)

            binding.titleTextView.text = videoModel.title
            binding.subtitleTextView.text = videoModel.subtitle

            binding.root.setOnClickListener {
                callback(videoModel.sources, videoModel.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(MainVideoItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<VideoModel>() {
            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}