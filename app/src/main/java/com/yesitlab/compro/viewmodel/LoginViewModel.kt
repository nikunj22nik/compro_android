package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: ComroRepository): ViewModel() {


    suspend fun apiLogin(

        email :String,
        password :String,

        successCallback: (response: NetworkResult<Pair<String,Int>>) -> Unit
    ){

        repository.apiLogin( email, password ){
            successCallback(it)
        }


    }

}