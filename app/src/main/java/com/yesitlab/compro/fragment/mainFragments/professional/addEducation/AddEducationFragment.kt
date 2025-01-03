package com.yesitlab.compro.fragment.mainFragments.professional.addEducation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.EducationAdapter
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentAddEducationBinding
import com.yesitlab.compro.model.AddEducationModel


class AddEducationFragment : Fragment(), OnItemClickListener, OnClickListener {
    lateinit var binding: FragmentAddEducationBinding
    private lateinit var educationAdapter: EducationAdapter
    private var list: MutableList<AddEducationModel> = mutableListOf()

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
        binding = FragmentAddEducationBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageAddEducation.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        initialize()
    }

    private fun initialize() {
        educationAdapter = EducationAdapter(requireActivity(), listOf(), this)
        binding.recyclerViewEducation.adapter = educationAdapter
        educationList()
        educationAdapter.updateData(list)
    }

    private fun educationList() {
       list.add(
           AddEducationModel(
               "Governors State University",
               "Bachelor of Arts (BA) Computer",
               "science 2011-2029 (expected)",
               "Ever Since Our inception, We Have Assisted\\nOur Partners Fulfil Recruitment Goals For\\nTheir Clients Speedily"
           )
       )
        list.add(
            AddEducationModel(
                "Governors State University",
                "Bachelor of Arts (BA) Computer",
                "science 2011-2029 (expected)",
                "Ever Since Our inception, We Have Assisted\\nOur Partners Fulfil Recruitment Goals For\\nTheir Clients Speedily"
            )
        )
        list.add(
            AddEducationModel(
                "Governors State University",
                "Bachelor of Arts (BA) Computer",
                "science 2011-2029 (expected)",
                "Ever Since Our inception, We Have Assisted\\nOur Partners Fulfil Recruitment Goals For\\nTheir Clients Speedily"
            )
        )
        list.add(
            AddEducationModel(
                "Governors State University",
                "Bachelor of Arts (BA) Computer",
                "science 2011-2029 (expected)",
                "Ever Since Our inception, We Have Assisted\\nOur Partners Fulfil Recruitment Goals For\\nTheir Clients Speedily"
            )
        )
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.imageAddEducation->{
                bottomAddEducation()
            }
            R.id.textBackButton->{
                findNavController().navigate(R.id.addExperienceFragment)
            }
            R.id.textNextButton->{
                findNavController().navigate(R.id.portfolioFragment)
            }
            R.id.textSkipForNow->{
                findNavController().navigate(R.id.portfolioFragment)
            }
        }
    }

    private fun bottomAddEducation() {
        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_add_education)
        bottomSheetDialog.show()

        val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton)!!
        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!


        btnSubmit.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
    }
    private fun bottomSelect(position: Int) {
        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_select)
        bottomSheetDialog.show()

        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!
        val textEdit: TextView = bottomSheetDialog.findViewById(R.id.textEdit)!!
        val textDelete: TextView = bottomSheetDialog.findViewById(R.id.textDelete)!!

        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        textEdit.setOnClickListener {
            bottomSheetDialog.dismiss()
            bottomEditEducation(position)
        }
        textDelete.setOnClickListener {
            bottomSheetDialog.dismiss()
            ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this education detail?")
        }
    }
    private fun bottomEditEducation(position: Int) {
        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_edit_education)
        bottomSheetDialog.show()

        val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton)!!
        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!
        btnSubmit.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    override fun itemClick(position: Int) {
        bottomSelect(position)
    }


}