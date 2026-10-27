package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface UserRepository {
    fun getUserProfile(): Flow<UserProfile>
    suspend fun updateCurrency(symbol: String, code: String)
    suspend fun updateTheme(isDark: Boolean)
    suspend fun updateProfile(name: String, email: String)
}

class InMemoryUserRepository : UserRepository {
    private val _profile = MutableStateFlow(UserProfile())

    override fun getUserProfile(): Flow<UserProfile> = _profile

    override suspend fun updateCurrency(symbol: String, code: String) {
        _profile.value = _profile.value.copy(currencySymbol = symbol, currencyCode = code)
    }

    override suspend fun updateTheme(isDark: Boolean) {
        _profile.value = _profile.value.copy(isDarkMode = isDark)
    }

    override suspend fun updateProfile(name: String, email: String) {
        _profile.value = _profile.value.copy(fullName = name, email = email)
    }
}
