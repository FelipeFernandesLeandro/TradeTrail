package com.wolfgang.tradetrail.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(vm: LoginViewModel, onSuccess: () -> Unit) {
    val state = vm.uiState
    if (state.success) onSuccess()

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.username,
            onValueChange = vm::updateUser,
            label = { Text("Username") }
        )
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = vm::updatePassword,
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.size(24.dp))
        Button(
            enabled = !state.isLoading,
            onClick = vm::login,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) CircularProgressIndicator(modifier = Modifier.size(16.dp))
            else Text("Login")
        }
        state.error?.let {
            Snackbar(modifier = Modifier.background(MaterialTheme.colorScheme.error)) {
                Text(it, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}