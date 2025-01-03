package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.databinding.LayoutPortfolioBinding
import com.yesitlab.compro.model.PortfolioModel

class PortfolioAdapter(
    var context: Context,
    var list: List<PortfolioModel>,
    private var listner: OnItemClickListener,
    private val showEditIcon: Boolean
): RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder>() {

    class PortfolioViewHolder(var binding: LayoutPortfolioBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val binding= LayoutPortfolioBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return PortfolioViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val currentItem = list[position]
        Glide
            .with(context)
            .load(currentItem.imagePortfolio)
            .centerCrop()
            .placeholder(R.drawable.ic_img_not_found)
            .into(holder.binding.imagePortfolio)

        holder.binding.text.text = currentItem.text

        if (showEditIcon){
            holder.binding.imageEditDelete.visibility = View.VISIBLE
        }else{
            holder.binding.imageEditDelete.visibility = View.GONE
        }

        holder.binding.imageEditDelete.setOnClickListener {
            listner.itemClick(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(newData : List<PortfolioModel>){
        list = newData
        notifyDataSetChanged()
    }


}