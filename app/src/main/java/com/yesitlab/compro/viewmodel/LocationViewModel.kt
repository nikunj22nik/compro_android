package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject



    @HiltViewModel
    class LocationViewModel @Inject constructor(private val repository: ComroRepository): ViewModel() {



        suspend fun apiAddLocation(

            user_id: RequestBody,
            streetaddress: RequestBody,
            app_suite: RequestBody,
            city: RequestBody,
            state: RequestBody,
            zipcode: RequestBody,
            dateofbirth: RequestBody,
            professional_title: RequestBody,
            image: MultipartBody.Part,

            successCallback: (response: NetworkResult<String>) -> Unit
        ){

            repository.apiAddLocation( user_id ,streetaddress,app_suite,city,state,zipcode,dateofbirth,professional_title,image ){
                successCallback(it)
            }


        }

    }