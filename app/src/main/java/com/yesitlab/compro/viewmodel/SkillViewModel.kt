package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SkillViewModel @Inject constructor(private val repository: ComroRepository) :
    ViewModel() {

    suspend fun apiAddSkill(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {

        repository.apiAddSkill(jsonObject) {
            successCallback(it)
        }

    }


}