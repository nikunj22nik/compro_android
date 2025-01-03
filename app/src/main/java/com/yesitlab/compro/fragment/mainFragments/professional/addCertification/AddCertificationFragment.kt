package com.yesitlab.compro.fragment.mainFragments.professional.addCertification

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.ImageAdapter
import com.yesitlab.compro.databinding.FragmentAddCertificationBinding


class AddCertificationFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentAddCertificationBinding
    private lateinit var imageAdapter: ImageAdapter
    private val selectedImages = mutableListOf<Uri>()


    private val pickImageLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.clipData?.let {
                    val count = it.itemCount
                    for (i in 0 until count) {
                        if (selectedImages.size < 1) {
                            val imageUri = it.getItemAt(i).uri
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
            }

            R.id.textSkipForNow -> {
                findNavController().navigate(R.id.addResumeFragment)
            }

            R.id.textBackButton -> {
                findNavController().navigate(R.id.addSkillsFragment)
            }
        }
    }
}