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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yesitlab.compro.R
import com.yesitlab.compro.base.BottomSheetHelper
import com.yesitlab.compro.base.Path
import com.yesitlab.compro.databinding.FragmentCreateProfileBinding
import java.io.File
import java.util.Calendar


class CreateProfileFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentCreateProfileBinding
    private var image_status = ""
    private var mYear = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    var imageProfile: ImageView? = null
    var bottomSheetImageProfile: ImageView? = null
    private lateinit var bottomSheetDialog: BottomSheetDialog

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
                    val file = File(Path.getPath(requireContext(), uri))

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
            val c = Calendar.getInstance()
            mYear = c[Calendar.YEAR]
            mMonth = c[Calendar.MONTH]
            mDay = c[Calendar.DAY_OF_MONTH]
            val datePickerDialog = DatePickerDialog(requireActivity(),
                { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    binding.textDOB.text =
                        dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year

                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
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
                findNavController().navigate(R.id.writeBioFragment)
            }
            R.id.textSkipForNow->{
                findNavController().navigate(R.id.writeBioFragment)
            }
            R.id.textBackButton->{
                findNavController().navigate(R.id.addResumeFragment)
            }

        }
    }
}