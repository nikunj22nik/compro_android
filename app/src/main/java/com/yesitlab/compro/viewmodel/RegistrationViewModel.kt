package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegistrationViewModel  @Inject constructor(private val repository: ComroRepository): ViewModel() {


    suspend fun apiRegistration(
        firstName :String,
        lastName :String,
        email :String,
        password :String,
        userType :String,
        mobile :String,
        country :String,
        successCallback: (response: NetworkResult<String>) -> Unit
    ){

        repository.apiRegistration(firstName, lastName, email, password, userType, mobile, country){
            successCallback(it)
        }


    }



}