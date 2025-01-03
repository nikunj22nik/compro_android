package com.yesitlab.compro.fragment.authfragment.changePasswordFragment

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.R
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentChangePasswordBinding
import java.util.regex.Pattern


class ChangePasswordFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentChangePasswordBinding

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
        binding = FragmentChangePasswordBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rlSendPasswordResetLink.setOnClickListener(this)
        binding.imageBackButton.setOnClickListener(this)
        eyeHideShow()

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rlSendPasswordResetLink -> {
                if (validateInput()) {
                    ErrorMsgBox.alertError(requireContext(),"Password Change Successfully")
                    findNavController().navigate(R.id.loginFragment)
                }
            }
            R.id.imageBackButton -> {

                    findNavController().navigate(R.id.forgotPasswordFragment)

            }

        }
    }


    fun eyeHideShow(){
        binding.imgHidePass.setOnClickListener{
            binding.imgShowPass.visibility = View.VISIBLE
            binding.imgHidePass.visibility = View.GONE
            binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }
        binding.imgShowPass.setOnClickListener {
            binding.imgShowPass.visibility = View.GONE
            binding.imgHidePass.visibility = View.VISIBLE
            binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }

        binding.imgHidePass1.setOnClickListener{
            binding.imgShowPass1.visibility = View.VISIBLE
            binding.imgHidePass1.visibility = View.GONE
            binding.etConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
        }
        binding.imgShowPass1.setOnClickListener {
            binding.imgShowPass1.visibility = View.GONE
            binding.imgHidePass1.visibility = View.VISIBLE
            binding.etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
        }



    }


    fun validateInput(): Boolean {
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()



        // Validate Password
        if (password.isEmpty()) {
            showErrorDialog("Please enter password")
            binding.etPassword.requestFocus()
            return false
        } else if (!checkPasswordValidity(password)) {
            return false
        }

        // Validate Confirm Password
        if (confirmPassword.isEmpty()) {
            showErrorDialog("Please confirm your password")
            binding.etConfirmPassword.requestFocus()
            return false
        } else if (password != confirmPassword) {
            showErrorDialog("Passwords do not match")
            binding.etConfirmPassword.requestFocus()
            return false
        }

        return true
    }



    private fun checkPasswordValidity(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#\$%^&*()-+=])[a-zA-Z0-9!@#\$%^&*()-+=]{6,}$"
        val pattern = Pattern.compile(passwordPattern)
        val isPasswordValid = pattern.matcher(password).matches()

        if (!isPasswordValid) {
            showErrorDialog("The password must consist of at least 6 characters and include at least 1 number, 1 uppercase letter, and 1 special character.")
            binding.etPassword.requestFocus()
        }

        return isPasswordValid
    }




    private fun showErrorDialog(message: String) {
        ErrorMsgBox.alertError(context, message)
    }


}