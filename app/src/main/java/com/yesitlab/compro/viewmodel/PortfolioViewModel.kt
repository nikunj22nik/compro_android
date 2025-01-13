package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import okhttp3.RequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
    class PortfolioViewModel @Inject constructor(private val repository: ComroRepository): ViewModel() {


        suspend fun apiAddPortfolio(
            user_id: RequestBody?,
            profile_id: RequestBody?,
            title: RequestBody?,
            role: RequestBody?,
            description: RequestBody?,
            images: ArrayList<MultipartBody.Part>?,
            successCallback: (response: NetworkResult<String>) -> Unit
        ) {

            repository.apiAddPortfolio(user_id, profile_id, title, role, description, images) {
                successCallback(it)
            }

        }


            suspend fun apiUpdatePortfolio(
                id: RequestBody?,
                user_id: RequestBody?,
                profile_id: RequestBody?,
                title: RequestBody?,
                role: RequestBody?,
                description: RequestBody?,
                images: ArrayList<MultipartBody.Part>?,
                successCallback: (response: NetworkResult<String>) -> Unit
            ) {

                repository.apiUpdatePortfolio(
                    id,
                    user_id,
                    profile_id,
                    title,
                    role,
                    description,
                    images
                ) {
                    successCallback(it)
                }

            }

}