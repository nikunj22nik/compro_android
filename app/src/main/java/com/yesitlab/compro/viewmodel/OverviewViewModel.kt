package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

    @HiltViewModel
    class OverviewViewModel  @Inject constructor(private val repository: ComroRepository): ViewModel()  {


        suspend fun apiAddOverview(
            jsonObject : JsonObject,
            successCallback: (response: NetworkResult<String>) -> Unit
        ){
            repository.apiAddOverview(jsonObject){
                successCallback(it)
            }
        }

    }