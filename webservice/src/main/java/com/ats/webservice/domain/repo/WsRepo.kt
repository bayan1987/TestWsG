package com.ats.webservice.domain.repo

import com.ats.webservice.domain.model.login.LoginRequest
import com.ats.webservice.domain.model.login.LoginResponse
import retrofit2.Response

interface WsRepo {

    suspend fun login( request: LoginRequest) : Response<LoginResponse>
}