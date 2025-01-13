package com.yesitlab.compro.fragment.mainFragments.professional.addCertification

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.network.NetworkResult
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.ImageAdapter
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.Path.Companion.getPath
import com.yesitlab.compro.databinding.FragmentAddCertificationBinding
import com.yesitlab.compro.viewmodel.ApiAddCertificateViewModel
import com.yesitlab.compro.viewmodel.ApiEducationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class AddCertificationFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentAddCertificationBinding
    private lateinit var imageAdapter: ImageAdapter
    private val selectedImages = mutableListOf<Uri>()
    private var tempUri: Uri? = null
    private var file: File? = null
    private lateinit var viewModel : ApiAddCertificateViewModel

    private lateinit var commonUtils: CommonUtils

    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.clipData?.let {
                    val count = it.itemCount
                    for (i in 0 until count) {
                        if (selectedImages.size < 1) {
                            val imageUri = it.getItemAt(i).uri



                            file = getPath(requireContext(), imageUri)?.let { File(it) }

                            Log.d("vipinFile", file.toString())

                            selectedImages.add(imageUri)
                            visiblilityCheck()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "You can only select up to 1 pictures.",
                                Toast.LENGTH_LONG
                            ).show()
                            break
                        }
                    }
                } ?: result.data?.data?.let { uri ->
                    if (selectedImages.size < 5) {
                        selectedImages.add(uri)
                        file = getPath(requireContext(), uri)?.let { File(it) }

                        Log.d("vipinFile", file.toString())

                        visiblilityCheck()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "You can only select up to 5 pictures.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                updateImageAdapter()
            }
        }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGalleryForImages()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
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
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddCertificationBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )

        viewModel = ViewModelProvider(this)[ApiAddCertificateViewModel::class.java]

        commonUtils = CommonUtils(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageUpload.setOnClickListener(this)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)


        imageAdapter = ImageAdapter(
            onImageDelete = { uri ->
                selectedImages.remove(uri)
                updateImageAdapter()
                visiblilityCheck()
            }
        )

        binding.recyclerViewImages1.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerViewImages1.adapter = imageAdapter



        binding.imageUpload.setOnClickListener { chooseImage() }

        visiblilityCheck()


    }


    private fun chooseImage() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGalleryForImages()
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun openGalleryForImages() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        pickImageLauncher.launch(Intent.createChooser(intent, "Choose Pictures"))
    }

    private fun updateImageAdapter() {
        imageAdapter.updateImages(selectedImages)
    }

    private fun visiblilityCheck() {
        when (selectedImages.size) {
            0 -> {
                binding.imageUpload.visibility = View.VISIBLE
                binding.recyclerViewImages1.visibility = View.GONE
            }
            else -> {
                binding.imageUpload.visibility = View.GONE
                binding.recyclerViewImages1.visibility = View.VISIBLE
            }

        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imageUpload -> {
                chooseImage()

            }

            R.id.textNextButton -> {
                findNavController().navigate(R.id.addResumeFragment)

//                lifecycleScope.launch {
//                    apiAddCertificate()
//                }
            }

            R.id.textSkipForNow -> {
                findNavController().navigate(R.id.addResumeFragment)
            }

            R.id.textBackButton -> {
                findNavController().navigate(R.id.addSkillsFragment)
            }
        }
    }

    suspend fun  apiAddCertificate(){
        val UserIdReq = commonUtils.getUserId().toString()
            .toRequestBody("text/plain".toMediaTypeOrNull())

        val profile_id = 2
      val  proReq = profile_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val certificate_name = binding.etCertificationName.text.trim()
        val cerReq = certificate_name.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val certificate_completion_id = binding.etCertificationCompletionID.text.trim()
        val certificate_completion_idReq = certificate_completion_id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val certificate_url = binding.etCertificationUrl.text.trim()
        val certificate_urlReq = certificate_url.toString().toRequestBody("text/plain".toMediaTypeOrNull())
       val start_date= binding.etStartDate.text.trim()
        val start_dateReq = start_date.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val end_date= binding.etCountry.text.trim()
        val end_dateReq = end_date.toString().toRequestBody("text/plain".toMediaTypeOrNull())
val certificate_prof = 1

        val certificate_profReq =certificate_prof.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val profilePic: MultipartBody.Part = file.let {
            val requestFile = file!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("profile_img", file!!.path, requestFile)
        }
viewModel.apiAddCertificate(UserIdReq,proReq,cerReq,certificate_completion_idReq,certificate_urlReq, start_dateReq,  end_dateReq, certificate_profReq,    profilePic){
    when(it){
        is NetworkResult.Success -> {
            LoadingUtils.hideDialog()


            it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }
            findNavController().navigate(R.id.addResumeFragment)

        }
        is NetworkResult.Error -> {
            LoadingUtils.hideDialog()
            LoadingUtils.showErrorDialog(requireContext(),it.message.toString())

        }
        is NetworkResult.Loading -> TODO()

    }
}
    }

}