package com.yesitlab.compro.fragment.mainFragments.professional.addSkillsFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.yesitlab.compro.R
import com.yesitlab.compro.databinding.FragmentAddSkillsBinding


class AddSkillsFragment : Fragment() ,OnClickListener {
lateinit var  binding: FragmentAddSkillsBinding
    private lateinit var chipGroup: ChipGroup
    private lateinit var searchEditText: EditText

    private val skillsList = mutableListOf(
        "Android", "Kotlin", "Java", "Python", "Swift", "React", "Node.js",
        "Flutter", "UI/UX", "Machine Learning", "Data Science", "C++", "SQL",
        "JavaScript", "Cloud Computing", "DevOps", "Blockchain", "Cybersecurity","Others"
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
        binding = FragmentAddSkillsBinding.inflate(LayoutInflater.from(requireActivity()),container,false)
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
                    Toast.makeText(requireContext(), "You can only select up to 15 skills", Toast.LENGTH_SHORT).show()
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
    private fun filterChips(query: String) {
        val filteredList = skillsList.filter { it.contains(query, ignoreCase = true) }
        initializeChips(filteredList)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){

            R.id.textNextButton->{
                findNavController().navigate(R.id.addCertificationFragment)
            }
            R.id.textSkipForNow->{
                findNavController().navigate(R.id.addCertificationFragment)
            }    R.id.textBackButton->{
                findNavController().navigate(R.id.portfolioFragment)
            }

        }
    }

}