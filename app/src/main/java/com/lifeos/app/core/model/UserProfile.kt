package com.lifeos.app.core.model

data class UserProfile(
    val id: String = "user_demo_01",
    val fullName: String = "Alex Rivera",
    val email: String = "alex.rivera@lifeos.dev",
    val currencySymbol: String = "₹",
    val currencyCode: String = "INR",
    val isDarkMode: Boolean = true,
    val startOfWeek: String = "Monday",
    val isAuthenticated: Boolean = false
)
