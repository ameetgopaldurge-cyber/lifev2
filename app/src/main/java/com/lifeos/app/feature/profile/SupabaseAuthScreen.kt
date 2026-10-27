package com.lifeos.app.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.data.supabase.AuthState
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun SupabaseAuthScreen(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("alex.rivera@lifeos.dev") }
    var password by remember { mutableStateOf("secret123") }
    var isSignUp by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = LifeOSBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LifeOSIconButton(
                    icon = Icons.Default.ArrowBack,
                    onClick = onNavigateBack
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Supabase Authentication",
                    style = LifeOSTypography.titleLarge,
                    color = LifeOSTextPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Cloud Integration State", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                when (state.authState) {
                    is AuthState.Authenticated -> {
                        val auth = state.authState as AuthState.Authenticated
                        LifeOSTagBadge(text = "Authenticated as ${auth.email}", color = LifeOSSuccess)
                    }
                    is AuthState.Loading -> {
                        LifeOSLoadingPlaceholder(message = "Connecting to Supabase...")
                    }
                    is AuthState.Error -> {
                        val err = state.authState as AuthState.Error
                        LifeOSTagBadge(text = "Error: ${err.message}", color = LifeOSError)
                    }
                    else -> {
                        LifeOSTagBadge(text = "Local Offline Mode (Ready for Supabase)", color = LifeOSWarning)
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LifeOSFilterChip(
                    selected = !isSignUp,
                    onClick = { isSignUp = false },
                    label = "Sign In"
                )
                LifeOSFilterChip(
                    selected = isSignUp,
                    onClick = { isSignUp = true },
                    label = "Sign Up"
                )
            }

            LifeOSTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                placeholder = "user@domain.com"
            )

            LifeOSTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                placeholder = "••••••••"
            )

            LifeOSPrimaryButton(
                text = if (isSignUp) "Create Account" else "Sign In with Email",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (isSignUp) {
                        viewModel.signUp(email, password)
                    } else {
                        viewModel.signIn(email, password)
                    }
                }
            )

            if (state.authState is AuthState.Authenticated) {
                LifeOSOutlineButton(
                    text = "Sign Out",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.signOut() }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
