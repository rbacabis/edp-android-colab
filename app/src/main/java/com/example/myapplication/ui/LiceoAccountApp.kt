package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// GIVEN (read it, do not change it)
@Composable
fun LiceoAccountApp(vm: AuthViewModel = viewModel()) {
    var screen by rememberSaveable { mutableStateOf("login") }
    val state = vm.uiState
    val isAirplaneMode = vm.isAirplaneMode

    Column(modifier = Modifier.fillMaxSize()) {
        // Status Bar area with Airplane Mode Toggle Icon
        Surface(
            color = if (isAirplaneMode) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isAirplaneMode) {
                    Text(
                        text = "✈️ Airplane Mode ON (No Internet)",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                } else {
                    Text(
                        text = "Tap ✈️ to simulate Airplane Mode",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = { vm.toggleAirplaneMode() }) {
                    Text(
                        text = "✈️",
                        fontSize = 20.sp
                    )
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            if (state is AuthUiState.LoggedIn) {
                ProfileScreen(
                    user = state.user,
                    onLogout = { vm.logout(); screen = "login" }
                )
            } else if (screen == "register") {
                RegisterScreen(
                    state = state,
                    onCreate = vm::register,
                    onGoToLogin = { vm.clearMessage(); screen = "login" }
                )
            } else {
                LoginScreen(
                    state = state,
                    onLogin = vm::login,
                    onGoToRegister = { vm.clearMessage(); screen = "register" }
                )
            }
        }
    }
}
