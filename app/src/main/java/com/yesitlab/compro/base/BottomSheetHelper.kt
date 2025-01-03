package com.yesitlab.compro.base

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yesitlab.compro.R

class BottomSheetHelper {

    companion object {

         fun bottomSelect(position: Int,context: Context,
                                 onEditClicked: (() -> Unit)? = null,
                                 onDeleteClicked: (() -> Unit)? = null) {
            var bottomSheetDialog: BottomSheetDialog? = null
            bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
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
                onEditClicked?.invoke()

            }
            textDelete.setOnClickListener {
                bottomSheetDialog.dismiss()
                onDeleteClicked?.invoke()
            }
        }

        fun showBottomSheet(context: Context,   profileImage: ImageView, onImageSelected: () -> Unit) {
            val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
            bottomSheetDialog.setContentView(R.layout.bottom_your_photo)
            bottomSheetDialog.show()

            val attachPhoto: TextView? = bottomSheetDialog.findViewById(R.id.textAttachButton)
            val cancel: TextView? = bottomSheetDialog.findViewById(R.id.textCancelButton)
            val imageCross: ImageView? = bottomSheetDialog.findViewById(R.id.imageCross)
            val imageUploadPlusIcon: ImageView? = bottomSheetDialog.findViewById(R.id.imageUploadPlusIcon)
            val bottomSheetImageProfile: ImageView? = bottomSheetDialog.findViewById(R.id.imageProfileIcon) // The ImageView in the bottom sheet

            // Set the drawable from the passed `profileImage` into the bottom sheet's `ImageView`
            bottomSheetImageProfile?.setImageDrawable(profileImage.drawable)

            imageUploadPlusIcon?.setOnClickListener {
                onImageSelected()
                bottomSheetDialog.dismiss()
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


         fun bottomEditExperience(context: Context,position: Int) {
            var bottomSheetDialog: BottomSheetDialog? = null
            bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
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


        fun bottomEditLocation(context: Context) {
            var bottomSheetDialog: BottomSheetDialog? = null
            bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
            bottomSheetDialog.setContentView(R.layout.bottom_edit_location)
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
        fun bottomEditOverview(context: Context) {
            var bottomSheetDialog: BottomSheetDialog? = null
            bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
            bottomSheetDialog.setContentView(R.layout.bottom_edit_overview)
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



        fun bottomEditEducation(context: Context,position: Int) {
            var bottomSheetDialog: BottomSheetDialog? = null
            bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
            bottomSheetDialog.setContentView(R.layout.bottom_edit_education)
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


        fun bottomEditSkill(context: Context,skillsList : MutableList<String>,chipGroup: ChipGroup?,searchEditText: EditText?, selectedSkills : MutableList<String>) {
            var bottomSheetDialog: BottomSheetDialog? = null
            bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
            bottomSheetDialog.setContentView(R.layout.bottom_edit_skill_preview)
            bottomSheetDialog.show()

            val btnSubmit: TextView = bottomSheetDialog.findViewById(R.id.textSubmitButton)!!
            val imageCross: ImageView = bottomSheetDialog.findViewById(R.id.imageCross)!!
            val chipGroup: ChipGroup = bottomSheetDialog.findViewById(R.id.chipGroupSkills)!!
            val searchEditText: EditText = bottomSheetDialog.findViewById(R.id.searchBar)!!

            initializeChips(context,skillsList,selectedSkills,chipGroup)

            // Setup search bar functionality
            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    filterChips(s.toString(),skillsList,context,selectedSkills,chipGroup)
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            btnSubmit.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            imageCross.setOnClickListener {
                bottomSheetDialog.dismiss()
            }


        }
        // Function to initialize chips with skills
        private fun initializeChips(context: Context,skills: List<String>, selectedSkills: MutableList<String>,chipGroup:ChipGroup) {
            chipGroup.removeAllViews()
            for (skill in skills) {
                val chip = createChip(context,skill, selectedSkills.contains(skill),selectedSkills)
                chipGroup.addView(chip)
            }
        }




        // Function to create a chip for each skill
        private fun createChip(context: Context,skill: String, isSelected: Boolean,selectedSkills: MutableList<String>): Chip {
            val chip = Chip(context)
            chip.text = skill
            chip.isCheckable = true
            chip.isCheckedIconVisible = false // Hide the default check icon
            chip.setChipIconResource(if (isSelected) R.drawable.ic_check else R.drawable.ic_plus)
            chip.isChecked = isSelected

            // Handle chip click events
            chip.setOnClickListener {
                if (chip.isChecked) {
                    // Select the chip
                    if (selectedSkills.size < 15) {
                        chip.setChipIconResource(R.drawable.ic_check) // Set check icon when selected
                        selectedSkills.add(skill)
                    } else {
                        // Uncheck the chip and show a message if limit exceeded
                        chip.isChecked = false
                        Toast.makeText(context, "You can only select up to 15 skills", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Unselect the chip
                    chip.setChipIconResource(R.drawable.ic_plus) // Set plus icon when unselected
                    selectedSkills.remove(skill)
                }
            }
            return chip
        }




        // Function to filter chips based on search query
        private fun filterChips(query: String,skillsList : MutableList<String>,context: Context, selectedSkills: MutableList<String>,chipGroup:ChipGroup) {
            val filteredList = skillsList.filter { it.contains(query, ignoreCase = true) }
            initializeChips(context,filteredList,selectedSkills,chipGroup)
        }






    }

}