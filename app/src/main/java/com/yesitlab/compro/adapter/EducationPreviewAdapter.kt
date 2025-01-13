package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.network.apiModel.Education
import com.yesitlab.compro.OnItemClickListener1
import com.yesitlab.compro.databinding.ItemImageBinding
import com.yesitlab.compro.databinding.LayoutEducationPreviewBinding
import com.yesitlab.compro.model.EducationPreviewModel

class EducationPreviewAdapter(
    var context: Context,
    var list: MutableList<Education>,
    private var listner: OnItemClickListener1,
    var showEditIcon: Boolean
) : RecyclerView.Adapter<EducationPreviewAdapter.EducationViewHolder>() {

    class EducationViewHolder(var binding: LayoutEducationPreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val binding = LayoutEducationPreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EducationViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
      val currentItem = list[position]



        holder.binding.textSchoolUniversityName.setText(currentItem.sch_uni)
        holder.binding.textDegreeName.setText(currentItem.degree)
        holder.binding.textFieldStudyName.setText(currentItem.fieldstudy)
      //  holder.binding.textCountryName.setText(currentItem.textCountryName)
        holder.binding.textStartDateValue.setText(currentItem.start_date)
        holder.binding.textEndDateValue.setText(currentItem.end_date)

        if (showEditIcon){
            holder.binding.imageEducationEdit.visibility = View.VISIBLE
        }else{
            holder.binding.imageEducationEdit.visibility = View.GONE
        }

        holder.binding.imageEducationEdit.setOnClickListener {
            listner.itemClick(position,"education")
        }
    }

    fun updateItem(list: MutableList<Education>) {
        this.list = list
        notifyDataSetChanged()
    }

}