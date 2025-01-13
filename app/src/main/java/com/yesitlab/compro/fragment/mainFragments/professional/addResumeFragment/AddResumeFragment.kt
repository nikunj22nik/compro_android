package com.yesitlab.compro.fragment.mainFragments.professional.addResumeFragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.NetworkResult
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.yesitlab.compro.BaseApplication
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.R
import com.yesitlab.compro.base.BaseUrl.Companion.check_online
import com.yesitlab.compro.base.BaseUrl.Companion.check_resume
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.Path.Companion.getPath
import com.yesitlab.compro.base.Path1.Companion.getPath1
import com.yesitlab.compro.databinding.FragmentAddResumeBinding
import com.yesitlab.compro.viewmodel.ApiAddCertificateViewModel
import com.yesitlab.compro.viewmodel.ResumeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


@AndroidEntryPoint
class AddResumeFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentAddResumeBinding
    private val PICK_FILE_REQUEST_CODE = 1001
    private lateinit var imageViewPDf: ImageView
    private lateinit var imageCancel: ImageView
    private lateinit var clImage: ConstraintLayout
    private var selectedFileUri: Uri? = null
    private var file: File? = null
    private lateinit var viewModel: ResumeViewModel

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
        binding = FragmentAddResumeBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )
        viewModel = ViewModelProvider(this)[ResumeViewModel::class.java]

        commonUtils = CommonUtils(requireContext())


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

//                    val mimeType = context?.contentResolver?.getType(uri)
//                Log.d("AddResumeFragment", "MIME Type: $mimeType, URI: $uri")
//                if (isValidMimeType(mimeType)) {

                    file = resolveFile(requireContext(), uri)

                    if (file != null) {
                        Log.d("AddResumeFragment", "File Pathvipin: ${file?.absolutePath}")
                        binding.imageViewPDf.setImageResource(R.drawable.ic_file_placeholder) // Use a generic icon for documents


                        updateImageLayoutVisibility()

                    } else {
                        showToast("Unable to resolve file path.")
                    }
                } else {
                    showToast("Please select a valid PDF or Word document.")
                }
            }
        }
    }

    private fun resolveFile(context: Context, uri: Uri): File? {
        val path = getPath1(context, uri)
        Log.d("AddResumeFragment", "Resolved Path: $path")
        return if (path != null) File(path) else {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val tempFile = File(context.cacheDir, getFileName(context, uri))
                inputStream.use { input ->
                    tempFile.outputStream().use { output ->
                        input?.copyTo(output)
                    }
                }
                tempFile
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun isValidMimeType(mimeType: String?): Boolean {
        return mimeType == "application/pdf" ||
                mimeType == "application/msword" ||
                mimeType == "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    }


    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String {
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        return uri.lastPathSegment ?: "unknown_file"
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


    suspend fun apiAddResume() {
        val userIdReq = commonUtils.getUserId().toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val profilePic: MultipartBody.Part = file.let {
            val requestFile = file!!.asRequestBody("application/pdf".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("resume", file!!.path, requestFile)
        }

        LoadingUtils.showDialog(requireContext(), true)

        viewModel.apiResume(userIdReq,profilePic) {
            when (it) {
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()


                    it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }
                    findNavController().navigate(R.id.createProfileFragment)

                }

                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(), it.message.toString())

                }

                is NetworkResult.Loading -> TODO()

            }
        }
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.textNextButton -> {
//             findNavController().navigate(R.id.createProfileFragment)

                if (selectedFileUri == null || file == null) {
                    showToast(check_resume)
                    return
                }
                if (BaseApplication.isOnline(requireContext())){
                    lifecycleScope.launch {
                        apiAddResume()

                }  }else{
LoadingUtils.showErrorDialog(requireContext(),check_online)
                }

            }

            R.id.textSkipForNow -> {
                findNavController().navigate(R.id.addCertificationFragment)
            }

            R.id.textBackButton -> {
                findNavController().navigate(R.id.addCertificationFragment)
            }

        }
    }


}


