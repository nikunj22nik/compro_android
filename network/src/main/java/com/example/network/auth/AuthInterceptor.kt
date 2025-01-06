package com.example.network.auth

import android.content.Context
import android.util.Log
import com.example.network.CommonUtild

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(var context: Context) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder: Request.Builder = chain.request().newBuilder()

//        if (sessionManager.fetchAuthToken()!=null) {
//            requestBuilder.addHeader("Authorization", "Bearer " + sessionManager.fetchAuthToken())
//        }
        requestBuilder.addHeader("x-api-key", "bGS6lzFqvvSQ8ALbOxatm7/Vk7mLQyzqaS34Q4oR1ew=")
        return chain.proceed(requestBuilder.build())
    }





}