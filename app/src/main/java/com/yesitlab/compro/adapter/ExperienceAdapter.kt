package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.databinding.LayoutExperiencesBinding
import com.yesitlab.compro.model.AddExperienceModel
import com.yesitlab.compro.setResizableText

class ExperienceAdapter(var list : List<AddExperienceModel>,var context: Context,var listner : OnItemClickListener):RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {

    class ExperienceViewHolder(val binding : LayoutExperiencesBinding):RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val binding = LayoutExperiencesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ExperienceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.textExperienceTitle.text = currentItem.title
        holder.binding.textCompanyName.text = currentItem.company
        //holder.binding.textDate.text = currentItem.textDate
        holder.binding.textDate.setResizableText(currentItem.start_date + " "+ currentItem.end_date + " "+ currentItem.location + " "+ currentItem.country, 2, true)
        holder.binding.imageEdit.setOnClickListener{
            currentItem.id?.let { it1 -> listner.itemClick(it1) }
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData : List<AddExperienceModel>){
    //    this.list.clear()
//        data.addAll(newData)

        this.list = newData
        notifyDataSetChanged()
    }

}