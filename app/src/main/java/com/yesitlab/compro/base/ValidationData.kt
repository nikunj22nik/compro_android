package com.yesitlab.compro.base

import java.util.regex.Pattern

class ValidationData {


    fun emailValidate(email : String) : Boolean {

        val emailString = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val patternEmail = Pattern.compile(emailString)
        val email = patternEmail.matcher(email)

        return  email.matches()
    }


    fun passCheck(pass : String) : Boolean {
        val passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$"
        val patternPass = Pattern.compile(passwordRegex)
        val pass = patternPass.matcher(pass)

        return  pass.matches()
    }

    fun phoneNoCheck(no : String) : Boolean {
        val phoneNoString = "\\A[0-9]{10}\\\\z"
        val patternNo = Pattern.compile(phoneNoString)
        val no = patternNo.matcher(no)

        return  no.matches()
    }


fun nameCheck(name : String) : Boolean {
    val nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"
    val patternname = Pattern.compile(nameRegex)
        val name = patternname.matcher(name)

        return  name.matches()
    }

}