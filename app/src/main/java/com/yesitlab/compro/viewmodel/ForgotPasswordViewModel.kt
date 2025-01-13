package com.yesitlab.compro.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.apiModel.HomeResponse
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repository: ComroRepository): ViewModel() {


    suspend fun apiForgotPasswordSendRequest(

        email :String,


        successCallback: (response: NetworkResult<String>) -> Unit
    ){

        repository.apiForgotPasswordSendRequest( email ){
            successCallback(it)
        }


    }
}