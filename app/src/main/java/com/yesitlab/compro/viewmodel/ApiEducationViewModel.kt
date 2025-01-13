package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

    @HiltViewModel
    class ApiEducationViewModel  @Inject constructor(private val repository: ComroRepository): ViewModel()  {


        suspend fun apiAddEducation(
            user_id :String,
            profile_id :String,
            school :String,
            degree :String,
            fieldstudy: String,
            country :String,
            start_date :String,
            end_date :String,
            description :String,
            successCallback: (response: NetworkResult<String>) -> Unit
        ){
            repository.apiAddEducation(user_id, profile_id, school,degree,fieldstudy,country, start_date, end_date, description){
                successCallback(it)
            }
        }



        suspend fun apiUpdateEducation(
            id: String,
            user_id :String,
            profile_id :String,
            school :String,
            degree :String,
            fieldstudy: String,
            country :String,
            start_date :String,
            end_date :String,
            description :String,
            successCallback: (response: NetworkResult<Pair<String, Int>>) -> Unit
        ){
            repository.apiUpdateEducation(id,user_id, profile_id, school,degree,fieldstudy,country, start_date, end_date, description){
                successCallback(it)
            }
        }
}