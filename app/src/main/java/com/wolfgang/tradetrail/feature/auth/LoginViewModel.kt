package com.wolfgang.tradetrail.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfgang.tradetrail.core.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun updateUser(text: String) { uiState = uiState.copy(username = text) }
    fun updatePassword(text: String) { uiState = uiState.copy(password = text) }

    fun togglePasswordVisibility() { uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible) }

    fun login() = viewModelScope.launch {
        runCatching {
            uiState = uiState.copy(isLoading = true)
            repo.login(uiState.username, uiState.password)
        }
            .onSuccess { uiState = uiState.copy(success = true, isLoading = false, error = null) }
            .onFailure { uiState = uiState.copy(error = it.message, isLoading = false, success = false) }
    }
}