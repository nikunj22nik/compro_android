package com.yesitlab.compro.viewmodel

import androidx.lifecycle.ViewModel
import com.example.network.NetworkResult
import com.example.network.repository.ComroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val repository: ComroRepository) :
    ViewModel() {

    suspend fun apiResetPassword(
        user_id: String,
        old_password: String,
        new_password: String,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {

        repository.apiResetPassword(user_id, old_password, new_password) {
            successCallback(it)
        }

    }


}