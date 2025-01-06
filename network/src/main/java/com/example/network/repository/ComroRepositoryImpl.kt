package com.example.network.repository

import com.example.network.NetworkResult
import com.example.network.apiModel.Certificate
import com.example.network.apiModel.Education
import com.example.network.apiModel.HomeResponse
import com.example.network.apiModel.LoginResponse
import com.example.network.apiModel.Skill
import com.example.network.remote.ComroApi
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class ComroRepositoryImpl @Inject constructor(private val api:ComroApi) :ComroRepository {

    override suspend fun apiRegistration(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        userType: String,
        mobile: String,
        country: String,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiRegistration(firstName,lastName,email,password, userType, mobile, country).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").equals("success")){
                            var MSG = it.get("message").asString
                            successCallback(NetworkResult.Success<String>(MSG))
                        }
                        else if(it.has("status") && it.get("status").asString.equals("error")){
                            if(it.has("errors")){
                                var obj = it.get("errors").asJsonObject

                                if(obj.has("email")){
                                    successCallback(NetworkResult.Error<String>("The email has already been taken."))
                                }else{
                                    successCallback(NetworkResult.Error<String>("The mobile has already been taken."))
                                }

                            }

                        }
                        else{
                            successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
                        }
                    }?: successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
                }
                else {
                    try {
                        val jsonObj = this.errorBody()?.string()?.let { JSONObject(it) }
                        successCallback(
                            NetworkResult.Error<String>(jsonObj?.getString("message")
                                ?: "There was an unknown error. Check your connection and try again."
                            )
                        )
                    }
                    catch (e: JSONException) {
                        e.printStackTrace()
                        successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
                    }
                }
            }
        }
        catch (e: HttpException) {
            if (e.code().toString() == "401") {
            }
            successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: IOException) {
            successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: Exception) {
            successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
        }



    }

    override suspend fun apiLogin(
        email: String,
        password: String,
        successCallback: (response: NetworkResult<Pair<String,Int>>) -> Unit
    ) {
        try{
            api.apiLogin(email,password).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asBoolean){
                            var massage = it.get("data").asString



                            var token = it.get("token").asString





                            var user_id = -1
                             user_id = it.get("user_id").asInt

                            if(user_id != -1){
                                successCallback(NetworkResult.Success<Pair<String,Int>>(Pair(token,user_id)))
                            }






                           // successCallback(NetworkResult.Success<String>())

                        }
                        else if(it.get("status").asBoolean == false){

                            val message = it.get("data").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<Pair<String,Int>>(message))
                            }

                        }
                        else{
                            successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
                        }
                    }?: successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
                }
                else {
                    try {
                        val jsonObj = this.errorBody()?.string()?.let { JSONObject(it) }
                        successCallback(
                            NetworkResult.Error<Pair<String,Int>>(jsonObj?.getString("message")
                                ?: "There was an unknown error. Check your connection and try again."
                            )
                        )
                    }
                    catch (e: JSONException) {
                        e.printStackTrace()
                        successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
                    }
                }
            }
        }
        catch (e: HttpException) {
            if (e.code().toString() == "401") {
            }
            successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: IOException) {
            successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: Exception) {
            successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
        }

    }

    override suspend fun apiAddExperience(

        user_id: String,
        profile_id: String,
        company: String,
        title: String,
        location: String,
        country: String,
        currently_prof: Boolean,
        start_date: String,
        end_date: String,

        successCallback: (response: NetworkResult<Pair<String, Int>>) -> Unit
    ) {
        try{
            api.apiAddExperience(user_id,profile_id,company,title,location,country,currently_prof,start_date,end_date).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString == "success"){



                        // successCallback(NetworkResult.Success<String>())

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<Pair<String,Int>>(message))
                            }

                        }
                        else{
                            successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
                        }
                    }?: successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
                }
                else {
                    try {
                        val jsonObj = this.errorBody()?.string()?.let { JSONObject(it) }
                        successCallback(
                            NetworkResult.Error<Pair<String,Int>>(jsonObj?.getString("message")
                                ?: "There was an unknown error. Check your connection and try again."
                            )
                        )
                    }
                    catch (e: JSONException) {
                        e.printStackTrace()
                        successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
                    }
                }
            }
        }
        catch (e: HttpException) {
            if (e.code().toString() == "401") {
            }
            successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: IOException) {
            successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: Exception) {
            successCallback(NetworkResult.Error<Pair<String,Int>>("There was an unknown error. Check your connection, and try again."))
        }
    }

    override suspend fun apiUpdateExperience(
        id: String,
        user_id: String,
        profile_id: String,
        company: String,
        title: String,
        location: String,
        country: String,
        currently_prof: Boolean,
        start_date: String,
        end_date: String,
        description: String,
        successCallback: (response: NetworkResult<Pair<String, Int>>) -> Unit
    ) {
        TODO("Not yet implemented")
    }


    override suspend fun apitalent(
        user_id: String,
        page: Int,
        successCallback: (response: NetworkResult<MutableList<HomeResponse>>) -> Unit
    ) {
        try{
            api.apitalent(user_id,page).apply {
                if(isSuccessful){
                    body()?.let {
                       // if(it.get("status").asBoolean){
                            val data = it.get("data").asJsonArray
                            val simplifiedResponses = data.map { jsonElement ->
                                val obj = jsonElement.asJsonObject
                                HomeResponse(age = obj.get("age").asString,
                                 average_rating = obj.get("age").asInt ,
                                 country = obj.get("country").asString,

                                 email =  obj.get("email").asString,
                                 experience_q  =  obj.get("experience_q").asString,
                                 firstName =  obj.get("firstName").asString ,
                                 gender =  obj.get("gender").asString  ,
                                 goal_q = obj.get("goal_q").asString ,
                                 id = obj.get("id").asInt ,
                                 image = obj.get("goal_q").asString ,
                                 is_contacted =  obj.get("is_contacted").asBoolean,
                                 lastName = obj.get("lastName").asString,
                                 mobile = obj.get("mobile").asString ,
                                 rating_count  = obj.get("rating_count").asInt  ,
                                 resume = obj.get("resume").asString,
                                 status = obj.get("status").asString,
                                 title = obj.get("title").asString,
                                 userType = obj.get("userType").asString,
                                 work_preference_q = obj.get("work_preference_q").asString,

                                skills = obj.get("skills").asJsonArray.map { skill ->
                                        // Map Skill object
                                        Skill(
                                            created_at = skill.asJsonObject.get("created_at").asString,
                                            id = skill.asJsonObject.get("id").asInt,
                                            profile_id = skill.asJsonObject.get("profile_id").asInt,
                                            skill_user = skill.asJsonObject.get("skill_user").asString,
                                            updated_at = skill.asJsonObject.get("updated_at").asString,
                                            user_id = skill.asJsonObject.get("user_id").asInt
                                        )
                                    }.toMutableList(),
                                    certificates = obj.get("certificates").asJsonArray.map {
                                        Certificate(
                                         certificate_completion_id = it.asJsonObject.get("certificate_completion_id").asString,
                                         certificate_name  = it.asJsonObject.get("certificate_name").asString,
                                         certificate_prof  = it.asJsonObject.get("certificate_prof").asInt,
                                         certificate_url = it.asJsonObject.get("certificate_url").asString,
                                         created_at = it.asJsonObject.get("created_at").asString,
                                         end_date = it.asJsonObject.get("end_date").asString,
                                         id = it.asJsonObject.get("id").asInt,
                                         image = it.asJsonObject.get("image").asString ,
                                         profile_id = it.asJsonObject.get("profile_id").asInt,
                                         start_date = it.asJsonObject.get("start_date").asString,
                                         updated_at = it.asJsonObject.get("updated_at").asString,
                                         user_id = it.asJsonObject.get("user_id").asInt

                                        )
                                    }.toMutableList()

                                    ,
                                    education = obj.get("education").asJsonArray.map { edu ->
                                        // Map Education object
                                        Education(
                                            created_at = edu.asJsonObject.get("created_at").asString,
                                            degree = edu.asJsonObject.get("degree").asString,
                                            description = edu.asJsonObject.get("description").asString,
                                            end_date = edu.asJsonObject.get("end_date").asString,
                                            fieldstudy = edu.asJsonObject.get("fieldstudy").asString,
                                            grade = edu.asJsonObject.get("grade").asString,
                                            id = edu.asJsonObject.get("id").asInt,
                                            profile_id = edu.asJsonObject.get("profile_id").asInt,
                                            sch_uni = edu.asJsonObject.get("sch_uni").asString,
                                            start_date = edu.asJsonObject.get("start_date").asString,
                                            updated_at = edu.asJsonObject.get("updated_at").asString,
                                            user_id = edu.asJsonObject.get("user_id").asInt
                                        )
                                    }.toMutableList()
                                )
                            }.toMutableList()

                            successCallback(NetworkResult.Success(simplifiedResponses))

                            // successCallback(NetworkResult.Success<String>())

                     //   }
//                        else if(it.get("status").asBoolean == false){
//
//                            val message = it.get("data").asString
//                            if (message != null){
//                                successCallback(NetworkResult.Error<MutableList<HomeResponse>>(message))
//                            }
//
//                        }
//                        else{
//                            successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
//                        }
                    }?: successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
                }
                else {
                    try {
                        val jsonObj = this.errorBody()?.string()?.let { JSONObject(it) }
                        successCallback(
                            NetworkResult.Error<MutableList<HomeResponse>>(jsonObj?.getString("message")
                                ?: "There was an unknown error. Check your connection and try again."
                            )
                        )
                    }
                    catch (e: JSONException) {
                        e.printStackTrace()
                        successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
                    }
                }
            }
        }
        catch (e: HttpException) {
            if (e.code().toString() == "401") {
            }
            successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: IOException) {
            successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: Exception) {
            successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
        }


    }




}