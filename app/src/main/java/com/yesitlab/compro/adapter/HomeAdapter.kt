package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.databinding.LayoutHomePageBinding

import com.yesitlab.compro.model.HomeModel
import com.yesitlab.compro.setResizableText

class HomeAdapter(var context: Context, var list : MutableList<HomeModel>, private var listner : OnItemClickListener): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(var binding : LayoutHomePageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val binding = LayoutHomePageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  HomeViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.textProfession.text = currentItem.textProfession
        holder.binding.textName.text = currentItem.textName
        holder.binding.textEmail.text = currentItem.textEmail
        holder.binding.textRatingNumber.text = currentItem.textRatingNumber
        holder.binding.textDescription.text = currentItem.textDescription
        holder.binding.textDescription.setResizableText(currentItem.textDescription, 2, true)
        // Set the RatingBar rating by converting the string rating to float
        val rating = currentItem.textRatingNumber.toFloatOrNull() ?: 0f
        holder.binding.ratingBar.rating = rating

        val childAdapter = SkillPreviewAdapter(currentItem.childModel ?: mutableListOf())
        holder.binding.recyclerViewSkills.setAdapter(childAdapter)
   holder.binding.textViewButton.setOnClickListener{
       listner.itemClick(position)
   }

    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(list: MutableList<HomeModel>){
        this.list = list
        notifyDataSetChanged()
    }

}