package com.yesitlab.compro.fragment.splashFragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.yesitlab.compro.R
import com.yesitlab.compro.activity.homeActivity.HomeActivity
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.databinding.FragmentSplashBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
   lateinit var binding : FragmentSplashBinding
   private lateinit var handler :Handler
   private lateinit var coroutineScope :CoroutineScope
   lateinit var commonUtils: CommonUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(LayoutInflater.from(requireActivity()),container,false)
//        var commonUtild = CommonUtild()
//        commonUtild.getAll()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        coroutineScope = CoroutineScope(Dispatchers.Main)
commonUtils = CommonUtils(requireContext())
//        handler.postDelayed({
//            // Task to be executed after 3 seconds
//            findNavController().navigate(R.id.welcomeFragment)
//
//        }, 3000)

if (commonUtils.getUserId() != -1){

 //   var intent = Intent()
    startActivity(Intent(requireActivity(),HomeActivity::class.java))

}else{
    coroutineScope.launch {
        delay(3000)
        findNavController().navigate(R.id.welcomeFragment)
        // Do your task
    }
}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        coroutineScope.cancel()
    }
}