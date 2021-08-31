package com.devjamesp.musicstreaming.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devjamesp.musicstreaming.databinding.ItemMusicBinding
import com.devjamesp.musicstreaming.service.model.MusicModel

class PlayListAdapter(private val callback: (MusicModel) -> Unit) : ListAdapter<MusicModel, PlayListAdapter.ItemViewHolder>(diffUtil) {
    inner class ItemViewHolder(private val binding : ItemMusicBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MusicModel) {
            binding.itemTrackTextView.text = model.track
            binding.itemArtistTextView.text = model.artist
            Glide.with(binding.itemCoverImageView.context)
                .load(model.coverUrl)
                .into(binding.itemCoverImageView)


            if (model.isPlaying) {
                binding.root.setBackgroundColor(Color.GRAY)
            } else {
                binding.root.setBackgroundColor(Color.TRANSPARENT)
            }
            binding.root.setOnClickListener { callback(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        currentList[position].also { musicModel ->
            holder.bind(musicModel)
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<MusicModel>(){
            override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}