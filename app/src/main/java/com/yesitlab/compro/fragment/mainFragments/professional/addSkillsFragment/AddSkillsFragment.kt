package com.yesitlab.compro.fragment.mainFragments.professional.addSkillsFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.NetworkResult
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.R
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.databinding.FragmentAddSkillsBinding
import com.yesitlab.compro.viewmodel.ResetPasswordViewModel
import com.yesitlab.compro.viewmodel.SkillViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddSkillsFragment : Fragment(), OnClickListener {
    lateinit var binding: FragmentAddSkillsBinding
    lateinit var viewModel: SkillViewModel

    lateinit var commonUtils: CommonUtils
    private lateinit var chipGroup: ChipGroup
    private lateinit var chipGroup1: ChipGroup
    private lateinit var searchEditText: EditText
    val chipList = mutableListOf<String>()
    private val skillsList = mutableListOf(
        "Android", "Kotlin", "Java", "Python", "Swift", "React", "Node.js",
        "Flutter", "UI/UX", "Machine Learning", "Data Science", "C++", "SQL",
        "JavaScript", "Cloud Computing", "DevOps", "Blockchain", "Cybersecurity", "Others"
    )

    private val selectedSkills = mutableSetOf<String>()
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
        binding = FragmentAddSkillsBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )
        viewModel = ViewModelProvider(this)[SkillViewModel::class.java]

        commonUtils = CommonUtils(requireContext())


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
        chipGroup = binding.chipGroupSkills




        searchEditText = binding.searchBar
        initializeChips(skillsList)
        chipGroup1 = binding.chipGroup1




        binding.buttonAdd.setOnClickListener {
            val text = searchEditText.text.toString().trim()
            if (text.isNotEmpty() && !chipList.contains(text)) {
                addChipToGroup(text, chipGroup1, chipList)
                searchEditText.text.clear()
            } else {
                Toast.makeText(requireContext(), "Enter unique text", Toast.LENGTH_SHORT).show()
            }
        }

        // Add Chip on Enter Key
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                val text = searchEditText.text.toString().trim()
                if (text.isNotEmpty() && !chipList.contains(text)) {
                    addChipToGroup(text, chipGroup1, chipList)
                    searchEditText.text.clear()
                } else {
                    Toast.makeText(requireContext(), "Enter unique text", Toast.LENGTH_SHORT).show()
                }
                true
            } else {
                false
            }
        }


        // Setup search bar functionality
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterChips(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Function to initialize chips with skills
    private fun initializeChips(skills: List<String>) {
        chipGroup.removeAllViews()
        for (skill in skills) {
            val chip = createChip(skill, selectedSkills.contains(skill))


            chipGroup.addView(chip)
        }
    }


    // Function to create a chip for each skill

    private fun createChip(skill: String, isSelected: Boolean): Chip {
        val chip = Chip(requireContext())
        chip.text = skill
        chip.isCheckable = false
        chip.isCheckedIconVisible = false // Hide the default check icon
        // chip.setChipIconResource(if (isSelected) R.drawable.ic_plus else R.drawable.ic_plus)
        chip.setChipIconResource(R.drawable.ic_plus)
        // chip.isChecked = isSelected

        // Handle chip click events
        chip.setOnClickListener {
            //  if (chip.isChecked) {
            // Select the chip
            if (selectedSkills.size < 15) {
                //  chip.setChipIconResource(R.drawable.ic_plus) // Set check icon when selected
                selectedSkills.add(skill)

                addChipToGroup(skill, binding.chipGroup1, chipList)

            } else {
                // Uncheck the chip and show a message if limit exceeded
                //  chip.isChecked = false
                Toast.makeText(
                    requireContext(),
                    "You can only select up to 15 skills",
                    Toast.LENGTH_SHORT
                ).show()
            }
//            }
//        else {
//                // Unselect the chip
////                chip.setChipIconResource(R.drawable.ic_plus) // Set plus icon when unselected
////                selectedSkills.remove(skill)
//
//                if (selectedSkills.size < 15) {
//                 //   chip.setChipIconResource(R.drawable.ic_plus) // Set check icon when selected
//                    selectedSkills.add(skill)
//
//                    addChipToGroup(skill, binding.chipGroup1, chipList)
//
//                } else {
//                    // Uncheck the chip and show a message if limit exceeded
//                  //  chip.isChecked = false
//                    Toast.makeText(requireContext(), "You can only select up to 15 skills", Toast.LENGTH_SHORT).show()
//                }
//            }
        }
        return chip
    }


    /*
        private fun createChip(skill: String, isSelected: Boolean): Chip {
            val chip = Chip(requireContext())
            chip.text = skill
            chip.isCheckable = true
            chip.isCheckedIconVisible = false // Hide the default check icon
            chip.setChipIconResource(if (isSelected) R.drawable.ic_check else R.drawable.ic_plus)
            chip.isChecked = isSelected

            // Pre-add the skill to chipList if already selected
            if (isSelected && !chipList.contains(skill)) {
                chipList.add(skill)
            }

            // Handle chip click events
            chip.setOnClickListener {
                if (chip.isChecked) {
                    // Select the chip
                    if (selectedSkills.size < 15) {
                        chip.setChipIconResource(R.drawable.ic_check) // Set check icon when selected
                        selectedSkills.add(skill)

                        // Add to chipList if not already present
                        if (!chipList.contains(skill)) {
                            chipList.add(skill)
                        }
                    } else {
                        // Uncheck the chip and show a message if limit exceeded
                        chip.isChecked = false
                        Toast.makeText(
                            requireContext(),
                            "You can only select up to 15 skills",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Unselect the chip
                    chip.setChipIconResource(R.drawable.ic_plus) // Set plus icon when unselected
                    selectedSkills.remove(skill)

                    // Remove from chipList
                    chipList.remove(skill)
                }
            }
            return chip
        }

     */


    // Function to filter chips based on search query
    private fun filterChips(query: String) {
        val filteredList = skillsList.filter { it.contains(query, ignoreCase = true) }
        // Update the chip visibility based on the filtered list and query
        if (query.isEmpty() || filteredList.isNotEmpty()) {
            binding.buttonAdd.visibility =
                View.GONE // Hide button if query is empty or matches found
        } else {
            binding.buttonAdd.visibility = View.VISIBLE // Show button if no matches found
        }

        initializeChips(filteredList)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.textNextButton -> {
                if (chipList != null) {
                    lifecycleScope.launch {
                        apiAddSkill(chipList)
                    }

                }


            }

            R.id.textSkipForNow -> {
                findNavController().navigate(R.id.addCertificationFragment)
            }

            R.id.textBackButton -> {
                findNavController().navigate(R.id.portfolioFragment)
            }

        }
    }

    private suspend fun apiAddSkill(chipList: MutableList<String>) {

        // Create a JsonObject for the main JSON structure
        val jsonObject = JsonObject()
        jsonObject.addProperty("user_id", commonUtils.getUserId().toString())
        jsonObject.addProperty("profile_id", "2")

        val jsonArray = JsonArray()
        for (chip in chipList) {
            jsonArray.add(chip)
        }

        // Add the ingredients array to the main JSON object
        jsonObject.add("skill_user", jsonArray)

        Log.d("json object ", "******$jsonObject")

        LoadingUtils.showDialog(requireContext(), true)

        viewModel.apiAddSkill(jsonObject) {
            when (it) {
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()

                    it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }

                    findNavController().navigate(R.id.addCertificationFragment)

                }

                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(), it.message.toString())

                }

                is NetworkResult.Loading -> TODO()

            }
        }


    }


    private fun addChipToGroup(text: String, chipGroup: ChipGroup, chipList: MutableList<String>) {
        val chip = Chip(requireContext())
        chip.text = text
        chip.isCloseIconVisible = true // Adds a close icon to remove the chip
        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
            chipList.remove(text)
        }

        chip.setOnClickListener {
            chip.isSelected = !chip.isSelected
            if (chip.isSelected) {
                chip.setChipBackgroundColorResource(android.R.color.holo_blue_light)
            } else {
                chip.setChipBackgroundColorResource(android.R.color.darker_gray)
            }
        }


        if (chipList.size >= 15) {
            Toast.makeText(
                requireContext(),
                "You can only select up to 15 skills",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            chipGroup.addView(chip)
            chipList.add(text)
        }

    }

}