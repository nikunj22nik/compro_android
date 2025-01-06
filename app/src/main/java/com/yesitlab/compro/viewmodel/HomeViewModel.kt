package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.apiModel.HomeResponse
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ComroRepository): ViewModel() {


    suspend fun apitalent(

        user_id :String,
        page :Int,

        successCallback: (response: NetworkResult<MutableList<HomeResponse>>) -> Unit
    ){

        repository.apitalent( user_id , page ){
            successCallback(it)
        }


    }

}