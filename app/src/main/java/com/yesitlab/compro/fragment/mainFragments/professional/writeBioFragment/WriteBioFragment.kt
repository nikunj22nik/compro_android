package com.yesitlab.compro.fragment.mainFragments.professional.writeBioFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.R
import com.yesitlab.compro.databinding.FragmentWriteBioBinding


class WriteBioFragment : Fragment() ,OnClickListener{
lateinit var  binding : FragmentWriteBioBinding


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
        binding = FragmentWriteBioBinding.inflate(LayoutInflater.from(requireActivity()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.textNextButton->{
                findNavController().navigate(R.id.previewProfileFragment)
            }
            R.id.textSkipForNow->{
                findNavController().navigate(R.id.previewProfileFragment)
            }
            R.id.textBackButton->{
                findNavController().navigate(R.id.createProfileFragment)
            }

        }
    }



}