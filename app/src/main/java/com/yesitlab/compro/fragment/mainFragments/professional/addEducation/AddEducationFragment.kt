package com.yesitlab.compro.fragment.mainFragments.professional.addEducation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yesitlab.compro.Click
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.EducationAdapter
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.base.TimeManager
import com.yesitlab.compro.databinding.FragmentAddEducationBinding
import com.yesitlab.compro.model.AddEducationModel
import com.yesitlab.compro.viewmodel.ApiEducationViewModel
import com.yesitlab.compro.viewmodel.ApiExperienceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEducationFragment : Fragment(), OnItemClickListener, OnClickListener {
    lateinit var binding: FragmentAddEducationBinding
    private lateinit var educationAdapter: EducationAdapter
    private var list: MutableList<AddEducationModel> = mutableListOf()
    lateinit var etSchool : EditText
    lateinit var etDegree : EditText
    lateinit var etFieldStudy : EditText
    lateinit var etCountry : EditText
    lateinit var etDescription : EditText

    lateinit var etStartDate : String
    lateinit var etEndDate : String

    private lateinit var viewModel : ApiEducationViewModel

    private lateinit var commonUtils: CommonUtils

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


        viewModel = ViewModelProvider(this)[ApiEducationViewModel::class.java]

        commonUtils = CommonUtils(requireContext())
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


        etSchool  = bottomSheetDialog.findViewById<EditText>(R.id.etSchool)!!
        etDegree  = bottomSheetDialog.findViewById<EditText>(R.id.etDegree)!!
        etFieldStudy  = bottomSheetDialog.findViewById<EditText>(R.id.etFieldStudy)!!
        etCountry = bottomSheetDialog.findViewById<EditText>(R.id.etCountry)!!
        etDescription = bottomSheetDialog.findViewById<EditText>(R.id.etDescription)!!



        bottomSheetDialog.findViewById<TextView>(R.id.etStartDate)?.setOnClickListener {
            val timeManager = TimeManager(requireContext())
            timeManager.selectDateManager { selectedDate ->
                // Update the TextView with the selected date
                bottomSheetDialog.findViewById<TextView>(R.id.etStartDate)?.text = selectedDate
                etStartDate = selectedDate
            }
        }


        bottomSheetDialog.findViewById<TextView>(R.id.etEndDate)?.setOnClickListener {
            val timeManager = TimeManager(requireContext())
            timeManager.selectDateManager { selectedDate ->
                // Update the TextView with the selected date
                bottomSheetDialog.findViewById<TextView>(R.id.etEndDate)?.text = selectedDate
                etEndDate = selectedDate
            }
        }



        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!


        btnSubmit.setOnClickListener {
            lifecycleScope.launch {
                apiAddEducation(etSchool.text.trim().toString(), etDegree.text.trim().toString(), etFieldStudy.text.trim().toString(), etCountry.text.trim().toString(), etStartDate, etEndDate,etDescription.text.trim().toString())
            }

            bottomSheetDialog.dismiss()

        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
    }


    private suspend fun apiAddEducation(etTitle: String, etCompany: String, etLocation: String, etCountry: String, etStartDate: String, etEndDate: String,etDescription:String) {
        var user_id : String = commonUtils.getUserId().toString()
        var profile_id : String = "2"





        LoadingUtils.showDialog(requireContext(),true)

        viewModel.apiAddEducation(user_id,profile_id,etCompany,etTitle,etLocation,etCountry ,etStartDate,etEndDate,etDescription){
            when(it){
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()


                    LoadingUtils.showSuccessDialog(requireContext(),"Education details add successfully")
                }
                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(),it.message.toString())

                }
                is NetworkResult.Loading -> TODO()

            }
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
            ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this education detail?",
                object : Click {
                    override fun confirm() {



                    }

                })
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

    private suspend fun apiUpdateEducation(etTitle: String, etCompany: String, etLocation: String, etCountry: String, etStartDate: String, etEndDate: String,etDescription:String) {
        var user_id : String = commonUtils.getUserId().toString()
        var profile_id : String = "2"



var id : String = "0"

        LoadingUtils.showDialog(requireContext(),true)

        viewModel.apiUpdateEducation(id, user_id,profile_id,etCompany,etTitle,etLocation,etCountry ,etStartDate,etEndDate,etDescription){
            when(it){
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()


                    LoadingUtils.showSuccessDialog(requireContext(),"Education details add successfully")
                }
                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(),it.message.toString())

                }
                is NetworkResult.Loading -> TODO()

            }
        }


    }




    override fun itemClick(position: Int) {
        bottomSelect(position)
    }


}