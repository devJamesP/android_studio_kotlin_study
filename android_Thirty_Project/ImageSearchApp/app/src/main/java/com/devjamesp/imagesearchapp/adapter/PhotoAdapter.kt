package com.devjamesp.imagesearchapp.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.devjamesp.imagesearchapp.R
import com.devjamesp.imagesearchapp.data.models.PhotoModel
import com.devjamesp.imagesearchapp.databinding.ItemPhotoBinding

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ItemViewHolder>() {
    private var photos: List<PhotoModel> = emptyList()
    private var onClickPhoto : (PhotoModel) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
         ItemViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(photos[position])
        holder.bindViews(photos[position])
    }
    override fun getItemCount(): Int = photos.size


    inner class ItemViewHolder(private val binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindViews(photo: PhotoModel) {
            binding.root.setOnClickListener { onClickPhoto(photo) }
        }

        fun bindData(photo: PhotoModel) {
            // Image width, height setup
            val (targetWidth, targetHeight) = setupImageWidthAndHeight(photo)

            // PhotoItem setup
            Glide.with(binding.root)
                .load(photo.urls?.regular)
                .thumbnail(
                    Glide.with(binding.root)
                        .load(photo.urls?.thumb)
                        .transition(DrawableTransitionOptions.withCrossFade())
                )
                .override(targetWidth, targetHeight)
                .into(binding.photoImageView)

            Glide.with(binding.root)
                .load(photo.user?.profileImage?.small)
                .placeholder(R.drawable.shape_profile_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(binding.profileImageView)

            if (photo.user?.name.isNullOrBlank()) {
                binding.authorTextView.isVisible = false
            } else {
                binding.authorTextView.isVisible = true
                binding.authorTextView.text = photo.user?.name
            }

            if (photo.description.isNullOrBlank()) {
                binding.descriptionTextView.isVisible = false
            } else {
                binding.descriptionTextView.isVisible = true
                binding.descriptionTextView.text = photo.description
            }
        }

        private fun setupImageWidthAndHeight(photo: PhotoModel): Pair<Int, Int> {
            val dimensionRatio = photo.height / photo.width.toFloat()
            val targetWidth = binding.root.resources.displayMetrics.widthPixels -
                    (binding.root.paddingStart + binding.root.paddingEnd)
            val targetHeight = (targetWidth * dimensionRatio).toInt()

            binding.contentContainerCardView.layoutParams.apply {
                height = targetHeight
                width = targetWidth
            }

            return Pair(targetWidth, targetHeight)
        }


    }

    fun setPhotoList(photoList: List<PhotoModel>) {
        photos = photoList
    }

    fun setOnClickPhoto(body: (PhotoModel) -> Unit) {
        onClickPhoto = body
    }
}