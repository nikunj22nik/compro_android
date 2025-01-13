package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.network.apiModel.HomeResponse
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.databinding.LayoutHomePageBinding
import com.yesitlab.compro.setResizableText

class HomeAdapter(var context: Context, var list : MutableList<HomeResponse>, private var listner : OnItemClickListener): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(var binding : LayoutHomePageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
       val binding = LayoutHomePageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  HomeViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = list[position]

//        if (currentItem.title != null) {
//            holder.binding.textProfession.text = currentItem.title
//        }

        if (currentItem.image != null) {
            Glide.with(context)
                .load(AppConstant.baseUrl + currentItem.image)
                .error(R.drawable.your_image)
                .placeholder(R.drawable.your_image)
                .into(holder.binding.imageProfilePicture)
        }

        if (currentItem.firstName != null  && currentItem.lastName != null) {
            holder.binding.textName.text = currentItem.firstName + " " + currentItem.lastName
        }

        if (currentItem.email != null) {
            holder.binding.textEmail.text = currentItem.email
        }

        if (currentItem.rating_count != null) {
            holder.binding.textRatingNumber.text = currentItem.rating_count.toString()
            var rate = currentItem.rating_count.toString()
            val rating = rate.toFloatOrNull() ?: 0f
            holder.binding.ratingBar.rating = rating
        }

        if (currentItem.title != null) {
            holder.binding.textDescription.text = currentItem.title
            holder.binding.textDescription.setResizableText(currentItem.title, 2, true)
        }




        // Set the RatingBar rating by converting the string rating to float

//        if (currentItem.skills[position].skill_user != null){
//            val stringArray = currentItem.skills[position].skill_user.split(",").toMutableList()
//
//// Now stringArray contains ["CSS", "HTML", "PHP", "JavaScript"]
//// You can print each element
//
//            val childAdapter = SkillPreviewAdapter( stringArray)
//            holder.binding.recyclerViewSkills.setAdapter(childAdapter)
//
//
//        }

        if (position < currentItem.skills.size) {
            val skillUser = currentItem.skills[position].skill_user
            if (skillUser != null) {
                val stringArray = skillUser.split(",").toMutableList()

                val childAdapter = SkillPreviewAdapter(stringArray)
                holder.binding.recyclerViewSkills.adapter = childAdapter
            }
        }





   holder.binding.textViewButton.setOnClickListener{
       listner.itemClick(position)
   }

    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(list: MutableList<HomeResponse>){
        this.list = list
        notifyDataSetChanged()
    }

}