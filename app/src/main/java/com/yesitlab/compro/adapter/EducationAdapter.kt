package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.databinding.LayoutEducationBinding
import com.yesitlab.compro.model.Education
import com.yesitlab.compro.setResizableText

class EducationAdapter(var context: Context, var list : MutableList<Education>, private var listner: OnItemClickListener): RecyclerView.Adapter<EducationAdapter.EducationViewHolder>() {
    class EducationViewHolder(val binding: LayoutEducationBinding): RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
     val binding = LayoutEducationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EducationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
       val currentItem = list[position]
        holder.binding.textEducationTitle.text = currentItem.degree
        holder.binding.textInstituteName.text = currentItem.school
        holder.binding.textDate.text ="${currentItem.start_date} - ${currentItem.end_date}"
        holder.binding.textDis.setResizableText(currentItem.description,2,true)
        holder.binding.imageEdit.setOnClickListener {
            currentItem.id.let { it1 -> listner.itemClick(it1) }
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData : MutableList<Education>){
        this.list = newData
        notifyDataSetChanged()
    }

}