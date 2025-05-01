package com.wolfgang.tradetrail.core.data.repository

import com.wolfgang.tradetrail.core.data.model.LoginBody
import com.wolfgang.tradetrail.core.data.model.LoginResponse
import com.wolfgang.tradetrail.core.data.remote.AuthApi
import com.wolfgang.tradetrail.core.data.session.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

interface AuthRepository {
    suspend fun login(username: String, password: String): LoginResponse

    suspend fun logout()
}

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {
    override suspend fun login(username: String, password: String): LoginResponse {
        val body = LoginBody(username, password)
        api.login(body).also {
            sessionManager.saveSession(it)
            return it
        }
    }

    override suspend fun logout() {
        sessionManager.clear()
    }
}