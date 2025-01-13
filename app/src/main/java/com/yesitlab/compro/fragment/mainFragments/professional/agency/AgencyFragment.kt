package com.yesitlab.compro.fragment.mainFragments.professional.agency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.OnItemClickListener1
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.AddAgencyAdapter
import com.yesitlab.compro.databinding.FragmentAgencyBinding
import com.yesitlab.compro.model.AgencyModel


class AgencyFragment : Fragment() {
    private  var _binding : FragmentAgencyBinding? = null
    private  val binding get() = _binding!!
var  maxItems = 5;
    private lateinit var adapter: AddAgencyAdapter
    private val tasks = mutableListOf<AgencyModel>()

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
        _binding = FragmentAgencyBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageAddIcon.setOnClickListener {
            if (tasks.size < maxItems) {
                val newItem = AgencyModel()
                tasks.add(newItem)

                adapter.notifyItemInserted(tasks.size - 1)
                binding.rcvAddAgency.smoothScrollToPosition(tasks.size - 1)
            } else {

                Toast.makeText(context, "Maximum limit of $maxItems items reached.", Toast.LENGTH_SHORT).show()
            }
        }

        setupRecyclerView()
    }
    private fun setupRecyclerView() {

        adapter = AddAgencyAdapter(requireContext(), tasks, object : OnItemClickListener1 {
            override fun itemClick(position: Int, action: String) {
                when (action) {
                    "nextPage" ->{
                        findNavController().navigate(R.id.addExperienceFragment)
                    }


                }
            }
        })


        binding.rcvAddAgency.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}