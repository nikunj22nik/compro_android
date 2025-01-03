package com.yesitlab.compro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.databinding.LayoutSkillBinding

class SkillPreviewAdapter(var list : MutableList<String>): RecyclerView.Adapter<SkillPreviewAdapter.SkillViewHolder>() {
    class SkillViewHolder(var binding: LayoutSkillBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val binding  = LayoutSkillBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SkillViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.binding.text.setText(list.get(position))

    }



}