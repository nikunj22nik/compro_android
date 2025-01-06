package com.yesitlab.compro.activity.homeActivity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsetsController
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.DonutProgressBar
import com.yesitlab.compro.R
import com.yesitlab.compro.activity.authActivity.AuthActivity
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var commonUtils: CommonUtils
    private lateinit var donutProgressBar: DonutProgressBar
    private lateinit var progressText: TextView
    var flowType: String? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        commonUtils = CommonUtils(this)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)
        drawerVisibility()
        header()
        bottomSheet()
        progressBar()
        flowType = intent.getStringExtra(AppConstant.homeActivity)
        flow()
       drawerFlow()

    }

    private fun drawerFlow() {
        val navController = supportFragmentManager.findFragmentById(R.id.fragmentMainContainerView)
            ?.findNavController()
        findViewById<View>(R.id.textMembership).setOnClickListener{
            navController?.navigate(R.id.currentPlanFragment)
            binding.main.close()
        }

        findViewById<View>(R.id.textContactInfo).setOnClickListener{
            navController?.navigate(R.id.professionalContactInfoFragment)
            binding.main.close()
        }
        findViewById<View>(R.id.textProfileVisibility).setOnClickListener{
            navController?.navigate(R.id.profileVisibilityFragment)
            binding.main.close()
        }
        findViewById<View>(R.id.textPasswordSecurity).setOnClickListener{
            navController?.navigate(R.id.passwordAndSecurityFragment)
            binding.main.close()
        }
        findViewById<View>(R.id.textLogOut).setOnClickListener{
            logOutDialog()
            binding.main.close()
        }
        findViewById<View>(R.id.textDeleteAccount).setOnClickListener{
            deleteDialog()
            binding.main.close()
        }
        findViewById<View>(R.id.textTransaction).setOnClickListener{
            navController?.navigate(R.id.transactionFragment)
            binding.main.close()
        }
        findViewById<View>(R.id.textMyProfile).setOnClickListener{
            if (commonUtils.getUserType() == AppConstant.Professional){
                navController?.navigate(R.id.professionalMyProfileFragment)
            }else{
                navController?.navigate(R.id.userMyProfileFragment)
            }

            binding.main.close()
        }

    }

    private fun logOutDialog() {
        val postDialog = Dialog(this)
        postDialog.setContentView(R.layout.alert_logout_dialog)
        postDialog.setCancelable(true)


        val yes: TextView = postDialog.findViewById(R.id.yes)
        val no: TextView = postDialog.findViewById(R.id.no)

        no.setOnClickListener {
            postDialog.dismiss()
        }
        yes.setOnClickListener {
            postDialog.dismiss()
            val intent = Intent(this,AuthActivity::class.java)
            intent.putExtra(AppConstant.loginFragment,AppConstant.homeActivity)
            startActivity(intent)
              finish()


        }
        postDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        postDialog.show()

    }
    private fun deleteDialog() {
        val postDialog = Dialog(this)
        postDialog.setContentView(R.layout.alert_delete_dialog)
        postDialog.setCancelable(true)


        val yes: TextView = postDialog.findViewById(R.id.yes)
        val no: TextView = postDialog.findViewById(R.id.no)

        no.setOnClickListener {
            postDialog.dismiss()
        }
        yes.setOnClickListener {
            val intent = Intent(this,AuthActivity::class.java)
            intent.putExtra(AppConstant.loginFragment,AppConstant.homeActivity)
            startActivity(intent)
              finish()
            postDialog.dismiss()

        }
        postDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        postDialog.show()

    }

    private fun flow() {
        val navController = supportFragmentManager.findFragmentById(R.id.fragmentMainContainerView)
            ?.findNavController()
        if (commonUtils.getUserType() == AppConstant.Professional && flowType == "Professional") {

            navController?.navigate(R.id.bigOpportunityFragment)
        } else if (commonUtils.getUserType() == AppConstant.User && flowType == "User") {
            navController?.navigate(R.id.homeFragment)
        } else if (commonUtils.getUserType() == AppConstant.Professional && flowType == "login") {
            navController?.navigate(R.id.homeFragment)
        } else if (commonUtils.getUserType() == AppConstant.User && flowType == "login") {
            navController?.navigate(R.id.homeFragment)
        }
    }

    private fun progressBar() {
        val percentage = 50 // Set this to the percentage you want the progress bar to display
        val headerView: View? = binding.navView.getHeaderView(0)

        headerView?.let {
            donutProgressBar = it.findViewById(R.id.donutProgressBar)
            progressText = it.findViewById(R.id.progressPercentage)
        }

        // Set the progress and update the text
        if (percentage <= 100) {
            donutProgressBar.setProgress(percentage)
            progressText.text = "$percentage%"
        }
    }

    private fun bottomSheet() {
        val llAccount: View = findViewById(R.id.llAccount)
        llAccount.setOnClickListener {
            // Open the drawer
            binding.main.open()
        }
        val llHome: View = findViewById(R.id.llHome)
        llHome.setOnClickListener {
            findNavController(R.id.fragmentMainContainerView).navigate(R.id.homeFragment)
        }
    }

    private fun header() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.header)

        // Change system icon color based on your theme
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            // For older versions, see below
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        if (binding.navView.getHeaderView(0) == null) {
            binding.navView.inflateHeaderView(R.layout.drawer_header)
        }
    }

    private fun drawerVisibility() {
        //cardProfessional
        if (commonUtils.getUserType() == AppConstant.Professional) {
            findViewById<View>(R.id.textMembership).visibility = View.VISIBLE
            findViewById<View>(R.id.textContactInfo).visibility = View.VISIBLE
            findViewById<View>(R.id.textProfileVisibility).visibility = View.VISIBLE
            findViewById<View>(R.id.viewMember).visibility = View.VISIBLE
            findViewById<View>(R.id.viewContactInfo).visibility = View.VISIBLE
            findViewById<View>(R.id.viewProfileVisibility).visibility = View.VISIBLE
            findViewById<View>(R.id.textTransaction).visibility = View.VISIBLE
            findViewById<View>(R.id.viewTransition).visibility = View.VISIBLE
        } else if (commonUtils.getUserType() == AppConstant.User) {
            findViewById<View>(R.id.viewMember).visibility = View.GONE
            findViewById<View>(R.id.viewContactInfo).visibility = View.GONE
            findViewById<View>(R.id.viewProfileVisibility).visibility = View.GONE
            findViewById<View>(R.id.textMembership).visibility = View.GONE
            findViewById<View>(R.id.textContactInfo).visibility = View.GONE
            findViewById<View>(R.id.textProfileVisibility).visibility = View.GONE
            findViewById<View>(R.id.textTransaction).visibility = View.GONE
            findViewById<View>(R.id.viewTransition).visibility = View.GONE
        }
    }

}