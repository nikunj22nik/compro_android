package com.example.network.repository

import com.example.network.NetworkResult
import com.example.network.apiModel.HomeResponse
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

    suspend fun apiLogin(
        email :String,
        password :String,
        successCallback: (response: NetworkResult<Pair<String,Int>>) -> Unit
    )



    suspend fun apitalent(
        user_id :String,
        page :Int,
        successCallback: (response: NetworkResult<MutableList<HomeResponse>>) -> Unit
    )






    suspend fun apiExperience(
        id :String,
        user_id :String,
        profile_id :String,
        organization :String,
        role :String,
        course :String,
        currently_prof :String,
        start_date :String,
        end_date :String,
        description :String,
        successCallback: (response: NetworkResult<Pair<String,Int>>) -> Unit
    )



}