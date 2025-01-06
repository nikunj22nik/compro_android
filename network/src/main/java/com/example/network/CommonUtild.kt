package com.example.network

import android.content.Context
import android.util.Log
import java.util.regex.Pattern


class CommonUtild(context: Context) {

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return emailRegex.matches(email)
    }
    fun isValidPhoneNumber(phone: String): Boolean {
        val phoneRegex = "^\\+?[0-9]{10,15}$".toRegex()
        return phoneRegex.matches(phone)
    }

    fun isValidPassword(password: String): Boolean {
        // val pattern = """^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"""
        val pattern = """^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"""
        val regex = Pattern.compile(pattern)
        val matcher = regex.matcher(password)
        return matcher.matches()
    }






}