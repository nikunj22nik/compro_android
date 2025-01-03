package com.yesitlab.compro.fragment.mainFragments.professional.profileVisibilityFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.yesitlab.compro.R
import com.yesitlab.compro.databinding.FragmentProfileVisibilityBinding

class ProfileVisibilityFragment : Fragment(){
   lateinit var  binding: FragmentProfileVisibilityBinding

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
        binding = FragmentProfileVisibilityBinding.inflate(LayoutInflater.from(requireActivity()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = arrayOf("Public", "Private")
        val subCategoryAdapter = ArrayAdapter(requireContext(),R.layout.item_drop_down,list)
       binding.spinner.setAdapter(subCategoryAdapter)


    }
}