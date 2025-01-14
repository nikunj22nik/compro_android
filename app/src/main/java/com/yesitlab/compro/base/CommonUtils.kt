package com.yesitlab.compro.base

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import java.util.*

class CommonUtils(var context: Context) {

    var pref: SharedPreferences? = null
    var editor:SharedPreferences.Editor?=null

    init {
        pref=context.getSharedPreferences(AppConstant.LOGIN_SESSION, Context.MODE_PRIVATE)

        editor= pref?.edit()
    }

    fun setUserType(userType: String) {
        editor?.putString(AppConstant.USER_TYPE, userType)
        editor?.apply()
    }


    fun getUserType():String?{
        return pref?.getString(AppConstant.USER_TYPE,"")
    }

    fun setUserId(id:Int){
        editor!!.putInt(AppConstant.Id,id)
        editor!!.commit()
    }

    fun getUserId():Int?{
        return pref?.getInt(AppConstant.Id,-1)
    }

    fun setUserName(userName:String){
        editor!!.putString(AppConstant.userName,userName)
        editor!!.commit()
    }

    fun getUserName():String?{
        return pref?.getString(AppConstant.userName,"")
    }


    fun setUserEmail(email:String){
        editor!!.putString(AppConstant.email,email)
        editor!!.commit()
    }

    fun getUserEmail():String?{
        return pref?.getString(AppConstant.email,"")
    }



    fun setUserPhoneNumber(phoneNumber:String){
        editor!!.putString(AppConstant.phoneNumber,phoneNumber)
        editor!!.commit()
    }

    fun getUserPhoneNumber():String?{
        return pref?.getString(AppConstant.phoneNumber,"")
    }
    fun setUserCountry(country:String){
        editor!!.putString(AppConstant.country,country)
        editor!!.commit()
    }

    fun getUserCountry():String?{
        return pref?.getString(AppConstant.country,"")
    }





    fun setToken(token : String){
        editor!!.putString(AppConstant.Token,token)
        editor!!.commit()
    }

    fun getToken(): String{
        return pref?.getString(AppConstant.Token,"")?:""
    }
}