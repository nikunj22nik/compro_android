package com.yesitlab.compro.fragment.mainFragments.both.homeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.NetworkResult
import com.example.network.apiModel.HomeResponse
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.OnItemClickListener
import com.yesitlab.compro.R
import com.yesitlab.compro.adapter.HomeAdapter
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.databinding.FragmentHomeBinding
import com.yesitlab.compro.model.HomeModel
import com.yesitlab.compro.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() , OnItemClickListener {
lateinit var  binding: FragmentHomeBinding
var homeAdapter: HomeAdapter? = null

    var currentPage : Int = 1;
    var list : MutableList<HomeResponse> = mutableListOf()


    private lateinit var viewModel : HomeViewModel
    private lateinit var commonUtils: CommonUtils

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

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        commonUtils = CommonUtils(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        backPress()
        lifecycleScope.launch {
            homeApi(currentPage)
        }

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                currentPage++
                lifecycleScope.launch{
                    homeApi(currentPage)
                }

            }
        })



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

       // listOfHomeModel()
       // homeAdapter!!.updateItems(list)


    }



/*
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

 */

    override fun itemClick(position: Int) {
        var bundle = Bundle()

        val homeResponse = list[position] // Get the HomeResponse at the clicked position
        bundle.putParcelable("homeResponse", homeResponse) // Put it in the bundle

        // Navigate to AnotherUserProfileFragment and pass the bundle
        findNavController().navigate(R.id.anotherUserProfileFragment, bundle)
//        bundle.putInt(AppConstant.anotherUserPosition,position)
//findNavController().navigate(R.id.anotherUserProfileFragment,bundle)
    }



    suspend fun homeApi(currentPage : Int){
var userID  = commonUtils.getUserId()

        LoadingUtils.showDialog(requireContext(),true)
        viewModel.apitalent(userID.toString(),currentPage){
            when(it){
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()
                    var data = it.data

                    if (data != null) {
                        list.addAll(data)
                        homeAdapter = HomeAdapter(requireContext(), mutableListOf(),this)
                        binding.recyclerViewHomePage.setAdapter(homeAdapter)
                        homeAdapter?.updateItems(list)
                    }

                    Log.d("i'm Here", "succeess")

                    Log.d("dataVipin",data.toString())

                }
                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    Log.d("i'm Here", "failed")
                    LoadingUtils.showErrorDialog(requireContext(),it.message.toString())
                }
                is NetworkResult.Loading -> TODO()

            }
        }

    }


}