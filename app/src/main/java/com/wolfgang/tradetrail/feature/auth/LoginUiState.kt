package com.wolfgang.tradetrail.feature.auth

data class LoginUiState(
    val username: String = "emilys",
    val password: String = "emilyspass",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
