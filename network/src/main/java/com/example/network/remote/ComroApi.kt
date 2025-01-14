package com.example.network.remote

import com.example.network.requestModel.AddExperienceRequest
import com.example.network.requestModel.LoginRequest
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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


//    @POST("Apilogin")
//    suspend fun apiLogin(
//        @Body request: LoginRequest
//    ) :Response<JsonObject>




    @POST("forgotPasswordSendRequest")
    @FormUrlEncoded
    suspend fun apiForgotPasswordSendRequest(
        @Field("email") email :String
    ) :Response<JsonObject>


    @POST("resetPassword")
    @FormUrlEncoded
    suspend fun apiResetPassword(
        @Field("user_id") user_id :String,
        @Field("old_password") old_password :String,
        @Field("new_password") new_password :String
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

        @Field("user_id") user_id : String,
        @Field("profile_id") profile_id :String,
        @Field("company") company : String,
        @Field("title") title :String,
        @Field("location") location : String,
        @Field("country") country : String,
        @Field("currently_prof") currently_prof :String,
        @Field("start_date") start_date : String,
        @Field("end_date") end_date : String
    ) :Response<JsonObject>



//    @POST("apiExperience")
//    suspend fun apiAddExperience(
//        @Body request: AddExperienceRequest
//    ): Response<JsonObject>



    @POST("apiExperience")
    @FormUrlEncoded
    suspend fun apiUpdateExperience(
        @Field("id") id :String,
        @Field("user_id") user_id : String,
        @Field("profile_id") profile_id :String,
        @Field("company") company : String,
        @Field("title") title :String,
        @Field("location") location : String,
        @Field("country") country : String,
        @Field("currently_prof") currently_prof :String,
        @Field("start_date") start_date : String,
        @Field("end_date") end_date : String
    ) :Response<JsonObject>

    @POST("listExperience")
    suspend fun apiGetExperience(@Body jsonObject : JsonObject) :Response<JsonObject>
    @POST("listEducation")
    suspend fun apiGetEducation(@Body jsonObject : JsonObject) :Response<JsonObject>
    @POST("deleteExperience")
    suspend fun apiDeleteExperience(@Body jsonObject : JsonObject) :Response<JsonObject>


    @POST("apiEducation")
    @FormUrlEncoded
    suspend fun apiAddEducation(
        @Field("user_id") user_id : String,
        @Field("profile_id") profile_id :String,
        @Field("school") school : String,
        @Field("degree") degree :String,
        @Field("fieldstudy") fieldstudy : String,
        @Field("country") country : String,
        @Field("start_date") start_date : String,
        @Field("end_date") end_date : String,
        @Field("description") description :String
    ) :Response<JsonObject>



    @POST("apiEducation")
    @FormUrlEncoded
    suspend fun apiUpdateEducation(
        @Field("id") id :String,
        @Field("user_id") user_id : String,
        @Field("school") school : String,
        @Field("degree") degree :String,
        @Field("fieldstudy") fieldstudy : String,
        @Field("country") country : String,
        @Field("start_date") start_date : String,
        @Field("end_date") end_date : String,
        @Field("description") description :String
    ) :Response<JsonObject>


    @POST("deleteEducation")
    suspend fun apiDeleteEducation(@Body jsonObject: JsonObject) : Response<JsonObject>


    @Multipart
    @POST("apiPortfolio")
    suspend fun apiAddPortfolio(
        @Part("user_id")user_id:RequestBody?,
        @Part("profile_id")profile_id:RequestBody?,
        @Part("title")title:RequestBody?,
        @Part("role")role : RequestBody?,
        @Part("description")description:RequestBody?,
        @Part images: ArrayList<MultipartBody.Part>?
    ) : Response<JsonObject>


    @Multipart
    @POST("apiPortfolio")
    suspend fun apiUpdatePortfolio(
        @Part("id")id:RequestBody?,
        @Part("user_id")user_id:RequestBody?,
        @Part("profile_id")profile_id:RequestBody?,
        @Part("title")title:RequestBody?,
        @Part("role")role : RequestBody?,
        @Part("description")description:RequestBody?,
        @Part images: ArrayList<MultipartBody.Part>?
    ) : Response<JsonObject>


    @Multipart
    @POST("apiCertificate")
    suspend fun apiAddCertificate(
        @Part("user_id") user_id: RequestBody,
        @Part("profile_id") profile_id: RequestBody,
        @Part("certificate_name") certificate_name: RequestBody,
        @Part("certificate_completion_id") certificate_completion_id: RequestBody,
        @Part("certificate_url") certificate_url: RequestBody,
        @Part("start_date") start_date: RequestBody,
        @Part("end_date") end_date: RequestBody,
        @Part("certificate_prof") certificate_prof: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<JsonObject>

    @Multipart
    @POST("apiCertificate")
    suspend fun apiUpdateCertificate(
        @Part("id") id: RequestBody,
        @Part("user_id") user_id: RequestBody,
        @Part("profile_id") profile_id: RequestBody,
        @Part("certificate_name") certificate_name: RequestBody,
        @Part("certificate_completion_id") certificate_completion_id: RequestBody,
        @Part("certificate_url") certificate_url: RequestBody,
        @Part("start_date") start_date: RequestBody,
        @Part("end_date") end_date: RequestBody,
        @Part("certificate_prof") certificate_prof: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<JsonObject>




    @POST("apiSkill")
    suspend fun apiAddSkill(@Body jsonObject : JsonObject) :Response<JsonObject>


    @POST("apiChoosePlan")
    suspend fun apiChoosePlan(@Body jsonObject : JsonObject) :Response<JsonObject>

    @POST("apiOverview")
    suspend fun apiAddOverview(@Body jsonObject : JsonObject) :Response<JsonObject>

    @Multipart
    @POST("apiResume")
    suspend fun apiResume(
        @Part("user_id") user_id: RequestBody,
        @Part resume: MultipartBody.Part
    ) :Response<JsonObject>


    @Multipart
    @POST("apiLocation")
    suspend fun apiAddLocation(
        @Part("user_id") user_id: RequestBody,
        @Part("streetaddress") streetaddress: RequestBody,
        @Part("app_suite") app_suite: RequestBody,
        @Part("city") city: RequestBody,
        @Part("state") state: RequestBody,
        @Part("zipcode") zipcode: RequestBody,
        @Part("dateofbirth") dateofbirth: RequestBody,
        @Part("professional_title") professional_title: RequestBody,
        @Part image: MultipartBody.Part
    ) :Response<JsonObject>



}