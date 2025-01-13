package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.OnItemClickListener1
import com.yesitlab.compro.databinding.AgencyItemsBinding
import com.yesitlab.compro.model.AgencyModel

class AddAgencyAdapter(var context: Context, private val tasks: MutableList<AgencyModel>, var listner : OnItemClickListener1 ): RecyclerView.Adapter<AddAgencyAdapter.AddAgencyViewHolder>() {




    inner  class  AddAgencyViewHolder(var binding : AgencyItemsBinding): RecyclerView.ViewHolder(binding.root) {


        init {
            binding.etWriteProfession.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    tasks[adapterPosition].task = s.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            })
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAgencyViewHolder {
      val binding = AgencyItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  AddAgencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return  tasks.size
    }

    override fun onBindViewHolder(holder: AddAgencyViewHolder, position: Int) {

        val item = tasks[position]
        holder.binding.etWriteProfession.setText(item.task)
        holder.binding.etWriteProfession.hint = "Write Your Profession"

        holder.binding.imageNextButton.setOnClickListener {
            listner.itemClick(position,"nextPage")
        }


        holder.binding.imageDelete.setOnClickListener {
            removeItem(position)
        }

    }



    private fun removeItem(position: Int) {
        if (position >= 0 && position < tasks.size) {
            tasks.removeAt(position)            // Remove item from list
            notifyItemRemoved(position)        // Notify adapter about item removal
            notifyItemRangeChanged(position, tasks.size) // Optional: refresh range
        }
    }

}