package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ApiAddCertificateViewModel  @Inject constructor(private val repository: ComroRepository): ViewModel()  {

    suspend fun apiAddCertificate(
        user_id: RequestBody,
        profile_id: RequestBody,
        certificate_name: RequestBody,
        certificate_completion_id: RequestBody,
        certificate_url: RequestBody,
        start_date: RequestBody,
        end_date: RequestBody,
        certificate_prof: RequestBody,
        file: MultipartBody.Part,
        successCallback: (response: NetworkResult<String>) -> Unit
    ){
        repository.apiAddCertificate(   user_id,
            profile_id,
            certificate_name,
            certificate_completion_id,
            certificate_url,
            start_date,
            end_date,
            certificate_prof,
            file){
            successCallback(it)
        }
    }

}