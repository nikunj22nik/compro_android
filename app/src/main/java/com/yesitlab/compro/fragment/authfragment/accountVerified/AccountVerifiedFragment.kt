package com.yesitlab.compro.fragment.authfragment.accountVerified

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yesitlab.compro.R
import com.yesitlab.compro.activity.homeActivity.HomeActivity
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.databinding.FragmentAccountVerifiedBinding


class AccountVerifiedFragment : Fragment() {
 private lateinit var  binding : FragmentAccountVerifiedBinding
 private  lateinit var  commonUtils: CommonUtils
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
        binding = FragmentAccountVerifiedBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commonUtils = CommonUtils(requireActivity())
        Toast.makeText(requireContext(),commonUtils.getUserType(),Toast.LENGTH_LONG).show()
       // Handler(Looper.getMainLooper()).postDelayed({
        if (commonUtils.getUserType() == AppConstant.Professional){

                val intent = Intent(requireActivity(),HomeActivity::class.java)
                intent.putExtra(AppConstant.homeActivity, "Professional")
                startActivity(intent)
                requireActivity().finish()

        }
        else if (commonUtils.getUserType() == AppConstant.User){

                val intent = Intent(requireActivity(),HomeActivity::class.java)
                intent.putExtra(AppConstant.homeActivity, "User")
                startActivity(intent)
                requireActivity().finish()

        }
      //  }, 1000)
    }
}