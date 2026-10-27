package com.lifeos.app.core.data.supabase

/**
 * Configuration placeholder for Supabase integration.
 * In production, populate these from BuildConfig / secure environment variables.
 */
object SupabaseConfig {
    const val SUPABASE_URL = "https://your-project.supabase.co"
    const val SUPABASE_ANON_KEY = "your-anon-key-here"

    // Integration status flag
    val isConfigured: Boolean
        get() = SUPABASE_URL != "https://your-project.supabase.co" && SUPABASE_ANON_KEY != "your-anon-key-here"
}
