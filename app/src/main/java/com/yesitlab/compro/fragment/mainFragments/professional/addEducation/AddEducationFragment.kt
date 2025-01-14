package com.yesitlab.compro.fragment.mainFragments.professional.addEducation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.NetworkResult
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.yesitlab.compro.BaseApplication
import com.yesitlab.compro.Click
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.LoadingUtils.Companion.showErrorDialog
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.EducationAdapter
import com.yesitlab.compro.base.BaseUrl.Companion.check_online
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.base.TimeManager
import com.yesitlab.compro.databinding.FragmentAddEducationBinding
import com.yesitlab.compro.model.Education
import com.yesitlab.compro.viewmodel.ApiEducationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEducationFragment : Fragment(), OnItemClickListener, OnClickListener {
    lateinit var binding: FragmentAddEducationBinding
    private lateinit var educationAdapter: EducationAdapter
    private var list: MutableList<Education> = mutableListOf()
    lateinit var etSchool: EditText
    lateinit var etDegree: EditText
    lateinit var etFieldStudy: EditText
    lateinit var etCountry: EditText
    lateinit var etDescription: EditText

    lateinit var etStartDate: String
    lateinit var etEndDate: String

    lateinit var etSchoolUpdate: EditText
    lateinit var etDegreeUpdate: EditText
    lateinit var etFieldStudyUpdate: EditText
    lateinit var etCountryUpdate: EditText
    lateinit var etDescriptionUpdate: EditText

    lateinit var etStartDateUpdate: String
    lateinit var etEndDateUpdate: String

    private lateinit var viewModel: ApiEducationViewModel

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
        if (BaseApplication.isOnline(requireContext())) {
            lifecycleScope.launch {
                apiGetEducation()
            }
        } else {
            showErrorDialog(requireContext(), check_online)
        }

        binding.pullToRefresh.setOnRefreshListener {
            if (BaseApplication.isOnline(requireContext())) {
                lifecycleScope.launch {
                    apiGetEducation()
                }
            } else {
                showErrorDialog(requireContext(), check_online)
            }
        }
        initialize()
    }

    private fun initialize() {
        educationAdapter = EducationAdapter(requireActivity(), mutableListOf(), this)
        binding.recyclerViewEducation.adapter = educationAdapter
        //   educationList()
        //   educationAdapter.updateData(list)
    }
    /*
       private fun educationList() {
          list.add(
              Education(
                  "Governors State University",
                  "Bachelor of Arts (BA) Computer",
                  "science 2011-2029 (expected)",
                  "Ever Since Our inception, We Have Assisted\\nOur Partners Fulfil Recruitment Goals For\\nTheir Clients Speedily"
              )
          )
           list.add(
               Education(
                   "Governors State University",
                   "Bachelor of Arts (BA) Computer",
                   "science 2011-2029 (expected)",
                   "Ever Since Our inception, We Have Assisted\\nOur Partners Fulfil Recruitment Goals For\\nTheir Clients Speedily"
               )
           )
           list.add(
               Education(
                   "Governors State University",
                   "Bachelor of Arts (BA) Computer",
                   "science 2011-2029 (expected)",
                   "Ever Since Our inception, We Have Assisted\\nOur Partners Fulfil Recruitment Goals For\\nTheir Clients Speedily"
               )
           )
           list.add(
               Education(
                   "Governors State University",
                   "Bachelor of Arts (BA) Computer",
                   "science 2011-2029 (expected)",
                   "Ever Since Our inception, We Have Assisted\\nOur Partners Fulfil Recruitment Goals For\\nTheir Clients Speedily"
               )
           )
       }

     */

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imageAddEducation -> {
                bottomAddEducation()
            }

            R.id.textBackButton -> {
                findNavController().navigate(R.id.addExperienceFragment)
            }

            R.id.textNextButton -> {
                findNavController().navigate(R.id.portfolioFragment)
            }

            R.id.textSkipForNow -> {
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


        etSchool = bottomSheetDialog.findViewById(R.id.etSchool)!!
        etDegree = bottomSheetDialog.findViewById(R.id.etDegree)!!
        etFieldStudy = bottomSheetDialog.findViewById(R.id.etFieldStudy)!!
        etCountry = bottomSheetDialog.findViewById(R.id.etCountry)!!
        etDescription = bottomSheetDialog.findViewById(R.id.etDescription)!!



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
                apiAddEducation(
                    etSchool.text.trim().toString(),
                    etDegree.text.trim().toString(),
                    etFieldStudy.text.trim().toString(),
                    etCountry.text.trim().toString(),
                    etStartDate,
                    etEndDate,
                    etDescription.text.trim().toString()
                )
            }

            bottomSheetDialog.dismiss()

        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
    }


    private suspend fun apiAddEducation(
        etTitle: String,
        etCompany: String,
        etLocation: String,
        etCountry: String,
        etStartDate: String,
        etEndDate: String,
        etDescription: String
    ) {
        var user_id: String = commonUtils.getUserId().toString()
        var profile_id: String = "2"

        LoadingUtils.showDialog(requireContext(), true)

        viewModel.apiAddEducation(
            user_id,
            profile_id,
            etCompany,
            etTitle,
            etLocation,
            etCountry,
            etStartDate,
            etEndDate,
            etDescription
        ) {
            when (it) {
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()

                }

                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    showErrorDialog(requireContext(), it.message.toString())

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
            ErrorMsgBox.alertDeleteEdit(requireContext(),
                "Are you sure you want to delete this education detail?",
                object : Click {
                    override fun confirm() {
                        lifecycleScope.launch {
                            apiDeleteEducation(position.toString())
                        }
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
        etSchoolUpdate = bottomSheetDialog.findViewById(R.id.etSchool)!!

        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!
        btnSubmit.setOnClickListener {


            bottomSheetDialog.dismiss()

            if (BaseApplication.isOnline(requireContext())) {
                lifecycleScope.launch {
                    apiUpdateEducation(
                        etSchoolUpdate.text.toString().trim(),
                        etDegreeUpdate.toString(),
                        etFieldStudyUpdate.toString(),
                        etCountryUpdate.toString(),
                        etStartDateUpdate,
                        etEndDateUpdate,
                        etDescriptionUpdate.toString()
                    )
                }

            } else {
                Toast.makeText(requireContext(), check_online, Toast.LENGTH_LONG).show()
            }
        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
    }

    private suspend fun apiUpdateEducation(
        etSchoolUpdate: String,
        etDegreeUpdate: String,
        etFieldStudyUpdate: String,
        etCountryUpdate: String,
        etStartDateUpdate: String,
        etEndDateUpdate: String,
        etDescriptionUpdate: String
    ) {
        var user_id: String = commonUtils.getUserId().toString()
        var id: String = "1"

        LoadingUtils.showDialog(requireContext(), true)
        viewModel.apiUpdateEducation(
            id,
            user_id,
            etSchoolUpdate,
            etDegreeUpdate,
            etFieldStudyUpdate,
            etCountryUpdate,
            etStartDateUpdate,
            etEndDateUpdate,
            etDescriptionUpdate
        ) {
            when (it) {
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()


                    LoadingUtils.showSuccessDialog(
                        requireContext(),
                        "Education details add successfully"
                    )
                }

                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    showErrorDialog(requireContext(), it.message.toString())

                }

                is NetworkResult.Loading -> TODO()

            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun apiGetEducation() {
        val jsonObject = JsonObject().apply {
            addProperty("user_id", commonUtils.getUserId().toString())
        }

        LoadingUtils.showDialog(requireContext(), true)

        viewModel.apiGetEducation(jsonObject) { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()
                    binding.pullToRefresh.isRefreshing = false

                    try {
                        // Parse the response into the correct data model (Education)
                        val type = object : TypeToken<MutableList<Education>>() {}.type
                        val educationList: MutableList<Education> =
                            Gson().fromJson(networkResult.data, type)

                        // Update the adapter with the parsed data
                        educationAdapter.updateData(educationList)
                        educationAdapter.notifyDataSetChanged()
                        
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showErrorDialog(requireContext(), "Failed to parse education details")
                    }
                }

                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    binding.pullToRefresh.isRefreshing = false
                    showErrorDialog(requireContext(), networkResult.message ?: "An error occurred")
                }

                is NetworkResult.Loading -> {
                    // Handle loading state, if applicable
                    // Optionally, show a progress indicator
                }
            }
        }
    }


    private suspend fun apiDeleteEducation(id: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("id", id)
        LoadingUtils.showDialog(requireContext(), true)

        viewModel.apiDeleteEducation(jsonObject) {
            when (it) {
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()

                    it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }

                    if (BaseApplication.isOnline(requireContext())) {
                        lifecycleScope.launch {
                            apiGetEducation()
                        }
                    } else {
                        showErrorDialog(requireContext(), check_online)
                    }
                }

                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    showErrorDialog(requireContext(), it.message.toString())
                }

                is NetworkResult.Loading -> TODO()

            }
        }


    }


    override fun itemClick(position: Int) {
        Log.d("vishal", position.toString())
        bottomSelect(position)
    }


}