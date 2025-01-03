package com.yesitlab.compro.fragment.mainFragments.professional.addResumeFragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.R
import com.yesitlab.compro.databinding.FragmentAddResumeBinding


class AddResumeFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentAddResumeBinding
    private val PICK_FILE_REQUEST_CODE = 1001
    private lateinit var imageViewPDf: ImageView
    private lateinit var imageCancel: ImageView
    private lateinit var clImage: ConstraintLayout
    private var selectedFileUri: Uri? = null
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
        binding = FragmentAddResumeBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textNextButton.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)


        binding.clUploadResume.setOnClickListener {
            openFilePicker()
        }


        binding.imageCancel.setOnClickListener {
            removeSelectedFile()
        }

        updateImageLayoutVisibility()

    }

    private fun updateImageLayoutVisibility() {
        if (selectedFileUri != null) {
            binding.clImage.visibility = View.VISIBLE
            binding.imageCancel.visibility = View.VISIBLE // Show the cancel button
        } else {
            binding.clImage.visibility = View.GONE
        }
    }

    private fun openFilePicker() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "*/*"
//        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST_CODE)
//
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // Allow only specific MIME types for documents
        intent.type = "application/*"
        intent.putExtra(
            Intent.EXTRA_MIME_TYPES,
            arrayOf(
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            )
        )
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE_REQUEST_CODE)


    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {


            data?.data?.let { uri ->
                val mimeType = context?.contentResolver?.getType(uri)
                if (isValidMimeType(mimeType)) {
                    selectedFileUri = uri
                    binding.imageViewPDf.setImageResource(R.drawable.ic_file_placeholder) // Use a generic icon for documents
                    updateImageLayoutVisibility()
                } else {
                    // Show an error message if the file is not a valid document
                    showToast("Please select a valid PDF or Word document.")
                }
            }
        }

    }

    private fun isValidMimeType(mimeType: String?): Boolean {
        return mimeType == "application/pdf" ||
                mimeType == "application/msword" ||
                mimeType == "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    }

    private fun removeSelectedFile() {
        selectedFileUri = null
        binding.imageViewPDf.setImageResource(R.drawable.ic_file_placeholder) // Reset to default image
        //imageCancel.visibility = View.GONE // Hide the cancel button
        updateImageLayoutVisibility()
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }




    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.textNextButton->{
             findNavController().navigate(R.id.createProfileFragment)
           }
           R.id.textSkipForNow->{
               findNavController().navigate(R.id.addCertificationFragment)
           }
           R.id.textBackButton->{
               findNavController().navigate(R.id.addCertificationFragment)
           }

       }
    }


}