package com.yesitlab.compro.fragment.mainFragments.userMyProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yesitlab.compro.R
import com.yesitlab.compro.base.Path
import com.yesitlab.compro.databinding.FragmentUserMyProfileBinding
import java.io.File


class UserMyProfileFragment : Fragment(),OnClickListener {
lateinit var  binding : FragmentUserMyProfileBinding
    private var image_status = ""

    var imageProfile: ImageView? = null
    var bottomSheetImageProfile: ImageView? = null
    private lateinit var bottomSheetDialog: BottomSheetDialog

    // Register for ActivityResult to pick the image
    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
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
        binding = FragmentUserMyProfileBinding.inflate(LayoutInflater.from(requireActivity()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rlUploadButton.setOnClickListener(this)

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


/*
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

 */

    private fun profileImageChooser() {
        ImagePicker.with(this)
            .crop() // Crop image (Optional)
            .compress(1024 * 5) // Compress the image to less than 5 MB
            .maxResultSize(250, 250) // Set max resolution
            .createIntent { intent ->
                pickImageLauncher.launch(intent)
            }
    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rlUploadButton -> {
                bottomYourPicture()
            }}
    }


}