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

}