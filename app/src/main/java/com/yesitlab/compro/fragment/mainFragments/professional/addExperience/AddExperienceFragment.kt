package com.yesitlab.compro.fragment.mainFragments.professional.addExperience

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.ExperienceAdapter
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentAddExperienceBinding
import com.yesitlab.compro.model.AddExperienceModel

class AddExperienceFragment : Fragment(), OnItemClickListener, OnClickListener {

    private lateinit var binding: FragmentAddExperienceBinding
    private lateinit var experienceAdapter: ExperienceAdapter
    private var list: MutableList<AddExperienceModel> = mutableListOf() // Mutable list to allow adding items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddExperienceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageAddExperience.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        initialize()  // Call the initialization method correctly
    }

    private fun initialize() {
        experienceAdapter = ExperienceAdapter(list, requireActivity(), this)
        binding.recyclerViewExp.adapter = experienceAdapter
        experienceList()
        experienceAdapter.updateData(list)
    }

    private fun bottomAddExperience() {


        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_add_experience)
        bottomSheetDialog.show()

        val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton)!!
        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!


        btnSubmit.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
    }


    private fun bottomEditExperience(position: Int) {
        var bottomSheetDialog: BottomSheetDialog? = null
        bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
        bottomSheetDialog.setContentView(R.layout.bottom_edit_experience)
        bottomSheetDialog.show()

        val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton)!!
        val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!
        btnSubmit.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
        imageCross.setOnClickListener {
            bottomSheetDialog.dismiss()

        }
    }

    private fun bottomSelect(position: Int) {
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
            ErrorMsgBox.alertDeleteEdit(requireContext(),"Are you sure you want to delete this experience detail?")
        }
    }


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

    override fun itemClick(position: Int) {
        // Handle item click
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
}
