package com.yesitlab.compro.fragment.authfragment.loginFragment

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.CommonUtild
import com.example.network.NetworkResult
import com.example.network.SessionManager
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.R
import com.yesitlab.compro.activity.homeActivity.HomeActivity
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.base.CommonUtils
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.base.ValidationData
import com.yesitlab.compro.databinding.FragmentLoginBinding
import com.yesitlab.compro.viewmodel.LoginViewModel
import com.yesitlab.compro.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    var validation: ValidationData = ValidationData()

    private lateinit var viewModel : LoginViewModel
    private lateinit var commonUtild: CommonUtild
    private lateinit var commonUtils: CommonUtils
    private lateinit var sessionManager: SessionManager

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
        binding =
            FragmentLoginBinding.inflate(LayoutInflater.from(requireContext()), container, false)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        commonUtild = CommonUtild(requireContext())
        commonUtils = CommonUtils(requireContext())
        sessionManager = SessionManager(requireContext())



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textForgotPassword.setOnClickListener(this)
        binding.rlSignIn.setOnClickListener(this)
        binding.textSignUp.setOnClickListener(this)
        binding.imageBackButton.setOnClickListener(this)
        eyeHideShow()
        backPress()
    }

    private fun backPress(){
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                // Handle back press logic here
                findNavController().navigate(R.id.welcomeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.textForgotPassword -> {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }
            R.id.textSignUp -> {
                findNavController().navigate(R.id.userTypeSelectionFragment)
            }
            R.id.imageBackButton -> {
                findNavController().navigate(R.id.welcomeFragment)
            }
            R.id.rlSignIn -> {
                if (validateInput()){
                    lifecycleScope.launch {
                        loginApi()
                    }

                }

            }
        }
    }


    suspend fun loginApi() {
        val email = binding.etEmailUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()




        LoadingUtils.showDialog(requireContext(),true)
        viewModel.apiLogin(email,password){
            when(it){
                is NetworkResult.Success -> {
                    LoadingUtils.hideDialog()


                    it.data?.token?.let { it1-> sessionManager.setToken(it1) }
                    it.data?.user_id?.let { it1 -> commonUtils.setUserId(it1) }
                    it.data?.mobile?.let { it1 -> commonUtils.setUserPhoneNumber(it1) }
                    it.data?.email1?.let { it1 -> commonUtils.setUserEmail(it1) }
                    it.data?.country?.let { it1 -> commonUtils.setUserCountry(it1) }
                    it.data?.name?.let { it1 -> commonUtils.setUserName(it1) }



                    Log.d("detailsVipin","   ${commonUtils.getUserId()}    ${commonUtils.getUserEmail()} ${commonUtils.getUserCountry()}  ${commonUtils.getUserPhoneNumber()} ${commonUtils.getUserName()} ")

                    val intent = Intent(requireActivity(), HomeActivity::class.java)
                    intent.putExtra(AppConstant.homeActivity, "login")
                    startActivity(intent)
                    requireActivity().finish()

                }
                is NetworkResult.Error -> {
                    LoadingUtils.hideDialog()
                    LoadingUtils.showErrorDialog(requireContext(),it.message.toString())

                }
                is NetworkResult.Loading -> TODO()

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
    }

    fun validateInput(): Boolean {
        val email = binding.etEmailUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isEmpty()) {
            showErrorDialog("Please enter email")
            binding.etEmailUsername.requestFocus()
            return false
        }

        if (password.isEmpty()) {
            showErrorDialog("Please enter password")
            binding.etPassword.requestFocus()
            return false
        }

        return if (isValidEmail(email)) {
            checkPasswordValidity(password)
        } else {
            showErrorDialog("Please enter a valid email")
            binding.etEmailUsername.requestFocus()
            false
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        val pattern = Pattern.compile(emailPattern)
        return pattern.matcher(email).matches()
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