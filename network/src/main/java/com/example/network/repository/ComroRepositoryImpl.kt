package com.example.network.repository

import com.example.network.NetworkResult
import com.example.network.remote.ComroApi
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
}