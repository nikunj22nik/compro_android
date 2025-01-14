package com.example.network.repository

import com.example.network.NetworkResult
import com.example.network.apiModel.HomeResponse
import com.example.network.apiModel.UserInfo
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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
        successCallback: (response: NetworkResult<UserInfo>) -> Unit
    )

    suspend fun apiForgotPasswordSendRequest(
        email :String,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiResetPassword(
        user_id :String,
        old_password :String,
        new_password :String,
        successCallback: (response: NetworkResult<String>) -> Unit
    )



    suspend fun apitalent(
        user_id :String,
        page :Int,
        successCallback: (response: NetworkResult<MutableList<HomeResponse>>) -> Unit
    )






    suspend fun apiAddExperience(

        user_id :String,
        profile_id :String,
        company :String,
        title :String,
        location: String,
        country :String,
        currently_prof :String,
        start_date :String,
        end_date :String,

        successCallback: (response: NetworkResult<String>) -> Unit
    )



    suspend fun apiUpdateExperience(
        id :String,
        user_id :String,
        profile_id :String,
        company :String,
        title :String,
        location: String,
        country :String,
        currently_prof :String,
        start_date :String,
        end_date :String,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiDeleteExperience(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    )


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
    )



    suspend fun apiUpdateEducation(
        id :String,
        user_id :String,
        school :String,
        degree :String,
        fieldstudy: String,
        country :String,
        start_date :String,
        end_date :String,
        description :String,
        successCallback: (response: NetworkResult<Pair<String,Int>>) -> Unit
    )

    suspend fun apiAddPortfolio(
        user_id: RequestBody?,
        profile_id: RequestBody?,
        title: RequestBody?,
        role: RequestBody?,
        description: RequestBody?,
        images: ArrayList<MultipartBody.Part>?,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiUpdatePortfolio(
        id: RequestBody?,
        user_id: RequestBody?,
        profile_id: RequestBody?,
        title: RequestBody?,
        role: RequestBody?,
        description: RequestBody?,
        images: ArrayList<MultipartBody.Part>?,
        successCallback: (response: NetworkResult<String>) -> Unit
    )
    suspend fun apiAddCertificate(
        user_id: RequestBody,
        profile_id: RequestBody,
        certificate_name: RequestBody,
        certificate_completion_id: RequestBody,
        certificate_url: RequestBody,
        start_date: RequestBody,
        end_date: RequestBody,
        certificate_prof: RequestBody,
        file: MultipartBody.Part,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiDeleteEducation(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiAddSkill(
        JsonObject : JsonObject,

        successCallback: (response: NetworkResult<String>) -> Unit
    )
    suspend fun apiResume(
        user_id: RequestBody,
        file: MultipartBody.Part,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiAddLocation(
        user_id: RequestBody,
        streetaddress: RequestBody,
        app_suite: RequestBody,
        city: RequestBody,
        state: RequestBody,
        zipcode: RequestBody,
        dateofbirth: RequestBody,
        professional_title: RequestBody,
        image: MultipartBody.Part,
        successCallback: (response: NetworkResult<String>) -> Unit
    )


    suspend fun apiGetExperience(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiChoosePlan(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiAddOverview(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    )

    suspend fun apiGetEducation(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    )
}