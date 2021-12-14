package com.ats.webservice.data.api

import com.ats.webservice.domain.model.login.LoginRequest
import com.ats.webservice.domain.model.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIEndpoints {

    @POST("authentication/patientLogin")
    suspend fun login(@Body request: LoginRequest) : Response<LoginResponse>

}