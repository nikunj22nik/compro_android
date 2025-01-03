package com.yesitlab.compro.activity.authActivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.R
import com.yesitlab.compro.base.AppConstant
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        callStartWork()



    }
    fun callStartWork() {

        val navController =
            supportFragmentManager.findFragmentById(R.id.fragmentAuthContainerView)
                ?.findNavController()
        if (intent != null && intent.hasExtra(AppConstant.loginFragment)) {
//            val textType = intent.getStringExtra(AppConstant.loginFragment)
//
//            if (textType == AppConstant.homeActivity) {
//                navController?.navigate(R.id.loginFragment)
//            } else {
//                navController?.navigate(R.id.splashFragment)
//            }

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.fragmentAuthContainerView) as NavHostFragment
            val navController = navHostFragment.navController

            // Load the navigation graph
            val navGraph = navController.navInflater.inflate(R.navigation.auth_nav)

            // Programmatically set a new start destination
            val textType = intent.getStringExtra(AppConstant.loginFragment)

            if (textType.equals(AppConstant.homeActivity)) {
                navGraph.setStartDestination(R.id.loginFragment)
                //  navController?.navigate(R.id.loginFragment)
            }

            // Set the modified graph to the NavController
            navController.graph = navGraph
        }
//        } else {
//            val navHostFragment = supportFragmentManager
//                .findFragmentById(R.id.fragmentAuthContainerView) as NavHostFragment
//            val navController = navHostFragment.navController
//            val navGraph = navController.navInflater.inflate(R.navigation.auth_nav)
//            navGraph.setStartDestination(R.id.splashFragment)
//
//        }
    }

    override fun onResume() {
        super.onResume()

    }


}