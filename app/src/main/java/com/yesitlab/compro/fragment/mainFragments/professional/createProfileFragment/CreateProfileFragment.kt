package com.yesitlab.compro.fragment.mainFragments.professional.createProfileFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.network.NetworkResult
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yesitlab.compro.BaseApplication
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.R
import com.yesitlab.compro.base.BaseUrl.Companion.check_online
import com.yesitlab.compro.base.BottomSheetHelper
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.Path
import com.yesitlab.compro.base.Path.Companion.getPath
import com.yesitlab.compro.base.TimeManager
import com.yesitlab.compro.databinding.FragmentCreateProfileBinding
import com.yesitlab.compro.viewmodel.ApiEducationViewModel
import com.yesitlab.compro.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class CreateProfileFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentCreateProfileBinding
    private var image_status = ""

    var imageProfile: ImageView? = null
    var bottomSheetImageProfile: ImageView? = null
    private var file: File? = null
    var selectedDate1 : String? = ""
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var viewModel : LocationViewModel
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
        binding = FragmentCreateProfileBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        viewModel = ViewModelProvider(this)[LocationViewModel::class.java]

        commonUtils = CommonUtils(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rlUploadButton.setOnClickListener(this)
        binding.cvDateBirth.setOnClickListener(this)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)

        imageProfile = binding.imageProfileIcon
    }


    private fun bottomYourPicture() {
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_your_photo)
        bottomSheetDialog.show()

        val attachPhoto: TextView? = bottomSheetDialog.findViewById(R.id.textAttachButton)
        val cancel: TextView? = bottomSheetDialog.findViewById(R.id.textCancelButton)
        val imageCross: ImageView? = bottomSheetDialog.findViewById(R.id.imageCross)
        bottomSheetImageProfile = bottomSheetDialog.findViewById(R.id.imageProfileIcon)
        val imageUploadPlusIcon: ImageView? = bottomSheetDialog.findViewById(R.id.imageUploadPlusIcon)

        imageUploadPlusIcon?.setOnClickListener {
            profileImageChooser()
        }
        cancel?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        attachPhoto?.setOnClickListener {

            bottomSheetDialog.dismiss()
        }
        imageCross?.setOnClickListener {

            bottomSheetDialog.dismiss()
        }


    }



    private fun profileImageChooser() {
        ImagePicker.with(this)
            .crop() // Crop image (Optional), Check Customization for more options
            .compress(1024*5) // Final image size will be less than 5 MB (Optional)
            .maxResultSize(
                250,
                250
            ) // Final image resolution will be less than 1080 x 1080 (Optional)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let { uri ->
                     file = getPath(requireContext(), uri)?.let { File(it) }

                    // Load image into Fragment's ImageView
                    Glide.with(this)
                        .load(uri)
                        .error(R.drawable.ic_profile_icon)
                        .placeholder(R.drawable.ic_profile_icon)
                        .into(binding.imageProfileIcon)

                    // Load image into BottomSheetDialog's ImageView if available
                    bottomSheetImageProfile?.let {
                        Glide.with(this)
                            .load(uri)
                            .error(R.drawable.ic_profile_icon)
                            .placeholder(R.drawable.ic_profile_icon)
                            .into(it)
                    }

                    image_status = "1"
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun getDate() {
        binding.textDOB.setOnClickListener {
            TimeManager(requireContext()).selectDateManager { selectedDate->

                binding.textDOB.setText(selectedDate)
                selectedDate1 = selectedDate
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rlUploadButton -> {
                bottomYourPicture()
            }
            R.id.cvDateBirth -> {
                getDate()
            }
            R.id.textNextButton->{

if (validateInputs()){
    if (BaseApplication.isOnline(requireContext())){
        lifecycleScope.launch {
            apiAddLocation()
        }
    }else{
        Toast.makeText(requireContext(),check_online,Toast.LENGTH_SHORT).show()
    }
}



            }
            R.id.textSkipForNow->{
                findNavController().navigate(R.id.writeBioFragment)
            }
            R.id.textBackButton->{
                findNavController().navigate(R.id.addResumeFragment)
            }

        }
    }

    suspend fun apiAddLocation() {

        /*
          user_id: RequestBody,
        streetaddress: RequestBody,
        app_suite: RequestBody,
        city: RequestBody,
        state: RequestBody,
        zipcode: RequestBody,
        dateofbirth: RequestBody,
        professional_title: RequestBody,
        image: MultipartBody.Part,
         */


        val userIdReq = commonUtils.getUserId().toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())

        val streetAddres = binding.etStreetAddress.text.trim()
        val streetAddresReq =
            streetAddres.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val aptSuite = binding.etAptSuite.text.trim()
        val aptSuiteReq = aptSuite.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val city = binding.etCity.text.trim()
        val cityReq = city.toString().toRequestBody("text/plain".toMediaTypeOrNull())


        val stateProvince = binding.etStateProvince.text.trim()
        val stateProvinceReq =
            stateProvince.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val zipcode = binding.etZIPPostal.text.trim()
        val zipcodeReq = zipcode.toString().toRequestBody("text/plain".toMediaTypeOrNull())


        val textDOBReq = selectedDate1!!.toRequestBody("text/plain".toMediaTypeOrNull())

        val title = binding.etProfessionalTitle.text.trim()
        val titleReq = title.toString().toRequestBody("text/plain".toMediaTypeOrNull())


        val profilePic: MultipartBody.Part = file.let {
            val requestFile = file!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("profile_img", file!!.path, requestFile)
        }
        LoadingUtils.showDialog(requireContext(),true)
        viewModel.apiAddLocation(
            userIdReq,
            streetAddresReq,
            aptSuiteReq,
            cityReq,
            stateProvinceReq,
            zipcodeReq,
            textDOBReq,
            titleReq,
            profilePic
        ) {
            when (it) {
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()


                    it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }
                    findNavController().navigate(R.id.writeBioFragment)

                }

                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(), it.message.toString())

                }

                is NetworkResult.Loading -> TODO()

            }
        }

    }
    private fun validateInputs(): Boolean {
        var isValid = true

        // Validate Professional Title
        if (binding.etProfessionalTitle.text.toString().trim().isEmpty()) {
            binding.etProfessionalTitle.error = "Professional title cannot be empty"
            isValid = false
        }

        // Validate Date of Birth
        if (binding.textDOB.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Date of Birth cannot be empty", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (!isValidDateFormat(binding.textDOB.text.toString().trim())) {
            Toast.makeText(requireContext(), "Invalid Date of Birth format. Use YYYY-MM-DD", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Validate Street Address
        if (binding.etStreetAddress.text.toString().trim().isEmpty()) {
            binding.etStreetAddress.error = "Street address cannot be empty"
            isValid = false
        }

        // Validate Apartment/Suite (Optional)
        if (binding.etAptSuite.text.toString().trim().length > 50) {
            binding.etAptSuite.error = "Apt/Suite cannot exceed 50 characters"
            isValid = false
        }

        return isValid
    }

    private fun isValidDateFormat(date: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}