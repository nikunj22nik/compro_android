package com.yesitlab.compro.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.databinding.TransactionsItemsBinding
import com.yesitlab.compro.model.TransactionModel


class TransactionAdapter(
    var list: MutableList<TransactionModel>,
    var context: Context):RecyclerView.Adapter<TransactionAdapter.Holder>() {
    class Holder(val binding : TransactionsItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = TransactionsItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dataItem = list[position]
      holder.binding.typeOfTransaction.text = dataItem.typeOfTransaction
        holder.binding.timeGetTrans.text = dataItem.timeGetTrans
        holder.binding.amountTrans.text = dataItem.amountTrans
    }
    fun updateItem(list : MutableList<TransactionModel>){
        this.list = list
        notifyDataSetChanged()
    }
}