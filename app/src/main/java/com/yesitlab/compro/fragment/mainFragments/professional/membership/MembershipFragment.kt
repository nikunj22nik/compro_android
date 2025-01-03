package com.yesitlab.compro.fragment.mainFragments.professional.membership

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.yesitlab.compro.R
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.databinding.FragmentMembershipBinding

class MembershipFragment : Fragment() ,OnClickListener{
    private lateinit var binding: FragmentMembershipBinding
      var flowType: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMembershipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textNextButton.setOnClickListener(this)

        binding.textBackButton.setOnClickListener(this)
        flowType = arguments?.getString(AppConstant.membership)



        if (flowType == AppConstant.bigOpportunity){

            binding.textBackButton.visibility = View.VISIBLE
            binding.textNextButton.visibility = View.VISIBLE
        }
        else{

            binding.textBackButton.visibility = View.GONE
            binding.textNextButton.visibility = View.GONE
        }

        // Set click listeners for each LinearLayout
        binding.llBasic.setOnClickListener {
            selectMembership(
                binding.llBasic,
                binding.textBasicButton,
                binding.textBasic,
                binding.textBasicAmount,
                binding.textAllowedBasic
            )
        }
        binding.llProfessional.setOnClickListener {
            selectMembership(
                binding.llProfessional,
                binding.textProfessionalButton,
                binding.textProfessional,
                binding.textProfessionalAmount,
                binding.textAllowedProfessional
            )
        }
        binding.llAgency.setOnClickListener {
            selectMembership(
                binding.llAgency,
                binding.textAgencyButton,
                binding.textAgency,
                binding.textAgencyAmount,
                binding.textAllowedAgency
            )
        }
        binding.llExpert.setOnClickListener {
            selectMembership(
                binding.llExpert,
                binding.textExpertButton,
                binding.textExpert,
                binding.textExpertAmount,
                binding.textAllowedExpert
            )
        }
    }

    private fun selectMembership(
        selectedLayout: LinearLayout,
        selectedButton: TextView,
        selectedTitle: TextView,
        selectedAmount: TextView,
        selectedAllowed: TextView
    ) {
        // Reset all backgrounds and button tints to default
        resetMemberships()

        // Highlight the selected membership
        selectedLayout.setBackgroundResource(R.color.deepNavyBlue)
        selectedButton.setBackgroundResource(R.drawable.white_button_bg)
        selectedTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        selectedAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        selectedAllowed.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        selectedButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.deepNavyBlue))



        // Set click listener for the selected button to navigate
        binding.textBasicButton.setOnClickListener {
            navigateToNextFragment() // Call function to navigate
        }
        binding.textProfessionalButton.setOnClickListener {
            navigateToNextFragment()
        }
        binding.textAgencyButton.setOnClickListener {
            navigateToNextFragment()
        }
        binding.textExpertButton.setOnClickListener {
            navigateToNextFragment()
        }
    }

    private fun navigateToNextFragment() {
        if (flowType == AppConstant.bigOpportunity){
            findNavController().navigate(R.id.addExperienceFragment)
        }
        else{
            findNavController().navigate(R.id.currentPlanFragment)
        }

    }

    private fun resetMemberships() {
        // Reset all memberships to the default background
        binding.llBasic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightGray))
        binding.llProfessional.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightGray))
        binding.llAgency.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightGray))
        binding.llExpert.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lightGray))

        // Reset button backgrounds to the default state
        binding.textBasicButton.setBackgroundResource(R.drawable.blue_gradiant_button_bg)
        binding.textProfessionalButton.setBackgroundResource(R.drawable.blue_gradiant_button_bg)
        binding.textAgencyButton.setBackgroundResource(R.drawable.blue_gradiant_button_bg)
        binding.textExpertButton.setBackgroundResource(R.drawable.blue_gradiant_button_bg)

        // Reset text colors to the default state
        binding.textBasic.setTextColor(ContextCompat.getColor(requireContext(), R.color.vividOrange))
        binding.textAgency.setTextColor(ContextCompat.getColor(requireContext(), R.color.vividOrange))
        binding.textExpert.setTextColor(ContextCompat.getColor(requireContext(), R.color.vividOrange))
        binding.textProfessional.setTextColor(ContextCompat.getColor(requireContext(), R.color.vividOrange))

        binding.textBasicAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.textAgencyAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.textExpertAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.textProfessionalAmount.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))

        binding.textAllowedBasic.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        binding.textAllowedAgency.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        binding.textAllowedExpert.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
        binding.textAllowedProfessional.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))

        // Reset button text colors to the default state
        binding.textBasicButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.textProfessionalButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.textAgencyButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.textExpertButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.textBackButton -> {

                findNavController().navigate(R.id.bigOpportunityFragment)
            }

            R.id.textNextButton -> {
                findNavController().navigate(R.id.addExperienceFragment)
            }

            R.id.textSkipForNow -> {
                findNavController().navigate(R.id.addExperienceFragment)
            }
        }}
}