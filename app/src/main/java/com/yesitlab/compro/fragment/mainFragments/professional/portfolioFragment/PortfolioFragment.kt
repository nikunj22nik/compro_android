package com.yesitlab.compro.fragment.mainFragments.professional.portfolioFragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.ImageAdapter
import com.yesitlab.compro.adapter.PortfolioAdapter
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentPortfolioBinding
import com.yesitlab.compro.model.PortfolioModel

class PortfolioFragment : Fragment(), OnItemClickListener, View.OnClickListener {

    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var portfolioAdapter: PortfolioAdapter
    private lateinit var imageAdapter: ImageAdapter
     private var imageUpload : ImageView? = null
    private var recyclerViewImages : RecyclerView? = null
    private var imageUpload1 : ImageView? = null
    private var recyclerViewImages1 : RecyclerView? = null
    private val list: MutableList<PortfolioModel> = mutableListOf()
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

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openGalleryForImages()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageAddPortfolio.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        initialize()
    }

    private fun initialize() {
        portfolioAdapter = PortfolioAdapter(requireActivity(), listOf(), this,true)
        binding.recyclerViewPortfolio.adapter = portfolioAdapter
        loadPortfolioItems()
        portfolioAdapter.updateItem(list)
    }

    private fun loadPortfolioItems() {
        repeat(6) {
            list.add(PortfolioModel(R.drawable.image_portfolio, "Graphic Design"))
        }
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


    private fun  visiblilityCheck(){
        when(selectedImages.size){
            0->{ imageUpload?.visibility = View.VISIBLE
                recyclerViewImages?.visibility = View.GONE

                imageUpload1?.visibility = View.VISIBLE
                recyclerViewImages1?.visibility = View.GONE

            }
            else->{
                imageUpload?.visibility = View.GONE
                recyclerViewImages?.visibility = View.VISIBLE
                imageUpload1?.visibility = View.GONE
                recyclerViewImages1?.visibility = View.VISIBLE
            }

        }
    }

    private fun bottomSheetPortfolio() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_add_portfolio)
        bottomSheetDialog.show()

        val btnSubmit: TextView? = bottomSheetDialog.findViewById(R.id.textSubmitButton)
        val imageCross: ImageView? = bottomSheetDialog.findViewById(R.id.imageCross)
        val etProjectTitle: EditText = bottomSheetDialog.findViewById(R.id.etProjectTitle)!!
        val etYourRole: EditText = bottomSheetDialog.findViewById(R.id.etYourRole)!!
        val etProjectDescription: EditText = bottomSheetDialog.findViewById(R.id.etProjectDescription)!!
        val tvProjectTitleCharCount: TextView = bottomSheetDialog.findViewById(R.id.tvProjectTitleCharCount)!!
        val tvProjectDescriptionCharCount: TextView = bottomSheetDialog.findViewById(R.id.tvProjectDescriptionCharCount)!!
        val tvYourRoleCharCount: TextView = bottomSheetDialog.findViewById(R.id.tvYourRoleCharCount)!!
         recyclerViewImages = bottomSheetDialog.findViewById(R.id.recyclerViewImages)
         imageUpload = bottomSheetDialog.findViewById(R.id.imageUpload)

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

        recyclerViewImages?.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerViewImages?.adapter = imageAdapter



        imageUpload?.setOnClickListener { chooseImage() }

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



    private fun bottomSelect(position: Int) {
        val bottomSheetDialog: BottomSheetDialog?
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
            bottomEditPortfolio(position)
        }
        textDelete.setOnClickListener {
            bottomSheetDialog.dismiss()
            ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this portfolio detail?")
        }
    }

    private fun bottomEditPortfolio(position: Int) {
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

    private fun updateImageAdapter() {
        imageAdapter.updateImages(selectedImages)
    }

    override fun itemClick(position: Int) {
        bottomSelect(position)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageAddPortfolio -> bottomSheetPortfolio()
            R.id.textBackButton -> { findNavController().navigate(R.id.addEducationFragment) }
            R.id.textNextButton -> { findNavController().navigate(R.id.addSkillsFragment) }
            R.id.textSkipForNow -> { findNavController().navigate(R.id.addSkillsFragment) }
        }
    }
}
