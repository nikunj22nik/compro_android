package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.databinding.LayoutEducationBinding
import com.yesitlab.compro.model.AddEducationModel
import com.yesitlab.compro.model.AddExperienceModel
import com.yesitlab.compro.setResizableText

class EducationAdapter(var context: Context, var list : List<AddEducationModel>,var listner: OnItemClickListener): RecyclerView.Adapter<EducationAdapter.EducationViewHolder>() {
    class EducationViewHolder(val binding: LayoutEducationBinding): RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
     val binding = LayoutEducationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EducationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
       val currentItem = list[position]
        holder.binding.textEducationTitle.text = currentItem.textEducationTitle
        holder.binding.textInstituteName.text = currentItem.textInstituteName
        holder.binding.textDate.text = currentItem.textDate
        holder.binding.textDis.setResizableText(currentItem.textDis,2,true)
        holder.binding.imageEdit.setOnClickListener {
            listner.itemClick(position)
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData : List<AddEducationModel>){
        this.list = newData
        notifyDataSetChanged()
    }

}