package com.example.network.requestModel

data class AddExperienceRequest(
    val user_id: String,
    val profile_id: String,
    val company: String,
    val title: String,
    val location: String,
    val country: String,
    val currently_prof: Boolean,
    val start_date: String,
    val end_date: String
)

