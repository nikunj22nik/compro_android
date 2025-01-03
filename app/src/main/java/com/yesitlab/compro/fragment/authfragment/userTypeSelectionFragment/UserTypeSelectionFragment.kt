package com.yesitlab.compro.fragment.authfragment.userTypeSelectionFragment

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.R
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentUserTypeSelectionBinding


class UserTypeSelectionFragment : Fragment() {
    private lateinit var binding: FragmentUserTypeSelectionBinding
    private lateinit var commonUtils: CommonUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserTypeSelectionBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commonUtils = CommonUtils(requireActivity())



        backPress()

        binding.imageBackButton.setOnClickListener{
            findNavController().navigate(R.id.welcomeFragment)
        }

        // Set up click listeners for CardViews to act as radio buttons
        binding.cardProfessional.setOnClickListener {
            binding.radioProfessional.isChecked = true
            binding.radioUser.isChecked = false
            updateSelection()
        }

        binding.cardUser.setOnClickListener {
            binding.radioUser.isChecked = true
            binding.radioProfessional.isChecked = false
            updateSelection()
        }


        binding.radioUser.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.radioProfessional.isChecked = false
                updateSelection()
            }

        }


        binding.radioProfessional.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.radioUser.isChecked = false
                updateSelection()
            }

        }


        // Handle Create Account Button
        binding.rlCreateAccount.setOnClickListener {
            if (!binding.radioProfessional.isChecked && !binding.radioUser.isChecked) {
                // Show an error message if no selection is made

                ErrorMsgBox.alertError(requireContext(),"Please select a user type.")
            } else {
                val type = if (binding.radioProfessional.isChecked) 0 else 1


                if (type == 0){
                    commonUtils.setUserType(AppConstant.Professional)
                } else{
                    commonUtils.setUserType(AppConstant.User)
                }
                // Create a Bundle to pass arguments
                val bundle = Bundle().apply {
                    putInt(AppConstant.userType, type)
                }

                // Navigate to the next fragment with the bundle
                findNavController().navigate(R.id.createAnAccountFragment, bundle)
            }
        }

    }
    private fun backPress(){
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle back press logic here
                findNavController().navigate(R.id.welcomeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }

    private fun updateSelection() {
        binding.cardProfessional.setBackgroundResource(
            if (binding.radioProfessional.isChecked) R.drawable.selected_radio_background
            else R.drawable.unselected_radio_background
        )

        binding.cardUser.setBackgroundResource(
            if (binding.radioUser.isChecked) R.drawable.selected_radio_background
            else R.drawable.unselected_radio_background
        )

        binding.textProfessional.setTextColor(
            if (binding.radioProfessional.isChecked) Color.parseColor("#ffffff")
            else Color.parseColor("#696969")
        )

        binding.textUser.setTextColor(
            if (binding.radioUser.isChecked) Color.parseColor("#ffffff")
            else Color.parseColor("#696969")
        )
    }
}