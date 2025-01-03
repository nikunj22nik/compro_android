package com.yesitlab.compro.fragment.authfragment.forgetPasswordFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yesitlab.compro.R
import com.yesitlab.compro.base.AppConstant
import com.yesitlab.compro.base.ErrorMsgBox
import com.yesitlab.compro.databinding.FragmentForgotPasswordBinding
import java.util.regex.Pattern


class ForgotPasswordFragment : Fragment() , OnClickListener{
private lateinit var binding: FragmentForgotPasswordBinding

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
        binding = FragmentForgotPasswordBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rlRegister.setOnClickListener(this)
        binding.imageBackButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.rlRegister->{
                if (validateInput()){
                val bundle = Bundle()
                bundle.putInt(AppConstant.verificationType,1)
                findNavController().navigate(R.id.pleaseVerifyFragment,bundle)
                }
            }
            R.id.imageBackButton->{
            findNavController().navigate(R.id.loginFragment)
            }
        }
    }

    fun validateInput(): Boolean {

        val email = binding.etEmailAddress.text.toString().trim()




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

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        val pattern = Pattern.compile(emailPattern)
        return pattern.matcher(email).matches()
    }






    private fun showErrorDialog(message: String) {
        ErrorMsgBox.alertError(context, message)
    }


}