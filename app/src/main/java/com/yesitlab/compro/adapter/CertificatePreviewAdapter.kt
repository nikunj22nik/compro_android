package com.yesitlab.compro.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.OnItemClickListener1
import com.yesitlab.compro.R
import com.yesitlab.compro.databinding.LayoutPreviewCertificateBinding
import com.yesitlab.compro.model.CertificatePreviewModel

class CertificatePreviewAdapter(
    var context: Context,
    var list: MutableList<CertificatePreviewModel>,
    private var listner: OnItemClickListener1,
    private var showEditIcon: Boolean
) : RecyclerView.Adapter<CertificatePreviewAdapter.CertificateViewHolder>() {

    class CertificateViewHolder(var binding: LayoutPreviewCertificateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val binding = LayoutPreviewCertificateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CertificateViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.textCertificationNameTitleName.setText(currentItem.textCertificationNameTitleName)
        holder.binding.textCertificationCompletionIDName.setText(currentItem.textCertificationCompletionIDName)
        holder.binding.textCertificationURLName.setText(currentItem.textCertificationURLName)
        holder.binding.textStartDateName.setText(currentItem.textStartDateName)
        holder.binding.textEndDateName.setText(currentItem.textEndDateName)

        holder.binding.imageCertification.setImageResource(R.drawable.certificate_image)

        if (showEditIcon){
            holder.binding.editCertification.visibility = View.VISIBLE
        }else{
            holder.binding.editCertification.visibility = View.GONE
        }

        holder.binding.editCertification.setOnClickListener {
            listner.itemClick(position, "certificate")
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(list: MutableList<CertificatePreviewModel>) {
        this.list = list
        notifyDataSetChanged()
    }


}