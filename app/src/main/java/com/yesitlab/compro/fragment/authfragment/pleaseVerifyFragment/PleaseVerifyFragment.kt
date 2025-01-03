package com.yesitlab.compro.fragment.authfragment.pleaseVerifyFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.R
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.databinding.FragmentPleaseVerifyBinding


class PleaseVerifyFragment : Fragment() , OnClickListener{
   private lateinit var binding : FragmentPleaseVerifyBinding
   private var verificationType = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          verificationType = it.getInt(AppConstant.verificationType)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPleaseVerifyBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textChangeEmail.setOnClickListener(this)
        binding.imageBackButton.setOnClickListener(this)
        binding.rlResendVerificationEmail.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.textChangeEmail->{
                when(verificationType){
                    0 ->findNavController().navigate(R.id.createAnAccountFragment)

                    1-> findNavController().navigate(R.id.forgotPasswordFragment)

                }
            }
            R.id.rlResendVerificationEmail->{

                when(verificationType){

                    0 -> {
                        findNavController().navigate(R.id.accountVerifiedFragment)
                    }

                    1-> findNavController().navigate(R.id.changePasswordFragment)

                }
            }
            R.id.imageBackButton->{
                when(verificationType){
                    0 ->findNavController().navigate(R.id.createAnAccountFragment)

                    1-> findNavController().navigate(R.id.forgotPasswordFragment)

                }
            }
        }
    }


}