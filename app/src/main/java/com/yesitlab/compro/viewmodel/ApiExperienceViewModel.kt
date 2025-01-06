package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApiExperienceViewModel  @Inject constructor(private val repository: ComroRepository): ViewModel()  {


     suspend fun apiExperience(
        id: String,
        user_id: String,
        profile_id: String,
        organization: String,
        role: String,
        course: String,
        currently_prof: String,
        start_date: String,
        end_date: String,
        description: String,
        successCallback: (response: NetworkResult<Pair<String, Int>>) -> Unit
    ){
         repository.apiExperience(id,user_id, profile_id, organization, role, course, currently_prof, start_date, end_date, description){
             successCallback(it)
         }
     }
}