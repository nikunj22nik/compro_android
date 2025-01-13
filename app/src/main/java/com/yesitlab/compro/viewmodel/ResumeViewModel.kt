package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject



@HiltViewModel
class ResumeViewModel  @Inject constructor(private val repository: ComroRepository): ViewModel()  {
    suspend fun apiResume(
        user_id: RequestBody,
        file: MultipartBody.Part,

        successCallback: (response: NetworkResult<String>) -> Unit
    ){
        repository.apiResume( user_id,file){
            successCallback(it)
        }
    }

}
