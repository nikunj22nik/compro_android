package com.yesitlab.compro.fragment.mainFragments.professional.bigOpportunity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.R
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.databinding.FragmentBigOpportunityBinding


class BigOpportunityFragment : Fragment() ,OnClickListener{
  lateinit var binding : FragmentBigOpportunityBinding
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
        binding = FragmentBigOpportunityBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rlGetStarted.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
       when(p0?.id){

           R.id.rlGetStarted->{
               val bundle = Bundle()
               bundle.putString(AppConstant.membership, AppConstant.bigOpportunity)
               findNavController().navigate(R.id.membershipFragment,bundle)
           }
       }
    }


}