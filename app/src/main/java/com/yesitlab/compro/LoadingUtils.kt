package com.yesitlab.compro

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.yesitlab.compro.base.CommonUtils
import taimoor.sultani.sweetalert2.Sweetalert

open class LoadingUtils {

    companion object {
        private var jarvisLoader: JarvisLoader? = null

        /*fun showDialog(context: Context?,
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

         */
        fun showDialog(context: Context?, isCancelable: Boolean) {
            hideDialog() // Ensure the previous dialog is hidden
            if (context != null) {
                try {
                    jarvisLoader = JarvisLoader(context)
                    jarvisLoader?.let { loader ->
                        loader.setCanceledOnTouchOutside(true)
                        loader.setCancelable(isCancelable)
                        loader.show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


        /*
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

         */

        fun hideDialog() {
            try {
                if (jarvisLoader != null && jarvisLoader!!.isShowing) {
                    jarvisLoader?.dismiss()
                    jarvisLoader = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                jarvisLoader = null
            }
        }


        /*
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


         */

        fun showErrorDialog(context: Context?, text: String) {
            if (context == null) return

            // Inflate the custom layout
            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.dialog_error, null)

            // Find views
            val errorMessage = dialogView.findViewById<TextView>(R.id.errorMessage)
            val errorIcon = dialogView.findViewById<ImageView>(R.id.errorIcon)
            val okButton = dialogView.findViewById<Button>(R.id.okButton)

            // Set the error message
            errorMessage.text = text

            // Create the dialog
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            // Add animation to the error icon
            val animation = AnimationUtils.loadAnimation(context, R.anim.shake)
            errorIcon.startAnimation(animation)

            // Set button click listener
            okButton.setOnClickListener {
                dialog.dismiss()
            }

            // Show the dialog
            dialog.show()
        }





        @SuppressLint("MissingInflatedId")
        fun showSuccessDialog(context: Context?, text: String) {
            if (context == null) return

            // Inflate the custom layout
            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.dialog_success, null)

            // Find views
            val successMessage = dialogView.findViewById<TextView>(R.id.successMessage)
            val successIcon = dialogView.findViewById<ImageView>(R.id.successIcon)
            val okButton1 = dialogView.findViewById<Button>(R.id.okButton1)

            // Set the error message
            successMessage.text = text

            // Create the dialog
            val dialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .create()

            // Add animation to the error icon
            val animation = AnimationUtils.loadAnimation(context, R.anim.shake)
            successIcon.startAnimation(animation)

            // Set button click listener
            okButton1.setOnClickListener {
                dialog.dismiss()
            }

            // Show the dialog
            dialog.show()
        }



        fun getAuthToken(context:Context) :  String{
            var commonUtils = CommonUtils(context)
        return    commonUtils.getToken()
        }
    }

}