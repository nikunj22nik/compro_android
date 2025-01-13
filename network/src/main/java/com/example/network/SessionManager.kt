package com.example.network

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context :Context) {


    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor?=null

    init {
        pref=context.getSharedPreferences("login_session", Context.MODE_PRIVATE)

        editor= pref?.edit()
    }



    fun setToken(token : String){
        editor!!.putString("Token",token)
        editor!!.commit()
    }

    fun getToken(): String{
        return pref?.getString("Token","")?:""
    }
}