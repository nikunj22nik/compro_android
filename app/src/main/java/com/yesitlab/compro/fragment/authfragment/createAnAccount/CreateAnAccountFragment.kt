package com.yesitlab.compro.fragment.authfragment.createAnAccount


import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.network.CommonUtild
import com.example.network.NetworkResult
import com.hbb20.countrypicker.dialog.launchCountryPickerDialog
import com.hbb20.countrypicker.models.CPCountry
import com.yesitlab.compro.JarvisLoader
import com.yesitlab.compro.LoadingUtils
import com.yesitlab.compro.R
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentCreateAnAccountBinding
import com.yesitlab.compro.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class CreateAnAccountFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentCreateAnAccountBinding
    private var userType: Int? = -1
    private lateinit var viewModel : RegistrationViewModel
    private lateinit var commonUtild: CommonUtild

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userType = it.getInt(AppConstant.userType)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        // Inflate the layout for this fragment
        binding = FragmentCreateAnAccountBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        commonUtild = CommonUtild(requireContext())
        binding.cvCountry.setOnClickListener {
            context?.launchCountryPickerDialog { selectedCountry: CPCountry? ->
                // your code to handle selected country
                if (selectedCountry != null) {
                    binding.tvCountry.setText(selectedCountry.name.toString())
                }
            }
        }

        return binding.root
    }

    fun checkingValidation() : Boolean{

        var firstName = binding.etFirstName.text.toString()
        var lastName = binding.etLastName.text.toString()
        var email = binding.etEmailAddress.text.toString()
        var mobile = binding.etMobile.text.toString()
        var password = binding.etPassword.text.toString()
        var confPass = binding.etConfirmPassword.text.toString()
        var country = binding.tvCountry.text.toString()

        if(firstName.length ==0){
            binding.etFirstName.setError("Fill Out this Field")
            binding.etFirstName.requestFocus()
            return false
        }

        else if(lastName.length == 0){
            binding.etLastName.setError("Fill Out this Field")
            binding.etLastName.requestFocus()
            return false
        }

        else if(email.length ==0){
            binding.etEmailAddress.setError("Fill Out this Field")
            binding.etEmailAddress.requestFocus()
            return false
        }

        else if(!commonUtild.isValidEmail(email)){
            binding.etEmailAddress.setError("Please Enter Valid Email")
            binding.etEmailAddress.requestFocus()
            return false
        }

        else if(mobile.length ==0){
            binding.etMobile.setError("Fill Out this Field")
            binding.etMobile.requestFocus()
            return false
        }

        else if(!commonUtild.isValidPhoneNumber(mobile)){
            binding.etMobile.setError("Please Enter Valid Phone Number")
            binding.etMobile.requestFocus()
            return false
        }

        else if(password.length ==0){
            binding.etPassword.setError("Fill Out this Field")
            binding.etPassword.requestFocus()
            return false
        }

        else if(!commonUtild.isValidPassword(password)){
            binding.etPassword.error = "Password should be minimum eight characters long and should \\n\" + \"at least have one numeric, one alphabetic and one special character"
            binding.etPassword.requestFocus()
            return false
        }

        else if(confPass.length == 0){
            binding.etConfirmPassword.setError("Fill Out this Field")
            binding.etConfirmPassword.requestFocus()
            return false
        }

        else if(password.equals(confPass)==false){
            binding.etConfirmPassword.error = "Password and Confirm Password Should be Same"
            binding.etConfirmPassword.requestFocus()
            return false
        }

        else if(binding.tvCountry.text.toString().equals("Select Country")) {
            Toast.makeText(requireContext(),"Please Select Country",Toast.LENGTH_LONG).show()
            return false
        }

        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rlRegister.setOnClickListener(this)
        binding.textLoginHere.setOnClickListener(this)
        binding.imageBackButton.setOnClickListener(this)

        eyeHideShow()
        binding.rlRegister.setOnClickListener {
           lifecycleScope.launch {
               callingSubmitApi()
           }
        }
    }


    suspend fun callingSubmitApi(){
        if(validateInput()){
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val email = binding.etEmailAddress.text.toString().trim()
            val mobile = binding.etMobile.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            val country = binding.tvCountry.text.toString().trim()
           LoadingUtils.showDialog(requireContext(),true)
            viewModel.apiRegistration(firstName,lastName,email,password,"2",mobile, country){
               when(it){
                   is NetworkResult.Success ->{
                       LoadingUtils.hideDialog();
                       val bundle = Bundle()
                       bundle.putInt(AppConstant.verificationType, 0)
                       findNavController().navigate(R.id.pleaseVerifyFragment, bundle)
                   }

                   is NetworkResult.Error -> {
                       LoadingUtils.hideDialog();
                       LoadingUtils.showErrorDialog(requireContext(),it.message.toString())
                   }

                   is NetworkResult.Loading -> {}
               }
            }
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.rlRegister -> {
               // if (validateInput()){
                val bundle = Bundle()
                bundle.putInt(AppConstant.verificationType, 0)
                findNavController().navigate(R.id.pleaseVerifyFragment, bundle)
            //}
        }
            R.id.textLoginHere->{
                findNavController().navigate(R.id.loginFragment)
            }
            R.id.imageBackButton->{
                findNavController().navigate(R.id.userTypeSelectionFragment)
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
            binding.etConfirmPassword.setSelection(binding.etPassword.text.length)
        }
        binding.imgShowPass1.setOnClickListener {
            binding.imgShowPass1.visibility = View.GONE
            binding.imgHidePass1.visibility = View.VISIBLE
            binding.etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.etConfirmPassword.setSelection(binding.etPassword.text.length)
        }



    }

    fun validateInput(): Boolean {

        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val email = binding.etEmailAddress.text.toString().trim()
        val mobile = binding.etMobile.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()
        val country = binding.tvCountry.text.toString().trim()
        // Validate First Name
        if (firstName.isEmpty()) {
            showErrorDialog("Please enter first name")
            binding.etFirstName.requestFocus()
            return false
        }

        // Validate Last Name
        if (lastName.isEmpty()) {
            showErrorDialog("Please enter last name")
            binding.etLastName.requestFocus()
            return false
        }

        // Validate Email
        if (email.isEmpty()) {
            showErrorDialog("Please enter email")
            binding.etEmailAddress.requestFocus()
            return false
        } else if (!isValidEmail(email)) {
            showErrorDialog("Please enter a valid email")
            binding.etEmailAddress.requestFocus()
            return false
        }

        // Validate Mobile
        if (mobile.isEmpty()) {
            showErrorDialog("Please enter mobile number")
            binding.etMobile.requestFocus()
            return false
        } else if (!isValidMobile(mobile)) {
            showErrorDialog("Please enter a valid mobile number")
            binding.etMobile.requestFocus()
            return false
        }

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
        }
        else if (password != confirmPassword) {
            showErrorDialog("Passwords do not match")
            binding.etConfirmPassword.requestFocus()
            return false
        }
        else if(country.equals("Select Country")){
            Toast.makeText(requireContext(),"Please Select Country",Toast.LENGTH_LONG).show()
            return false
        }



        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        val pattern = Pattern.compile(emailPattern)
        return pattern.matcher(email).matches()
    }

    private fun isValidMobile(mobile: String): Boolean {
        // Example validation: Ensure it contains only digits and is of a certain length (e.g., 10 digits)
        val mobilePattern = "^[0-9]{10}$"
        val pattern = Pattern.compile(mobilePattern)
        return pattern.matcher(mobile).matches()
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