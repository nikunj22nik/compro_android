package com.yesitlab.compro.fragment.mainFragments.both.passwordAndSecurity

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.NetworkResult
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.R
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentPasswordAndSecurityBinding
import com.yesitlab.compro.viewmodel.ApiEducationViewModel
import com.yesitlab.compro.viewmodel.ResetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class PasswordAndSecurityFragment : Fragment() {
    lateinit var binding: FragmentPasswordAndSecurityBinding

     lateinit var viewModel : ResetPasswordViewModel

     lateinit var commonUtils: CommonUtils



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
        binding = FragmentPasswordAndSecurityBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )

        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]

        commonUtils = CommonUtils(requireContext())


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eyeHideShow()

        binding.submit.setOnClickListener {
            if(validateInput()){
                lifecycleScope.launch {
                    apiResetPassword(binding.etCurrentPassword,binding.etNewPassword)
                }

            }

        }

    }


    private suspend fun apiResetPassword(currentPassword : EditText,newPassword:EditText) {
        var user_id : String = commonUtils.getUserId().toString()
        var old = currentPassword.text.trim().toString()
        var new = newPassword.text.trim().toString()

        LoadingUtils.showDialog(requireContext(),true)

        viewModel.apiResetPassword(user_id,old,new){
            when(it){
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()

it.data?.let {it1->  LoadingUtils.showSuccessDialog(requireContext(), it1) }

                }
                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(),it.message.toString())

                }
                is NetworkResult.Loading -> TODO()

            }
        }


    }




    private fun eyeHideShow() {
        binding.imgHidePass.setOnClickListener {
            binding.imgShowPass.visibility = View.VISIBLE
            binding.imgHidePass.visibility = View.GONE
            binding.etCurrentPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.etCurrentPassword.setSelection(binding.etCurrentPassword.text.length)
        }
        binding.imgShowPass.setOnClickListener {
            binding.imgShowPass.visibility = View.GONE
            binding.imgHidePass.visibility = View.VISIBLE
            binding.etCurrentPassword.transformationMethod =
                PasswordTransformationMethod.getInstance()
            binding.etCurrentPassword.setSelection(binding.etCurrentPassword.text.length)
        }

        binding.imgHidePass1.setOnClickListener {
            binding.imgShowPass1.visibility = View.VISIBLE
            binding.imgHidePass1.visibility = View.GONE
            binding.etNewPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()
            binding.etNewPassword.setSelection(binding.etNewPassword.text.length)
        }
        binding.imgShowPass1.setOnClickListener {
            binding.imgShowPass1.visibility = View.GONE
            binding.imgHidePass1.visibility = View.VISIBLE
            binding.etNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.etNewPassword.setSelection(binding.etNewPassword.text.length)
        }


    }
    private fun checkPasswordValidity(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#\$%^&*()-+=])[a-zA-Z0-9!@#\$%^&*()-+=]{6,}$"
        val pattern = Pattern.compile(passwordPattern)
        val isPasswordValid = pattern.matcher(password).matches()

        if (!isPasswordValid) {
            showErrorDialog("The password must consist of at least 6 characters and include at least 1 number, 1 uppercase letter, and 1 special character.")
            binding.etCurrentPassword.requestFocus()
        }

        return isPasswordValid
    }



    fun validateInput(): Boolean {
        val password = binding.etCurrentPassword.text.toString().trim()
        val confirmPassword = binding.etNewPassword.text.toString().trim()



        // Validate Password
        if (password.isEmpty()) {
            showErrorDialog("Please enter password")
            binding.etCurrentPassword.requestFocus()
            return false
        } else if (!checkPasswordValidity(password)) {
            return false
        }

        // Validate Confirm Password
        if (confirmPassword.isEmpty()) {
            showErrorDialog("Please confirm your password")
            binding.etNewPassword.requestFocus()
            return false
        }

        return true
    }
    private fun showErrorDialog(message: String) {
        ErrorMsgBox.alertError(context, message)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }





}