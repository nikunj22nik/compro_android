package com.yesitlab.compro.fragment.mainFragments.professional.addExperience

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.CommonUtild
import com.example.network.NetworkResult
import com.example.network.requestModel.AddExperienceRequest
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.checkbox.MaterialCheckBox.CheckedState
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.yesitlab.compro.BaseApplication
import com.yesitlab.compro.Click
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.activity.homeActivity.HomeActivity
import com.yesitlab.compro.adapter.ExperienceAdapter
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.base.TimeManager
import com.yesitlab.compro.databinding.FragmentAddExperienceBinding
import com.yesitlab.compro.model.AddExperienceModel
import com.yesitlab.compro.viewmodel.ApiExperienceViewModel
import com.yesitlab.compro.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AddExperienceFragment : Fragment(), OnItemClickListener, OnClickListener {

    private lateinit var binding: FragmentAddExperienceBinding
    private lateinit var experienceAdapter: ExperienceAdapter
    private var list: MutableList<AddExperienceModel> = mutableListOf() // Mutable list to allow adding items
     var etTitle : EditText? = null
     var etCompany : EditText? = null
     var etLocation : EditText? = null
     var etCountry : EditText? = null

     var etStartDate : String? = ""
     var etEndDate : String? = ""
    var currentPro : Int = 0

    var idUpdate : Int = -1
     var etTitleUpdate : EditText? = null
     var etCompanyUpdate : EditText? = null
     var etLocationUpdate : EditText? = null
     var etCountryUpdate : EditText? = null

     var etStartDateUpdate : String? = ""
     var etEndDateUpdate : String? = ""
    var currentProUpdate : Int = 0


    private lateinit var viewModel : ApiExperienceViewModel

    private lateinit var commonUtils: CommonUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddExperienceBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ApiExperienceViewModel::class.java]

        commonUtils = CommonUtils(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageAddExperience.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        initialize()  // Call the initialization method correctly

        if (BaseApplication.isOnline(requireContext())) {
            lifecycleScope.launch {
                apiGetExperience()
            }
        } else {
            showErrorDialog("Please Check Your Network")
        }


        binding.pullToRefresh.setOnRefreshListener {

            if (BaseApplication.isOnline(requireContext())) {
                lifecycleScope.launch {
                    apiGetExperience()
                }
            } else {
                showErrorDialog("Please Check Your Network")
            }

        }
    }

    private fun initialize() {
        experienceAdapter = ExperienceAdapter(mutableListOf(), requireActivity(), this)
        binding.recyclerViewExp.adapter = experienceAdapter
        //  experienceList()
        //  experienceAdapter.updateData(list)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun bottomAddExperience() {


        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_add_experience)
        bottomSheetDialog.show()

        val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton)!!

        etTitle  = bottomSheetDialog.findViewById<EditText>(R.id.etTitle)!!
        etCompany  = bottomSheetDialog.findViewById<EditText>(R.id.etCompany)!!
        etLocation  = bottomSheetDialog.findViewById<EditText>(R.id.etLocation)!!
        etCountry = bottomSheetDialog.findViewById<EditText>(R.id.etCountry)!!

//        etStartDate  = bottomSheetDialog.findViewById<TextView>(R.id.etStartDate).toString()!!
//         etEndDate  = bottomSheetDialog.findViewById<TextView>(R.id.etEndDate).toString()!!


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


        currentPro = 0

        bottomSheetDialog.findViewById<CheckBox>(R.id.checkbox1)!!
            .setOnCheckedChangeListener { compoundButton, isChecked ->

                if (isChecked){
                    currentPro = 1
                }else{
                    currentPro = 0
                }
                Log.d("check", if (isChecked) "Checked" else "Unchecked")
            }


        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!


        btnSubmit.setOnClickListener {

            if (BaseApplication.isOnline(requireContext())) {
                if(validateInputs(etTitle?.text?.trim().toString(),etCompany?.text?.trim().toString(),etLocation?.text?.trim().toString(),etCountry?.text?.trim().toString(),etStartDate,etEndDate,bottomSheetDialog.findViewById<CheckBox>(R.id.checkbox1))){
                    lifecycleScope.launch {
                        apiAddExperience(etTitle?.text?.trim().toString(),etCompany?.text?.trim().toString(),etLocation?.text?.trim().toString(),etCountry?.text?.trim().toString(),  etStartDate,etEndDate)
                    }
                }


            } else {
                showErrorDialog("Please Check Your Network")
            }



        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
    }

    private suspend fun apiAddExperience(etTitle: String, etCompany: String, etLocation: String, etCountry: String, etStartDate: String?, etEndDate: String?) {
        var user_id : String = commonUtils.getUserId().toString()
        var profile_id : String = "2"




        LoadingUtils.showDialog(requireContext(),true)

        viewModel.apiAddExperience(user_id,profile_id,etCompany,etTitle,etLocation,etCountry,currentPro.toString() ,etStartDate.toString(),etEndDate.toString()){
            when(it){
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()

                    if (BaseApplication.isOnline(requireContext())) {
                        lifecycleScope.launch {
                            apiGetExperience()
                        }
                    } else {
                        showErrorDialog("Please Check Your Network")
                    }
                    it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }
                }
                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(),it.message.toString())

                }
                is NetworkResult.Loading -> TODO()

            }
        }


    }


    private suspend fun apiGetExperience() {
        // var user_id : String = commonUtils.getUserId().toString()

        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", commonUtils.getUserId().toString())


        LoadingUtils.showDialog(requireContext(),true)

        viewModel.apiGetExperience(jsonObject){
            when(it){
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()
                    binding.pullToRefresh.isRefreshing = false

                    val type = object : TypeToken<List<AddExperienceModel>>() {}.type
                    val model: List<AddExperienceModel> = Gson().fromJson(it.data, type)

                    // var model = Gson().fromJson<JsonObject>(it.data,AddExperienceModel::class.java)

                    experienceAdapter.updateData(model)

                 //   LoadingUtils.showSuccessDialog(requireContext(),"Experience details get successfully")
                }
                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    binding.pullToRefresh.isRefreshing = false
                    LoadingUtils.showErrorDialog(requireContext(),it.message.toString())

                }
                is NetworkResult.Loading -> TODO()

            }
        }


    }

    private suspend fun apiDeleteExperience( id : String) {
        // var user_id : String = commonUtils.getUserId().toString()
        val jsonObject = JsonObject()
        jsonObject.addProperty("id", id)
        LoadingUtils.showDialog(requireContext(),true)

        viewModel.apiDeleteExperience(jsonObject){
            when(it){
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()


                    // var model = Gson().fromJson<JsonObject>(it.data,AddExperienceModel::class.java)


                    it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }
                }
                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()

                    LoadingUtils.showErrorDialog(requireContext(),it.message.toString())

                }
                is NetworkResult.Loading -> TODO()

            }
        }


    }


    private fun bottomEditExperience(position: Int) {


        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_edit_experience)
        bottomSheetDialog.show()

        val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton)!!
         etTitleUpdate  = bottomSheetDialog.findViewById(R.id.etTitle)!!
         etCompanyUpdate  = bottomSheetDialog.findViewById(R.id.etCompany)!!
        etLocationUpdate = bottomSheetDialog.findViewById(R.id.etLocation)!!
        etCountryUpdate  = bottomSheetDialog.findViewById(R.id.etCountry)!!
        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!
        currentProUpdate = 0

        bottomSheetDialog.findViewById<CheckBox>(R.id.checkbox2)!!
            .setOnCheckedChangeListener { compoundButton, isChecked ->

                if (isChecked){
                    currentProUpdate = 1
                }else{
                    currentProUpdate = 0
                }
                Log.d("check", if (isChecked) "Checked" else "Unchecked")
            }
        bottomSheetDialog.findViewById<TextView>(R.id.etStartDate)?.setOnClickListener {
            val timeManager = TimeManager(requireContext())
            timeManager.selectDateManager { selectedDate ->
                // Update the TextView with the selected date
                bottomSheetDialog.findViewById<TextView>(R.id.etStartDate)?.text = selectedDate
                etStartDateUpdate = selectedDate
            }
        }


        bottomSheetDialog.findViewById<TextView>(R.id.etEndDate)?.setOnClickListener {
            val timeManager = TimeManager(requireContext())
            timeManager.selectDateManager { selectedDate ->
                // Update the TextView with the selected date
                bottomSheetDialog.findViewById<TextView>(R.id.etEndDate)?.text = selectedDate
                etEndDateUpdate = selectedDate
            }
        }


        btnSubmit.setOnClickListener {
            idUpdate = position
            Log.d("idcheck", position.toString())

            if (!idUpdate.equals(-1)){

                if(validateInputs(etTitleUpdate?.text?.trim().toString(),etCompanyUpdate?.text?.trim().toString(),etLocationUpdate?.text?.trim().toString(),etCountryUpdate?.text?.trim().toString(),etStartDateUpdate,etEndDateUpdate,bottomSheetDialog.findViewById<CheckBox>(R.id.checkbox2))){
                    lifecycleScope.launch {
                        apiUpdateExperience(idUpdate,etTitleUpdate?.text?.trim().toString(), etCompanyUpdate?.text?.trim().toString(), etLocationUpdate?.text?.trim().toString(), etCountryUpdate?.text?.trim().toString(),currentProUpdate.toString() , etStartDateUpdate, etEndDateUpdate)

                    } }

                bottomSheetDialog.dismiss()
            }



        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
    }



    private suspend fun apiUpdateExperience(idUpdate : Int,etTitle: String, etCompany: String, etLocation: String, etCountry: String,currentPro :String, etStartDate: String?, etEndDate: String?) {

        var user_id : String = commonUtils.getUserId().toString()
        var profile_id : String = "1"


        LoadingUtils.showDialog(requireContext(),true)

        viewModel.apiUpdateExperience(idUpdate.toString(),user_id,profile_id,etCompany,etTitle,etLocation,etCountry,currentPro ,etStartDate.toString(),etEndDate.toString()){
            when(it){
                is NetworkResult.Success -> {

                    LoadingUtils.hideDialog()

                    if (BaseApplication.isOnline(requireContext())) {
                        lifecycleScope.launch {
                            apiGetExperience()
                        }
                    } else {
                        showErrorDialog("Please Check Your Network")
                    }

                    it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }

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
        Log.d("idcheckSelect", position.toString())

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
            bottomEditExperience(position)
        }
        textDelete.setOnClickListener {
            bottomSheetDialog.dismiss()
            ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this experience detail?",
                object : Click {
                    override fun confirm() {

                        lifecycleScope.launch {
                            apiDeleteExperience(position.toString())
                        }

                    }

                })
        }
    }

    /*
        private fun experienceList() {
            list.add(
                AddExperienceModel(
                    "Front end developer", "head field solution pvt", "2019-05-29 - 2024-08-22 Noida India"
                )
            )

            list.add(
                AddExperienceModel(
                    "Front end developer", "head field solution pvt", "2019-05-29 - 2024-08-22 Noida India"
                )
            )

            list.add(
                AddExperienceModel(
                    "Front end developer", "head field solution pvt", "2019-05-29 - 2024-08-22 Noida India. 2019-05-29 - 2024-08-22 Noida India. 2019-05-29 - 2024-08-22 Noida India. 2019-05-29 - 2024-08-22 Noida India. 2019-05-29 - 2024-08-22 Noida India. 2019-05-29 - 2024-08-22 Noida India. 2019-05-29 - 2024-08-22 Noida India."
                )
            )
            list.add(
                AddExperienceModel(
                    "Front end developer", "head field solution pvt", "2019-05-29 - 2024-08-22 Noida India"
                )
            )
            list.add(
                AddExperienceModel(
                    "Front end developer", "head field solution pvt", "2019-05-29 - 2024-08-22 Noida India"
                )
            )
            list.add(
                AddExperienceModel(
                    "Front end developer", "head field solution pvt", "2019-05-29 - 2024-08-22 Noida India"
                )
            )


        }

     */

    override fun itemClick(position: Int) {
        // Handle item click
        Log.d("idcheckitemClick", position.toString())
        Toast.makeText(requireContext(),position.toString(),Toast.LENGTH_SHORT).show()
        bottomSelect(position)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.imageAddExperience->{
                bottomAddExperience()
            }
            R.id.textBackButton -> {
                findNavController().navigate(R.id.membershipFragment)
            }

            R.id.textNextButton->{
                findNavController().navigate(R.id.addEducationFragment)
            }
            R.id.textSkipForNow->{
                findNavController().navigate(R.id.addEducationFragment)
            }

        }
    }
    private fun showErrorDialog(message: String) {
        ErrorMsgBox.alertError(context, message)
    }



    private fun validateInputs(etTitle:String,etCompany:String,etLocation:String,etCountry:String,etStartDate:String?,etEndDate:String?,checkbox2:CheckBox?) :Boolean{
        // Validate Title
        val title = etTitle
        if (title.isEmpty()) {
            showToast("Title is required")
            return false
        }

        // Validate Company
        val company = etCompany
        if (company.isEmpty()) {
            showToast("Company is required")
            return false
        }

        // Validate Location
        val location = etLocation
        if (location.isEmpty()) {
            showToast("Location is required")
            return false
        }

        // Validate Country
        val country = etCountry
        if (country.isEmpty()) {
            showToast("Country is required")
            return false
        }

        // Validate Start Date
        val startDate = etStartDate
        if (!startDate?.let { isValidDate(it) }!!) {
            showToast("Invalid Start Date. Use format: YYYY-MM-DD")
            return false
        }

        // Validate End Date (if not current)
        if (!checkbox2?.isChecked!!) {
            val endDate = etEndDate
            if (!endDate?.let { isValidDate(it) }!!) {
                showToast("Invalid End Date. Use format: YYYY-MM-DD")
                return false
            }
            // Check if End Date is after or equal to Start Date
            if (!isEndDateAfterOrEqualToStartDate(etStartDate, etEndDate)) {
                showToast("End Date must be after or equal to Start Date")
                return false
            }

        }



        return true

    }

    private fun isValidDate(date: String): Boolean {
        val regex = Regex("\\d{4}-\\d{2}-\\d{2}")
        return regex.matches(date)
    }


    private fun isEndDateAfterOrEqualToStartDate(startDate: String, endDate: String): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val start = sdf.parse(startDate)
            val end = sdf.parse(endDate)
            end != null && (end.after(start) || end == start)
        } catch (e: ParseException) {
            false
        }
    }

    private fun showToast(message: String) {
        LoadingUtils.showErrorDialog(requireContext(),message)
     //   Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
