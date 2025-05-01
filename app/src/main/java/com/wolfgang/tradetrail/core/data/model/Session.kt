package com.wolfgang.tradetrail.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val refreshToken: String,
    val id: Int,
    val username: String,
)

data class LoginBody(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30,
)