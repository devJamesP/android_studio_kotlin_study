package com.devjamesp.airbnbactivity.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devjamesp.airbnbactivity.R
import com.devjamesp.airbnbactivity.model.HouseModel

class HouseViewPagerAdapter(val itemClicked: (HouseModel) -> Unit) :
    ListAdapter<HouseModel, HouseViewPagerAdapter.ItemViewHolder>(diffUtil) {
    inner class ItemViewHolder(private val itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(house: HouseModel) {
            val titleTextView = itemview.findViewById<TextView>(R.id.titleTextView)
            val priceTextView = itemview.findViewById<TextView>(R.id.priceTextView)
            val thumbnailImageView = itemview.findViewById<ImageView>(R.id.thumbnailImageView)

            titleTextView.text = house.title
            priceTextView.text = house.price
            Glide.with(thumbnailImageView)
                .load(house.imgUrl)
                .into(thumbnailImageView)

            itemview.setOnClickListener {
                itemClicked(house)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            inflater.inflate(
                R.layout.item_house_detail_for_viewpager,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<HouseModel>() {
            override fun areItemsTheSame(oldItem: HouseModel, newItem: HouseModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HouseModel, newItem: HouseModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}