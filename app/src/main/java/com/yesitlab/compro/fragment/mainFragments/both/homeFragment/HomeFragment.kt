package com.yesitlab.compro.fragment.mainFragments.both.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.OnItemClickListener1
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.HomeAdapter
import com.yesitlab.compro.databinding.FragmentHomeBinding
import com.yesitlab.compro.model.HomeModel


class HomeFragment : Fragment() , OnItemClickListener {
lateinit var  binding: FragmentHomeBinding
var homeAdapter: HomeAdapter? = null
    var list : MutableList<HomeModel> = mutableListOf()
    var list1 : MutableList<String> = mutableListOf()
    private val skillsList1 = mutableListOf(
        "Android", "Kotlin", "Java", "Python", "Swift", "React", "Node.js",
        "Flutter", "UI/UX", "Machine Learning", "Data Science", "C++", "SQL",
        "JavaScript", "Cloud Computing", "DevOps", "Blockchain", "Cybersecurity"
    )
    private val skillsList2 = mutableListOf(
        "Android", "Kotlin", "Java"
    )
    private val skillsList3 = mutableListOf(
        "Android"
    )

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
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        backPress()
    }
    private fun backPress(){
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle back press logic here
                requireActivity().finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
    private fun initialize() {
        homeAdapter = HomeAdapter(requireContext(), mutableListOf(),this)
        binding.recyclerViewHomePage.setAdapter(homeAdapter)
        listOfHomeModel()
        homeAdapter!!.updateItems(list)
    }




    private fun listOfHomeModel() {
        list.add(
            HomeModel(
                "Dentist Doctor",
                "Priyanka Chawla","priyanka1028@gmail.com", "5","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.....",skillsList1
        )
        )
        list.add(
            HomeModel(
                "Dentist Doctor",
                "Priyanka Chawla","priyanka1028@gmail.com", "4","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.....",skillsList2
        )
        )
        list.add(
            HomeModel(
                "Dentist Doctor",
                "Priyanka Chawla","priyanka1028@gmail.com", "3","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.....",skillsList3
        )
        )
        list.add(
            HomeModel(
                "Dentist Doctor",
                "Priyanka Chawla","priyanka1028@gmail.com", "2","Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC.....",null
        )
        )
    }

    override fun itemClick(position: Int) {
findNavController().navigate(R.id.anotherUserProfileFragment)
    }


}