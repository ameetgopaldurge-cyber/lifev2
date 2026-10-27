package com.lifeos.app.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.UserRepository
import com.lifeos.app.core.data.supabase.AuthState
import com.lifeos.app.core.data.supabase.SupabaseAuthService
import com.lifeos.app.core.model.UserProfile
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ProfileUiState(
    val userProfile: UserProfile = UserProfile(),
    val authState: AuthState = AuthState.Unauthenticated,
    val isLoading: Boolean = false
)

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val authService: SupabaseAuthService
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = combine(
        userRepository.getUserProfile(),
        authService.authState
    ) { profile, auth ->
        ProfileUiState(
            userProfile = profile,
            authState = auth,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState(isLoading = true)
    )

    fun updateCurrency(symbol: String, code: String) {
        viewModelScope.launch {
            userRepository.updateCurrency(symbol, code)
        }
    }

    fun updateTheme(isDark: Boolean) {
        viewModelScope.launch {
            userRepository.updateTheme(isDark)
        }
    }

    fun signIn(email: String, pass: String) {
        authService.signInWithEmail(email, pass)
    }

    fun signUp(email: String, pass: String) {
        authService.signUpWithEmail(email, pass)
    }

    fun signOut() {
        authService.signOut()
    }
}
