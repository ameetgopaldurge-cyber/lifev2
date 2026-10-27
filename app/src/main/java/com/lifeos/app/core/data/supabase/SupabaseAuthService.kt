package com.lifeos.app.core.data.supabase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AuthState {
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Authenticated(val email: String, val userId: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

/**
 * Service managing authentication state. Ready to plug into io.github.jan.supabase:gotrue-kt
 */
class SupabaseAuthService {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signInWithEmail(email: String, pass: String) {
        _authState.value = AuthState.Loading
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Please enter both email and password")
            return
        }
        if (!email.contains("@")) {
            _authState.value = AuthState.Error("Invalid email address format")
            return
        }
        // Simulated local auth until Supabase credentials are configured
        _authState.value = AuthState.Authenticated(email = email, userId = "usr_${System.currentTimeMillis()}")
    }

    fun signUpWithEmail(email: String, pass: String) {
        _authState.value = AuthState.Loading
        if (pass.length < 6) {
            _authState.value = AuthState.Error("Password must be at least 6 characters")
            return
        }
        _authState.value = AuthState.Authenticated(email = email, userId = "usr_${System.currentTimeMillis()}")
    }

    fun signOut() {
        _authState.value = AuthState.Unauthenticated
    }
}
