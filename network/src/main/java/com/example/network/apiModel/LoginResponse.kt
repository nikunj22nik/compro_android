package com.example.network.apiModel

data class LoginResponse(
    val status: Boolean,
    val message: String?,
    val token: String?,
    val user_id: String?
)
