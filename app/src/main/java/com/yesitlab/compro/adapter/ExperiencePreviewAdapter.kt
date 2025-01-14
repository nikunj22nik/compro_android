package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.OnItemClickListener1
import com.yesitlab.compro.databinding.LayoutProfessionalExperienceBinding
import com.yesitlab.compro.model.AddExperienceModel
import com.yesitlab.compro.model.ExperiencePreviewModel

class ExperiencePreviewAdapter(
    var context: Context,
    var list: List<AddExperienceModel>,
    var listner: OnItemClickListener1,
    var showEditIcon: Boolean
) : RecyclerView.Adapter<ExperiencePreviewAdapter.ExperienceViewHolder>() {

    class ExperienceViewHolder(var binding: LayoutProfessionalExperienceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val binding = LayoutProfessionalExperienceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExperienceViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val currentItem = list[position]

        holder.binding.textCompanyName.text = currentItem.company
        holder.binding.textLocationName.text = currentItem.location
        holder.binding.textJobTitleValue.text = currentItem.title
        holder.binding.textStartDateValue.text = currentItem.start_date
        holder.binding.textEndDateValue.text = currentItem.end_date
        holder.binding.textCountryValue.text = currentItem.country
        if (showEditIcon) {
            holder.binding.imageExperienceEdit.visibility = View.VISIBLE
        } else {
            holder.binding.imageExperienceEdit.visibility = View.GONE
        }

        holder.binding.imageExperienceEdit.setOnClickListener {
            listner.itemClick(position, "experience")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(newData : List<AddExperienceModel>){
        this.list = newData
        notifyDataSetChanged()
    }

}