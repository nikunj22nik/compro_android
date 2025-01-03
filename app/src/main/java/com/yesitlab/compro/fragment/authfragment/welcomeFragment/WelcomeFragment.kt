package com.yesitlab.compro.fragment.authfragment.welcomeFragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp


import com.yesitlab.compro.R
import com.yesitlab.compro.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(), OnClickListener {

     private lateinit var binding: FragmentWelcomeBinding
     private var bg : Int = 0
     private var bg1 : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
//       var common :CommonUtild = CommonUtild()
//        common.getAll()
        binding = FragmentWelcomeBinding.inflate(LayoutInflater.from(requireActivity()), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rlSignIn.setOnClickListener(this)
        binding.rlSignUp.setOnClickListener(this)
        backPress()



    }


    private fun backPress(){
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle back press logic here
                requireActivity().finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }

    private fun changeSignUpBackground(){
        if (bg1 == 1){
            binding.rlSignUp.setBackgroundResource(R.drawable.blue_gradiant_button_bg)
            binding.textSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            // Your code to execute after the delay
                findNavController().navigate(R.id.userTypeSelectionFragment)

        } else{
            binding.rlSignUp.setBackgroundResource(R.drawable.blue_line_bg)
            binding.textSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.blueTextColor))
        }
    }

    private fun changeSignInBackground(){
        if (bg == 1){
            binding.rlSignIn.setBackgroundResource(R.drawable.blue_gradiant_button_bg)
            binding.textSignIn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                // Your code to execute after the delay
               findNavController().navigate(R.id.loginFragment)

        } else{
            binding.rlSignIn.setBackgroundResource(R.drawable.blue_line_bg)
            binding.textSignIn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blueTextColor))
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rlSignIn -> {
                bg =1
                changeSignInBackground()
            }
            R.id.rlSignUp -> {
            bg1 = 1
                changeSignUpBackground()
            }
        }
    }

}