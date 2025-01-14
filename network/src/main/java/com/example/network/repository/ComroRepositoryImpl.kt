package com.example.network.repository

import android.util.Log
import com.example.network.NetworkResult
import com.example.network.apiModel.Certificate
import com.example.network.apiModel.Education
import com.example.network.apiModel.HomeResponse
import com.example.network.apiModel.LoginResponse
import com.example.network.apiModel.Skill
import com.example.network.remote.ComroApi
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class ComroRepositoryImpl @Inject constructor(private val api:ComroApi) :ComroRepository {
    /*
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
                              //  var MSG = it.get("message").asString
                                successCallback(NetworkResult.Success<String>(it.get("message").asString))
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

     */

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
        try {
            api.apiRegistration(firstName, lastName, email, password, userType, mobile, country)
                .apply {
                    if (isSuccessful) {
                        body()?.let {
                            if (it.get("status").asString == "success") {

                                successCallback(NetworkResult.Success<String>(it.get("message").asString))

                            } else if (it.get("status").asString == "error") {
//
                                val message = it.get("message").asString
                                if (message != null) {
                                    successCallback(NetworkResult.Error<String>(message))
                                }

                            } else {
                                successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
                            }
                        }
                            ?: successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
                    } else {
                        try {
                            val jsonObj = this.errorBody()?.string()?.let { JSONObject(it) }
                            successCallback(
                                NetworkResult.Error<String>(
                                    jsonObj?.getString("message")
                                        ?: "There was an unknown error. Check your connection and try again."
                                )
                            )
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
                        }
                    }
                }
        } catch (e: HttpException) {
            if (e.code().toString() == "401") {
            }
            successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
        } catch (e: IOException) {
            successCallback(NetworkResult.Error<String>("There was an unknown error. Check your connection, and try again."))
        } catch (e: Exception) {
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

    override suspend fun apiForgotPasswordSendRequest(
        email: String,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiForgotPasswordSendRequest(email).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asBoolean){
                            var massage = it.get("message").asString

                            successCallback(NetworkResult.Success<String>(massage))

                        }
                        else if(it.get("status").asBoolean == false){

                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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

    override suspend fun apiResetPassword(
        user_id: String,
        old_password: String,
        new_password: String,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiResetPassword(user_id,old_password,new_password).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("success").asBoolean){
                            var massage = it.get("message").asString

                            successCallback(NetworkResult.Success<String>(massage))

                        }
                        else if(it.get("success").asBoolean == false){

                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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

    override suspend fun apiAddExperience(

        user_id: String,
        profile_id: String,
        company: String,
        title: String,
        location: String,
        country: String,
        currently_prof: String,
        start_date: String,
        end_date: String,

        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiAddExperience(user_id,profile_id,company,title,location,country,currently_prof,start_date,end_date).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString == "success"){



                            val massege : String = "Experience details add successfully"
                            successCallback(NetworkResult.Success<String>(massege))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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

    override suspend fun apiUpdateExperience(
        id: String,
        user_id: String,
        profile_id: String,
        company: String,
        title: String,
        location: String,
        country: String,
        currently_prof: String,
        start_date: String,
        end_date: String,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiUpdateExperience(id,user_id,profile_id,company,title,location,country,currently_prof,start_date,end_date).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString == "success"){

                            val massege : String = "Experience details update successfully"
                            successCallback(NetworkResult.Success<String>(massege))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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

    override suspend fun apiAddEducation(
        user_id: String,
        profile_id: String,
        school: String,
        degree: String,
        fieldstudy: String,
        country: String,
        start_date: String,
        end_date: String,
        description: String,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiAddEducation(user_id,profile_id,school,degree,fieldstudy,country,start_date,end_date,description).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString == "success"){

                            var massage : String = "Education details add successfully"

                            successCallback(NetworkResult.Success<String>(massage))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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

    override suspend fun apiUpdateEducation(
        id: String,
        user_id: String,
        school: String,
        degree: String,
        fieldstudy: String,
        country: String,
        start_date: String,
        end_date: String,
        description: String,
        successCallback: (response: NetworkResult<Pair<String, Int>>) -> Unit
    ) {
        try{
            api.apiUpdateEducation(id,user_id,school,degree,fieldstudy,country,start_date,end_date,description).apply {
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

    override suspend fun apiAddPortfolio(
        user_id: RequestBody?,
        profile_id: RequestBody?,
        title: RequestBody?,
        role: RequestBody?,
        description: RequestBody?,
        images: ArrayList<MultipartBody.Part>?,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiAddPortfolio(user_id,profile_id,title,role,description,images).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString == "success"){



                            // successCallback(NetworkResult.Success<String>())

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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

    override suspend fun apiUpdatePortfolio(
        id: RequestBody?,
        user_id: RequestBody?,
        profile_id: RequestBody?,
        title: RequestBody?,
        role: RequestBody?,
        description: RequestBody?,
        images: ArrayList<MultipartBody.Part>?,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun apiAddCertificate(
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
    ) {
        try{
            api.apiAddCertificate(user_id,profile_id,certificate_name,certificate_completion_id,
                certificate_url,start_date,end_date,certificate_prof,file).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString.equals("success")){


                            var massege : String = "Certificate  Add Successfully"

                            successCallback(NetworkResult.Success<String>(massege))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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
            successCallback(NetworkResult.Error("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: IOException) {
            successCallback(NetworkResult.Error("There was an unknown error. Check your connection, and try again."))
        }
        catch (e: Exception) {
            successCallback(NetworkResult.Error("There was an unknown error. Check your connection, and try again."))
        }
    }

    override suspend fun apiDeleteEducation(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiDeleteEducation(jsonObject).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString.equals("success")){
                            successCallback(NetworkResult.Success(it.get("message").asString))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error(message))
                            }

                        }
                        else{
                            successCallback(NetworkResult.Error("1There was an unknown error. Check your connection, and try again."))
                        }
                    }?: successCallback(NetworkResult.Error("2There was an unknown error. Check your connection, and try again."))
                }
                else {
                    try {
                        val jsonObj = this.errorBody()?.string()?.let { JSONObject(it) }
                        successCallback(
                            NetworkResult.Error(jsonObj?.getString("message")
                                ?: "3There was an unknown error. Check your connection and try again."
                            )
                        )
                    }
                    catch (e: JSONException) {
                        e.printStackTrace()
                        successCallback(NetworkResult.Error("4There was an unknown error. Check your connection, and try again."))
                    }
                }
            }
        }
        catch (e: HttpException) {
            if (e.code().toString() == "401") {
            }
            successCallback(NetworkResult.Error("5There was an unknown error. Check your connection, and try again."))
        }
        catch (e: IOException) {
            successCallback(NetworkResult.Error("6There was an unknown error. Check your connection, and try again."))
        }
        catch (e: Exception) {
            successCallback(NetworkResult.Error("7There was an unknown error. Check your connection, and try again."))
        }
    }


    override suspend fun apiGetEducation(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiGetEducation(jsonObject).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString.equals("success")){


                            successCallback(NetworkResult.Success<String>(it.get("education").asJsonArray.toString()))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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


    override suspend fun apiAddSkill(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiAddSkill(jsonObject).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString.equals("success")){
                            Log.d("skill.....", "skill")

                            var massege : String = "Skill  Add Successfully"

                            successCallback(NetworkResult.Success<String>(massege))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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

    override suspend fun apiResume(
        user_id: RequestBody,
        file: MultipartBody.Part,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiResume(user_id,file).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString.equals("success")){


                            var massege : String = "Resume  Add Successfully"

                            successCallback(NetworkResult.Success<String>(massege))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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


    override suspend fun apiChoosePlan(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiChoosePlan(jsonObject).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString.equals("success")){


                            successCallback(NetworkResult.Success<String>(it.get("message").asString))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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




    override suspend fun apiGetExperience(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiGetExperience(jsonObject).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString.equals("success")){


                            successCallback(NetworkResult.Success<String>(it.get("professional_experience").asJsonArray.toString()))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
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



    override suspend fun apiDeleteExperience(
        jsonObject : JsonObject,
        successCallback: (response: NetworkResult<String>) -> Unit
    ) {
        try{
            api.apiDeleteExperience(jsonObject).apply {
                if(isSuccessful){
                    body()?.let {
                        if(it.get("status").asString.equals("success")){
                            successCallback(NetworkResult.Success<String>(it.get("message").asString))

                        }
                        else if(it.get("status").asString == "error"){
//
                            val message = it.get("message").asString
                            if (message != null){
                                successCallback(NetworkResult.Error<String>(message))
                            }

                        }
                        else{
                            successCallback(NetworkResult.Error<String>("1There was an unknown error. Check your connection, and try again."))
                        }
                    }?: successCallback(NetworkResult.Error<String>("2There was an unknown error. Check your connection, and try again."))
                }
                else {
                    try {
                        val jsonObj = this.errorBody()?.string()?.let { JSONObject(it) }
                        successCallback(
                            NetworkResult.Error<String>(jsonObj?.getString("message")
                                ?: "3There was an unknown error. Check your connection and try again."
                            )
                        )
                    }
                    catch (e: JSONException) {
                        e.printStackTrace()
                        successCallback(NetworkResult.Error<String>("4There was an unknown error. Check your connection, and try again."))
                    }
                }
            }
        }
        catch (e: HttpException) {
            if (e.code().toString() == "401") {
            }
            successCallback(NetworkResult.Error<String>("5There was an unknown error. Check your connection, and try again."))
        }
        catch (e: IOException) {
            successCallback(NetworkResult.Error<String>("6There was an unknown error. Check your connection, and try again."))
        }
        catch (e: Exception) {
            successCallback(NetworkResult.Error<String>("7There was an unknown error. Check your connection, and try again."))
        }
    }



    override suspend fun apitalent(
        userId: String,
        page: Int,
        successCallback: (response: NetworkResult<MutableList<HomeResponse>>) -> Unit
    ) {
        try {
            api.apitalent(userId, page).apply {
                if (isSuccessful) {
                    body()?.let {
                        val data = it.get("data").asJsonArray
                        val list = mutableListOf<HomeResponse>()

                        data.forEach { jsonElement ->
                            val obj = jsonElement.asJsonObject

                            val homeResponse = HomeResponse().apply {
                                if (obj.has("age") && obj["age"].isJsonNull == false) {
                                    age = obj["age"].asString
                                }
                                if (obj.has("average_rating") && obj["average_rating"].isJsonNull == false) {
                                    average_rating = obj["average_rating"].asInt
                                }
                                if (obj.has("country") && obj["country"].isJsonNull == false) {
                                    country = obj["country"].asString
                                }
                                if (obj.has("email") && obj["email"].isJsonNull == false) {
                                    email = obj["email"].asString
                                }
                                if (obj.has("experience_q") && obj["experience_q"].isJsonNull == false) {
                                    experience_q = obj["experience_q"].asString
                                }
                                if (obj.has("firstName") && obj["firstName"].isJsonNull == false) {
                                    firstName = obj["firstName"].asString
                                }
                                if (obj.has("gender") && obj["gender"].isJsonNull == false) {
                                    gender = obj["gender"].asString
                                }
                                if (obj.has("goal_q") && obj["goal_q"].isJsonNull == false) {
                                    goal_q = obj["goal_q"].asString
                                }
                                if (obj.has("id") && obj["id"].isJsonNull == false) {
                                    id = obj["id"].asInt
                                }
                                if (obj.has("image") && obj["image"].isJsonNull == false) {
                                    image = obj["image"].asString
                                }
                                if (obj.has("is_contacted") && obj["is_contacted"].isJsonNull == false) {
                                    is_contacted = obj["is_contacted"].asBoolean
                                }
                                if (obj.has("lastName") && obj["lastName"].isJsonNull == false) {
                                    lastName = obj["lastName"].asString
                                }
                                if (obj.has("mobile") && obj["mobile"].isJsonNull == false) {
                                    mobile = obj["mobile"].asString
                                }
                                if (obj.has("rating_count") && obj["rating_count"].isJsonNull == false) {
                                    rating_count = obj["rating_count"].asInt
                                }
                                if (obj.has("resume") && obj["resume"].isJsonNull == false) {
                                    resume = obj["resume"].asString
                                }
                                if (obj.has("status") && obj["status"].isJsonNull == false) {
                                    status = obj["status"].asString
                                }
                                if (obj.has("title") && obj["title"].isJsonNull == false) {
                                    title = obj["title"].asString
                                }
                                if (obj.has("userType") && obj["userType"].isJsonNull == false) {
                                    userType = obj["userType"].asString
                                }
                                if (obj.has("work_preference_q") && obj["work_preference_q"].isJsonNull == false) {
                                    work_preference_q = obj["work_preference_q"].asString
                                }



                            }

                            // Process skills
                            homeResponse.skills = obj["skills"]?.asJsonArray?.map { skill ->
                                val skillObj = skill.asJsonObject
                                Skill().apply {
                                    if (skillObj.has("created_at") && skillObj["created_at"].isJsonNull == false) {
                                        created_at = skillObj["created_at"].asString
                                    }
                                    if (skillObj.has("id") && skillObj["id"].isJsonNull == false) {
                                        id = skillObj["id"].asInt
                                    }
                                    if (skillObj.has("profile_id") && skillObj["profile_id"].isJsonNull == false) {
                                        profile_id = skillObj["profile_id"].asInt
                                    }
                                    if (skillObj.has("skill_user") && skillObj["skill_user"].isJsonNull == false) {
                                        skill_user = skillObj["skill_user"].asString
                                    }
                                    if (skillObj.has("updated_at") && skillObj["updated_at"].isJsonNull == false) {
                                        updated_at = skillObj["updated_at"].asString
                                    }
                                    if (skillObj.has("user_id") && skillObj["user_id"].isJsonNull == false) {
                                        user_id = skillObj["user_id"].asInt
                                    }
                                }
                            }?.toMutableList() ?: mutableListOf()

                            // Process certificates
                            homeResponse.certificates = obj["certificates"]?.asJsonArray?.map { cert ->
                                val certObj = cert.asJsonObject

                                Certificate().apply {
                                    if (certObj.has("certificate_completion_id") && certObj["certificate_completion_id"].isJsonNull == false) {
                                        certificate_completion_id = certObj["certificate_completion_id"].asString
                                    }

                                    if (certObj.has("certificate_name") && certObj["certificate_name"].isJsonNull == false) {
                                        certificate_name = certObj["certificate_name"].asString
                                    }
                                    if (certObj.has("certificate_prof") && certObj["certificate_prof"].isJsonNull == false) {
                                        certificate_prof = certObj["certificate_prof"].asInt
                                    }
                                    if (certObj.has("certificate_url") && certObj["certificate_url"].isJsonNull == false) {
                                        certificate_url = certObj["certificate_url"].asString
                                    }
                                    if (certObj.has("created_at") && certObj["created_at"].isJsonNull == false) {
                                        created_at = certObj["created_at"].asString
                                    }

                                    if (certObj.has("end_date") && certObj["end_date"].isJsonNull == false) {
                                        end_date = certObj["end_date"].asString
                                    }
                                    if (certObj.has("id") && certObj["id"].isJsonNull == false) {
                                        id = certObj["id"].asInt
                                    }
                                    if (certObj.has("image") && certObj["image"].isJsonNull == false) {
                                        image = certObj["image"].asString
                                    }
                                    if (certObj.has("profile_id") && certObj["profile_id"].isJsonNull == false) {
                                        profile_id = certObj["profile_id"].asInt
                                    }
                                    if (certObj.has("start_date") && certObj["start_date"].isJsonNull == false) {
                                        start_date = certObj["start_date"].asString
                                    }
                                    if (certObj.has("updated_at") && certObj["updated_at"].isJsonNull == false) {
                                        updated_at = certObj["updated_at"].asString
                                    }

                                    if (certObj.has("user_id") && certObj["user_id"].isJsonNull == false) {
                                        user_id = certObj["user_id"].asInt
                                    }

                                }



                            }?.toMutableList() ?: mutableListOf()

                            // Process education
                            homeResponse.education = obj["education"]?.asJsonArray?.map { edu ->
                                val eduObj = edu.asJsonObject
                                Education().apply {
                                    if (eduObj.has("user_id") && eduObj["user_id"].isJsonNull == false) {
                                        user_id = eduObj["user_id"].asInt
                                    }
                                    if (eduObj.has("created_at") && eduObj["created_at"].isJsonNull == false) {
                                        created_at = eduObj["created_at"].asString
                                    }
                                    if (eduObj.has("degree") && eduObj["degree"].isJsonNull == false) {
                                        degree = eduObj["degree"].asString
                                    }
                                    if (eduObj.has("description") && eduObj["description"].isJsonNull == false) {
                                        degree = eduObj["description"].asString
                                    }
                                    if (eduObj.has("end_date") && eduObj["end_date"].isJsonNull == false) {
                                        end_date = eduObj["end_date"].asString
                                    }
                                    if (eduObj.has("fieldstudy") && eduObj["fieldstudy"].isJsonNull == false) {
                                        fieldstudy = eduObj["fieldstudy"].asString
                                    }
                                    if (eduObj.has("grade") && eduObj["grade"].isJsonNull == false) {
                                        grade = eduObj["grade"].asString
                                    }
                                    if (eduObj.has("id") && eduObj["id"].isJsonNull == false) {
                                        id = eduObj["id"].asInt
                                    }
                                    if (eduObj.has("profile_id") && eduObj["profile_id"].isJsonNull == false) {
                                        profile_id = eduObj["profile_id"].asInt
                                    }
                                    if (eduObj.has("sch_uni") && eduObj["sch_uni"].isJsonNull == false) {
                                        sch_uni = eduObj["sch_uni"].asString
                                    }
                                    if (eduObj.has("start_date") && eduObj["start_date"].isJsonNull == false) {
                                        start_date = eduObj["start_date"].asString
                                    }
                                    if (eduObj.has("updated_at") && eduObj["updated_at"].isJsonNull == false) {
                                        updated_at = eduObj["updated_at"].asString
                                    }


                                }

                            }?.toMutableList() ?: mutableListOf()

                            list.add(homeResponse)
                        }

                        successCallback(NetworkResult.Success(list))
                    }
                        ?: successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection and try again."))
                } else {
                    try {
                        val jsonObj = this.errorBody()?.string()?.let { JSONObject(it) }
                        successCallback(
                            NetworkResult.Error<MutableList<HomeResponse>>(
                                jsonObj?.getString("message")
                                    ?: "There was an unknown error. Check your connection and try again."
                            )
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
                    }
                }


            }
        } catch (e: HttpException) {
            if (e.code().toString() == "401") {

            }
            successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
        } catch (e: IOException) {
            successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
        } catch (e: Exception) {
            successCallback(NetworkResult.Error<MutableList<HomeResponse>>("There was an unknown error. Check your connection, and try again."))
        }


    }








}