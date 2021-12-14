package com.ats.webservice.domain.usecases

import com.ats.webservice.domain.model.login.LoginRequest
import com.ats.webservice.domain.repo.WsRepo
import javax.inject.Inject

class LoginUseCase  @Inject constructor(val wsRepo: WsRepo) {
    suspend operator fun invoke(request: LoginRequest) = wsRepo.login(request)
}