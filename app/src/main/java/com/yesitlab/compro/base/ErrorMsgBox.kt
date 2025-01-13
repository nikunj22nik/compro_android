package com.yesitlab.compro.base

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.TextView
import com.yesitlab.compro.Click
import com.yesitlab.compro.R

class ErrorMsgBox {
    companion object{
        fun  alertError(context: Context?, msg:String?){
            val dialog= context?.let { Dialog(it, R.style.BottomSheetDialog) }
            dialog?.setCancelable(false)
            dialog?.setContentView(R.layout.alert_dialog)
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog?.window!!.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            dialog.window!!.attributes = layoutParams
            val tvTitle: TextView =dialog.findViewById(R.id.tv_title)
            val btnOk: TextView  =dialog.findViewById(R.id.btn_ok)
            tvTitle.text=msg
            btnOk.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }

        fun  alertDeleteEdit(context: Context?, msg:String?,listner: Click){
            val dialog= context?.let { Dialog(it, R.style.BottomSheetDialog) }
            dialog?.setCancelable(false)
            dialog?.setContentView(R.layout.alert_delete_edit)
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog?.window!!.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            dialog.window!!.attributes = layoutParams
            val tvTitle: TextView =dialog.findViewById(R.id.titleEdit)
            val confirm: TextView  =dialog.findViewById(R.id.confirm)
            val cancel: TextView  =dialog.findViewById(R.id.cancel)
            tvTitle.text=msg
            confirm.setOnClickListener {
                listner.confirm()

                dialog.dismiss()
            }
            cancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }


    }
}