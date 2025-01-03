package com.example.network.repository

import com.example.network.NetworkResult
import retrofit2.http.Field

interface ComroRepository {

    suspend fun apiRegistration(
           firstName :String,
           lastName :String,
           email :String,
           password :String,
           userType :String,
           mobile :String,
           country :String,
           successCallback: (response: NetworkResult<String>) -> Unit
    )
}