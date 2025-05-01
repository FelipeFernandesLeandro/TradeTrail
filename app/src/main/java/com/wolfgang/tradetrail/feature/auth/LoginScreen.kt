package com.wolfgang.tradetrail.feature.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(vm: LoginViewModel, onSuccess: () -> Unit) {
    val state = vm.uiState
    if (state.success) onSuccess()

    Column(Modifier.padding(24.dp)) {
        OutlinedTextField(
            value = state.username,
            onValueChange = vm::updateUser,
            label = { Text("Username") }
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = vm::updatePassword,
            label = { Text("Password") }
        )
        if (state.error != null) Text(state.error, color = MaterialTheme.colorScheme.error)
        Button(
            enabled = !state.isLoading,
            onClick = vm::login,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
            else Text("Login")
        }
    }
}