package com.example.network.apiModel

 data class HomeResponse (
    var age: Any ,
    var average_rating: Int ,
    var certificates: MutableList<Certificate>,
    var country: String,
    val education: MutableList<Education>,
    val email: String,
    val experience_q: Any,
    val firstName: String,
    val gender: String,
    val goal_q: Any,
    val id: Int,
    val image: String,
    val is_contacted: Boolean,
    val lastName: String,
    val mobile: String,
    val rating_count: Int,
    val resume: String,
    val skills: MutableList<Skill>,
    val status: String,
    val title: String,
    val userType: String,
    val work_preference_q: Any
 )

data class Certificate(
    val certificate_completion_id: String,
    val certificate_name: String,
    val certificate_prof: Int,
    val certificate_url: String,
    val created_at: String,
    val end_date: String,
    val id: Int,
    val image: String,
    val profile_id: Int,
    val start_date: String,
    val updated_at: String,
    val user_id: Int
)

data class Education(
    val created_at: String,
    val degree: String,
    val description: String,
    val end_date: String,
    val fieldstudy: String,
    val grade: String,
    val id: Int,
    val profile_id: Int,
    val sch_uni: String,
    val start_date: String,
    val updated_at: String,
    val user_id: Int
)



data class Skill(
    val created_at: String,
    val id: Int,
    val profile_id: Int,
    val skill_user: String,
    val updated_at: String,
    val user_id: Int
)

