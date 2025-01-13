package com.yesitlab.compro.fragment.mainFragments.professional.previewProfileFragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.ChipGroup
import com.yesitlab.compro.Click
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.OnItemClickListener1
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.CertificatePreviewAdapter
import com.yesitlab.compro.adapter.EducationPreviewAdapter
import com.yesitlab.compro.adapter.ExperiencePreviewAdapter
import com.yesitlab.compro.adapter.ImageAdapter
import com.yesitlab.compro.adapter.PortfolioAdapter
import com.yesitlab.compro.adapter.SkillPreviewAdapter
import com.yesitlab.compro.base.BottomSheetHelper
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.base.Path
import com.yesitlab.compro.databinding.FragmentPreviewProfileBinding
import com.yesitlab.compro.model.CertificatePreviewModel
import com.yesitlab.compro.model.EducationPreviewModel
import com.yesitlab.compro.model.ExperiencePreviewModel
import com.yesitlab.compro.model.PortfolioModel
import java.io.File

class PreviewProfileFragment : Fragment(), OnItemClickListener, OnItemClickListener1,
    OnClickListener {
    lateinit var binding: FragmentPreviewProfileBinding
    private var experiencePreviewAdapter: ExperiencePreviewAdapter? = null
    private var experiencePreviewList: MutableList<ExperiencePreviewModel> = mutableListOf()
    private var educationPreviewAdapter: EducationPreviewAdapter? = null
    private var educationPreviewList: MutableList<EducationPreviewModel> = mutableListOf()
    private var skillList: MutableList<String> = mutableListOf()
    private var skillPreviewAdapter: SkillPreviewAdapter? = null
    private var certificatePreviewAdapter: CertificatePreviewAdapter? = null
    private var certificatePreviewList: MutableList<CertificatePreviewModel> = mutableListOf()
    private lateinit var portfolioAdapter: PortfolioAdapter
    private var image_status = ""
    private var image_status1 = ""
    private val portfolioList: MutableList<PortfolioModel> = mutableListOf()
    private var chipGroup: ChipGroup? = null
    private var searchEditText: EditText? = null
    private val REQUEST_CODE_PROFILE_IMAGE = 100
    private val REQUEST_CODE_CERTIFICATE_IMAGE = 101
    private val REQUEST_CODE_Portfolio_IMAGE = 102
    private lateinit var imageAdapter: ImageAdapter
    private var imageUpload1 : ImageView? = null
    private var recyclerViewImages1 : RecyclerView? = null

    private val selectedSkills: MutableList<String> = mutableListOf()
    private val skillsList = mutableListOf(
        "Android", "Kotlin", "Java", "Python", "Swift", "React", "Node.js",
        "Flutter", "UI/UX", "Machine Learning", "Data Science", "C++", "SQL",
        "JavaScript", "Cloud Computing", "DevOps", "Blockchain", "Cybersecurity"
    )

    private val PICK_FILE_REQUEST_CODE = 1001
    private val selectedImages = mutableListOf<Uri>()
    private var selectedFileUri: Uri? = null
    private var selectedCertificateUri: Uri? = null
    var imageProfile: ImageView? = null
    private var bottomSheetImageProfile: ImageView? = null
    private var bottomSheetImageCertificate: ImageView? = null
    private var bottomSheetImagePortfolio: ImageView? = null

    var clImage: ConstraintLayout? = null
    var clImage1: ConstraintLayout? = null
    var imageCancel: ImageView? = null
    var imageCancel1: ImageView? = null
    var imageViewPDf: ImageView? = null


    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGalleryForImages()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
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
                    if (selectedImages.size < 1) {
                        selectedImages.add(uri)
                        visiblilityCheck()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "You can only select up to 1 pictures.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                updateImageAdapter()
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
        binding = FragmentPreviewProfileBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editProfile.setOnClickListener(this)
        binding.editSkill.setOnClickListener(this)
        binding.imageEditOverview.setOnClickListener(this)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
        // Access the included layout first
        val includedLocationView = binding.includedLocationLayout

        // Then, find the views inside the included layout
        includedLocationView.imageLocationEdit.setOnClickListener(this)
        binding.editResume.setOnClickListener(this)
       // binding.editPortfolio.setOnClickListener(this)
        //  var bottomSheetImageProfile: ImageView? = null


        // Initialize the bottomSheetImageProfile with a valid ImageView reference
        bottomSheetImageProfile = binding.profileImage

        adaptersInitialize()


    }

    private fun adaptersInitialize() {
        // experience
        experiencePreviewAdapter = ExperiencePreviewAdapter(requireContext(), mutableListOf(), this,true)
        binding.recyclerViewExperience.setAdapter(experiencePreviewAdapter)
        experiencePreview()
        experiencePreviewAdapter!!.updateItem(experiencePreviewList)

        // education
//        educationPreviewAdapter = EducationPreviewAdapter(requireContext(), mutableListOf(), this,true)
//        binding.recyclerViewEducation.setAdapter(educationPreviewAdapter)
//        educationPreview()
//        educationPreviewAdapter!!.updateItem(educationPreviewList)

        // Skill

        skillPreviewAdapter = SkillPreviewAdapter(skillList)
        binding.recyclerViewSkill.setAdapter(skillPreviewAdapter)
        skillPreviewList()


//        certificatePreviewAdapter =
//            CertificatePreviewAdapter(requireContext(), mutableListOf(), this,true)
//        binding.recyclerViewCertificate.setAdapter(certificatePreviewAdapter)
//        certificateList()
//        certificatePreviewAdapter?.updateItem(certificatePreviewList)



        portfolioAdapter = PortfolioAdapter(requireActivity(), mutableListOf(), this,true)
        binding.recyclerViewPortfolio.adapter = portfolioAdapter
        loadPortfolioItems()
        portfolioAdapter.updateItem(portfolioList)


    }

    private fun loadPortfolioItems() {
        repeat(6) {
            portfolioList.add(PortfolioModel(R.drawable.image_portfolio, "Graphic Design"))
        }
    }

    private fun certificateList() {
        repeat(5) {
            certificatePreviewList.add(
                CertificatePreviewModel(
                    "oxford software institute",
                    "123456789110",
                    "nnnddd",
                    "11-11-2011",
                    "12-12-2012",
                    R.drawable.certificate_image
                )
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun skillPreviewList() {
        skillList.add("c")
        skillList.add("c++")
        skillList.add("java")
        skillPreviewAdapter?.notifyDataSetChanged()
    }

    private fun educationPreview() {
        repeat(6) {
            educationPreviewList.add(
                EducationPreviewModel(
                    "Savitri Public School",
                    "MCA",
                    "IT",
                    "India",
                    "2024-08-22",
                    "2022-09-22"
                )
            )
        }
    }

    private fun experiencePreview() {
        repeat(6) {
            experiencePreviewList.add(
                ExperiencePreviewModel(
                    "head field solution pvt",
                    "Noida",
                    "front end developer",
                    "2019-05-29",
                    "2024-08-22",
                    "India"
                )
            )
        }
    }

    private fun bottomYourPicture() {

        BottomSheetHelper.showBottomSheet(requireContext(), binding.profileImage) {
            profileImageChooser()
        }
    }


    private fun profileImageChooser() {
        ImagePicker.with(this)
            .crop() // Crop image (Optional), Check Customization for more options
            .compress(1024 * 5) // Final image size will be less than 5 MB (Optional)
            .maxResultSize(
                250,
                250
            ) // Final image resolution will be less than 1080 x 1080 (Optional)
            .start(REQUEST_CODE_PROFILE_IMAGE)
    }

    private fun chooseCertificateImage() {
        ImagePicker.with(this)
            .crop() // Crop image
            .compress(1024 * 5) // Compress image
            .maxResultSize(250, 250) // Set max image size
            .start(REQUEST_CODE_CERTIFICATE_IMAGE) // Use unique request code
    }


    private fun updateImageLayoutVisibility() {
        if (selectedFileUri != null) {
            clImage?.visibility = View.VISIBLE
            imageCancel?.visibility = View.VISIBLE // Show the cancel button
        } else {
            clImage?.visibility = View.GONE
        }
    }

    private fun openFilePicker() {

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

        if (requestCode == REQUEST_CODE_PROFILE_IMAGE) {

            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let { uri ->
                    val file = File(Path.getPath(requireContext(), uri))

                    // Load image into Fragment's ImageView
                    Glide.with(this)
                        .load(uri)
                        .error(R.drawable.ic_profile_icon)
                        .placeholder(R.drawable.ic_profile_icon)
                        .into(binding.profileImage)


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

        else if (requestCode == REQUEST_CODE_CERTIFICATE_IMAGE) {


            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let { uri ->
                  //  val file = File(Path.getPath(requireContext(), uri))

                    selectedCertificateUri = uri
                    // Load image into BottomSheetDialog's ImageView if available
                    bottomSheetImageCertificate?.let {
                        Glide.with(this)
                            .load(selectedCertificateUri)
                            .error(R.drawable.ic_profile_icon)
                            .placeholder(R.drawable.ic_profile_icon)
                            .into(it)
                    }
                    updateVisibilityCertificate()
                    image_status1 = "1"
                }
            }
        }

        else if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val mimeType = context?.contentResolver?.getType(uri)
                if (isValidMimeType(mimeType)) {
                    selectedFileUri = uri
                    imageViewPDf?.setImageResource(R.drawable.ic_file_placeholder) // Use a generic icon for documents
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
        imageViewPDf?.setImageResource(R.drawable.ic_file_placeholder) // Reset to default image
        //imageCancel.visibility = View.GONE // Hide the cancel button
        updateImageLayoutVisibility()
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun bottomEditResume(context: Context) {
        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_edit_resume)
        bottomSheetDialog.show()
        val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton)!!
        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!
        imageViewPDf = bottomSheetDialog.findViewById(R.id.imageViewPDf)!!
        val clUploadResume: ConstraintLayout = bottomSheetDialog.findViewById(R.id.clUploadResume)!!
        clImage = bottomSheetDialog.findViewById(R.id.clImage)!!
        imageCancel = bottomSheetDialog.findViewById(R.id.imageCancel)!!


        btnSubmit.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
        clUploadResume.setOnClickListener {
            openFilePicker()

        }
        imageCancel!!.setOnClickListener {
            removeSelectedFile()

        }
        updateImageLayoutVisibility()
    }


    private fun bottomEditCertificate() {
        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_edit_certification)
        bottomSheetDialog.show()
        val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton1)!!
        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross1)!!
        bottomSheetImageCertificate =
            bottomSheetDialog.findViewById(R.id.imageCertification)!!
        val clUploadCertificate: ConstraintLayout =
            bottomSheetDialog.findViewById(R.id.clUploadCertificate)!!
        clImage1 = bottomSheetDialog.findViewById(R.id.clImage1)!!
        imageCancel1 = bottomSheetDialog.findViewById(R.id.imageCancel1)!!


        btnSubmit.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        clUploadCertificate.setOnClickListener {
            chooseCertificateImage()
        }

        imageCancel1!!.setOnClickListener {
            removeSelectedCertificate()
        }

        updateVisibilityCertificate()
    }


    private fun updateVisibilityCertificate() {
        if (selectedCertificateUri != null) {
            clImage1?.visibility = View.VISIBLE
        } else {

            clImage1?.visibility = View.GONE
        }
    }

    private fun removeSelectedCertificate() {
        selectedCertificateUri = null
        bottomSheetImageCertificate?.setImageResource(R.drawable.certificate_image)
        updateVisibilityCertificate()

    }


    private fun bottomEditPortfolio() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_edit_portfolio)
        bottomSheetDialog.show()

        val btnSubmit: TextView? = bottomSheetDialog.findViewById(R.id.textSubmitButton)
        val imageCross: ImageView? = bottomSheetDialog.findViewById(R.id.imageCross)
        val etProjectTitle: EditText = bottomSheetDialog.findViewById(R.id.etProjectTitle)!!
        val etYourRole: EditText = bottomSheetDialog.findViewById(R.id.etYourRole)!!
        val etProjectDescription: EditText = bottomSheetDialog.findViewById(R.id.etProjectDescription)!!
        val tvProjectTitleCharCount: TextView = bottomSheetDialog.findViewById(R.id.tvProjectTitleCharCount)!!
        val tvProjectDescriptionCharCount: TextView = bottomSheetDialog.findViewById(R.id.tvProjectDescriptionCharCount)!!
        val tvYourRoleCharCount: TextView = bottomSheetDialog.findViewById(R.id.tvYourRoleCharCount)!!
        recyclerViewImages1 = bottomSheetDialog.findViewById(R.id.recyclerViewImages1)
        imageUpload1 = bottomSheetDialog.findViewById(R.id.imageUpload1)

        // Set up character count for all EditTexts
        setupCharacterCountListener(etProjectTitle, tvProjectTitleCharCount, 70)
        setupCharacterCountListener(etYourRole, tvYourRoleCharCount, 100)
        setupCharacterCountListener(etProjectDescription, tvProjectDescriptionCharCount, 600)

        imageAdapter = ImageAdapter(
            onImageDelete = { uri ->
                selectedImages.remove(uri)
                updateImageAdapter()
                visiblilityCheck()
            }
        )

        recyclerViewImages1?.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerViewImages1?.adapter = imageAdapter



        imageUpload1?.setOnClickListener { chooseImage() }

        visiblilityCheck()


        btnSubmit?.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Total pictures uploaded: ${selectedImages.size}",
                Toast.LENGTH_SHORT
            ).show()


            bottomSheetDialog.dismiss()
        }

        imageCross?.setOnClickListener { bottomSheetDialog.dismiss() }
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
    private fun setupCharacterCountListener(editText: EditText, textView: TextView, maxLength: Int) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val remainingChars = maxLength - (s?.length ?: 0)
                textView.text = "$remainingChars characters left"
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateImageAdapter() {
        imageAdapter.updateImages(selectedImages)
    }


    private fun  visiblilityCheck(){
        when(selectedImages.size){
            0->{
                imageUpload1?.visibility = View.VISIBLE
                recyclerViewImages1?.visibility = View.GONE

            }
            else->{

                imageUpload1?.visibility = View.GONE
                recyclerViewImages1?.visibility = View.VISIBLE
            }

        }
    }




    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.editProfile -> {

                BottomSheetHelper.bottomSelect(
                    position = 0,
                    context = requireActivity(),
                    onEditClicked = {
                        // Custom action for edit
                        bottomYourPicture()
                    },
                    onDeleteClicked = {
                        // Custom action for delete
                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this picture detail?",
                            object : Click {
                                override fun confirm() {



                                }

                            })
                    })

            }

            R.id.imageLocationEdit -> {
                BottomSheetHelper.bottomSelect(
                    position = 0,
                    context = requireActivity(),
                    onEditClicked = {
                        // Custom action for edit
                        BottomSheetHelper.bottomEditLocation(requireContext())
                    },
                    onDeleteClicked = {
                        // Custom action for delete
                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this location detail?",
                            object : Click {
                                override fun confirm() {



                                }

                            })
                    })

            }

            R.id.imageEditOverview -> {
                BottomSheetHelper.bottomSelect(
                    position = 0,
                    context = requireActivity(),
                    onEditClicked = {
                        // Custom action for edit
                        BottomSheetHelper.bottomEditOverview(requireContext())
                    },
                    onDeleteClicked = {
                        // Custom action for delete
                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this overview detail?",
                            object : Click {
                                override fun confirm() {



                                }

                            })
                    })

            }

            R.id.editSkill -> {

                BottomSheetHelper.bottomSelect(
                    position = 0,
                    context = requireActivity(),
                    onEditClicked = {
                        // Custom action for edit
                        BottomSheetHelper.bottomEditSkill(
                            requireContext(),
                            skillsList,
                            chipGroup,
                            searchEditText,
                            selectedSkills
                        )
                    },
                    onDeleteClicked = {
                        // Custom action for delete
                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this skill detail?",
                            object : Click {
                                override fun confirm() {



                                }

                            })
                    })


            }

            R.id.editResume -> {
                BottomSheetHelper.bottomSelect(
                    position = 0,
                    context = requireActivity(),
                    onEditClicked = {
                        // Custom action for edit
                        bottomEditResume(requireContext())
                    },
                    onDeleteClicked = {
                        // Custom action for delete
                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this resume detail?",
                            object : Click {
                                override fun confirm() {



                                }

                            })
                    })

            }
//            R.id.editPortfolio -> {
//                BottomSheetHelper.bottomSelect(
//                    position = 0,
//                    context = requireActivity(),
//                    onEditClicked = {
//                        // Custom action for edit
//                        bottomEditPortfolio()
//                    },
//                    onDeleteClicked = {
//                        // Custom action for delete
//                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this portfolio detail?")
//                    })
//
//            }
            R.id.textNextButton -> {
               findNavController().navigate(R.id.homeFragment)
            }
            R.id.textSkipForNow -> {
               findNavController().navigate(R.id.homeFragment)
            }
            R.id.textBackButton -> {
               findNavController().navigate(R.id.writeBioFragment)
            }
        }
    }

    override fun itemClick(position: Int, source: String) {
        when (source) {
            "education" -> {
                BottomSheetHelper.bottomSelect(
                    position = 0,
                    context = requireActivity(),
                    onEditClicked = {
                        // Custom action for edit
                        BottomSheetHelper.bottomEditEducation(requireContext(), position)
                    },
                    onDeleteClicked = {
                        // Custom action for delete
                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this education detail?",
                            object : Click {
                                override fun confirm() {



                                }

                            })
                    })

            }

            "experience" -> {
                BottomSheetHelper.bottomSelect(
                    position = 0,
                    context = requireActivity(),
                    onEditClicked = {
                        // Custom action for edit
                        BottomSheetHelper.bottomEditExperience(requireContext(), position)
                    },
                    onDeleteClicked = {
                        // Custom action for delete
                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this experience detail?",
                            object : Click {
                                override fun confirm() {



                                }

                            })
                    })


            }

            "certificate" -> {
                BottomSheetHelper.bottomSelect(
                    position = 0,
                    context = requireActivity(),
                    onEditClicked = {
                        // Custom action for edit
                        bottomEditCertificate()
                    },
                    onDeleteClicked = {
                        // Custom action for delete
                        ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this certificate detail?",
                            object : Click {
                                override fun confirm() {



                                }

                            })
                    })

            }
        }
    }


    override fun itemClick(position: Int) {
        BottomSheetHelper.bottomSelect(
            position = 0,
            context = requireActivity(),
            onEditClicked = {
                // Custom action for edit
                bottomEditPortfolio()
            },
            onDeleteClicked = {
                // Custom action for delete
                ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this portfolio detail?",
                    object : Click {
                        override fun confirm() {



                        }

                    })
            })

    }


}
