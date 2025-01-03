package com.yesitlab.compro.fragment.mainFragments.professional.professionalContactInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yesitlab.compro.R
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentProfessionalContactInfoBinding


class ProfessionalContactInfoFragment : Fragment() {
    private lateinit var binding: FragmentProfessionalContactInfoBinding

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
        binding = FragmentProfessionalContactInfoBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submit.setOnClickListener {
            ErrorMsgBox.alertError(requireContext(), "Thank you for contacting us")
        }
    }


}