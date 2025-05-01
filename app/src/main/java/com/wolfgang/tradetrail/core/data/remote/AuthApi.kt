package com.wolfgang.tradetrail.core.data.remote

import com.wolfgang.tradetrail.core.data.model.LoginResponse
import com.wolfgang.tradetrail.core.data.model.LoginBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body body: LoginBody
    ): LoginResponse
}