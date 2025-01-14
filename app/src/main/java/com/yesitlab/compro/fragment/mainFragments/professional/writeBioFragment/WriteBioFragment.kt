package com.yesitlab.compro.fragment.mainFragments.professional.writeBioFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.NetworkResult
import com.google.gson.JsonObject
import com.yesitlab.compro.BaseApplication
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.R
import com.yesitlab.compro.base.BaseUrl.Companion.check_online
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.databinding.FragmentWriteBioBinding
import com.yesitlab.compro.viewmodel.LocationViewModel
import com.yesitlab.compro.viewmodel.OverviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WriteBioFragment : Fragment() ,OnClickListener{
lateinit var  binding : FragmentWriteBioBinding
    private lateinit var viewModel : OverviewViewModel
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
        binding = FragmentWriteBioBinding.inflate(LayoutInflater.from(requireActivity()),container,false)
        viewModel = ViewModelProvider(this)[OverviewViewModel::class.java]

        commonUtils = CommonUtils(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textNextButton.setOnClickListener(this)
        binding.textSkipForNow.setOnClickListener(this)
        binding.textBackButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.textNextButton->{

                if (binding.etBio.text.toString().trim().isNotEmpty()){
                    if (BaseApplication.isOnline(requireContext())){
                        lifecycleScope.launch {
                            apiAddOverView()
                        }

                    }else{
                        Toast.makeText(requireContext(),check_online,Toast.LENGTH_SHORT).show()
                    }


                }else{
                    binding.etBio.error = "Overview not be empty."
                }


            }
            R.id.textSkipForNow->{
                findNavController().navigate(R.id.previewProfileFragment)
            }
            R.id.textBackButton->{
                findNavController().navigate(R.id.createProfileFragment)
            }

        }
    }

    suspend fun apiAddOverView() {
      val overview : String = binding.etBio.text.trim().toString()
        var jsonObject =JsonObject()

        jsonObject.addProperty("user_id", commonUtils.getUserId())
        jsonObject.addProperty("overviewfield",overview)

        viewModel.apiAddOverview(
            jsonObject
        ) {
            when (it) {
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()


                    it.data?.let { it1 -> LoadingUtils.showSuccessDialog(requireContext(), it1) }
                    findNavController().navigate(R.id.previewProfileFragment)

                }

                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(), it.message.toString())

                }

                is NetworkResult.Loading -> TODO()

            }


    }


}
}