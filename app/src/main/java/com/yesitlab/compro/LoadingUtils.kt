package com.yesitlab.compro

import android.app.AlertDialog
import android.content.Context
import taimoor.sultani.sweetalert2.Sweetalert

open class LoadingUtils {

    companion object {
        private var jarvisLoader: JarvisLoader? = null
        fun showDialog(context: Context?,
            isCancelable: Boolean
        ) {
            hideDialog()
            if (context != null) {
                try {
                    jarvisLoader = JarvisLoader(context)
                    jarvisLoader?.let { jarvisLoader->
                        jarvisLoader.setCanceledOnTouchOutside(true)
                        jarvisLoader.setCancelable(isCancelable)
                        jarvisLoader.show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun hideDialog() {
            if (jarvisLoader!=null && jarvisLoader?.isShowing!!) {
                jarvisLoader = try {
                    jarvisLoader?.dismiss()
                    null
                } catch (e: Exception) {
                    null
                }
            }
        }

        fun showErrorDialog(context: Context?,
                       text:String
        ){
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Error")
                .setMessage(text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }

            val dialog = builder.create()
            dialog.show()

        }

    }

}