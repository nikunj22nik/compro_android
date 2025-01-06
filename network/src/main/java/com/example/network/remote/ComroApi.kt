package com.example.network.remote

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ComroApi {

    @POST("Apiregister")
    @FormUrlEncoded
    suspend fun apiRegistration(
     @Field("firstName") firstName :String,
     @Field("lastName") lastName :String,
     @Field("email") email :String,
     @Field("password") password :String,
     @Field("userType") userType :String,
     @Field("mobile") mobile :String,
     @Field("country") country :String
    ) :Response<JsonObject>



    @POST("Apilogin")
    @FormUrlEncoded
    suspend fun apiLogin(
        @Field("email") email :String,
        @Field("password") password :String
    ) :Response<JsonObject>


    @POST("apitalent")
    @FormUrlEncoded
    suspend fun apitalent(
        @Field("user_id") user_id :String,
        @Field("page") page : Int
    ) :Response<JsonObject>



    @POST("apiExperience")
    @FormUrlEncoded
    suspend fun apiAddExperience(
        @Field("id") id :String,
        @Field("user_id") user_id : String,
        @Field("profile_id") profile_id :String,
        @Field("company") company : String,
        @Field("title") title :String,
        @Field("location") location : String,
        @Field("currently_prof") currently_prof :Boolean,
        @Field("start_date") start_date : String,
        @Field("end_date") end_date : String
    ) :Response<JsonObject>



    @POST("apiExperience")
    @FormUrlEncoded
    suspend fun apiUpdateExperience(
        @Field("id") id :String,
        @Field("user_id") user_id : String,
        @Field("profile_id") profile_id :String,
        @Field("company") company : String,
        @Field("title") title :String,
        @Field("location") location : String,
        @Field("currently_prof") currently_prof :Boolean,
        @Field("start_date") start_date : String,
        @Field("end_date") end_date : String
    ) :Response<JsonObject>





}