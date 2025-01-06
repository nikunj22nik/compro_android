package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApiExperienceViewModel  @Inject constructor(private val repository: ComroRepository): ViewModel()  {


     suspend fun apiAddExperience(
         user_id :String,
         profile_id :String,
         company :String,
         title :String,
         location: String,
         country :String,
         currently_prof :Boolean,
         start_date :String,
         end_date :String,
        successCallback: (response: NetworkResult<Pair<String, Int>>) -> Unit
    ){
         repository.apiAddExperience(user_id, profile_id, company,title,location,country, currently_prof, start_date, end_date){
             successCallback(it)
         }
     }
}