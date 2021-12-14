package com.ats.webservice.domain.model.login


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("deviceCode")
    val deviceCode: Int,
    @SerializedName("nickName")
    val nickName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("tokenNo")
    val tokenNo: String
)