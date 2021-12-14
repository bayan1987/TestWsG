package com.ats.webservice.data.repoImpl

import com.ats.webservice.data.api.APIEndpoints
import com.ats.webservice.domain.model.login.LoginRequest
import com.ats.webservice.domain.model.login.LoginResponse
import com.ats.webservice.domain.repo.WsRepo
import retrofit2.Response

class RepoImpl (private val apiEndpoints: APIEndpoints) : WsRepo {

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        return apiEndpoints.login(request)
    }
}