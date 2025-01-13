package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yesitlab.compro.databinding.ItemImageBinding

class ImageAdapter(
    private val onImageDelete: (Uri) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val images = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri = images[position]
        Glide.with(holder.itemView.context)
            .load(imageUri)
            .into(holder.binding.imageView)


        holder.binding.imageCancel.setOnClickListener {
            onImageDelete(imageUri)
            images.remove(imageUri)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = images.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateImages(newImages: List<Uri>) {
        images.clear()
        images.addAll(newImages)
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}
