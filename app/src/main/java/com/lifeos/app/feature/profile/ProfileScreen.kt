package com.lifeos.app.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToSettings: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(containerColor = LifeOSBackground) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profile & Settings",
                        style = LifeOSTypography.displayMedium,
                        color = LifeOSTextPrimary
                    )
                    LifeOSIconButton(
                        icon = Icons.Outlined.Settings,
                        onClick = onNavigateToSettings
                    )
                }
            }

            // User Profile Card
            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(LifeOSSecondarySurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = LifeOSPrimaryAccent,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = state.userProfile.fullName, style = LifeOSTypography.titleMedium, color = LifeOSTextPrimary)
                            Text(text = state.userProfile.email, style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))
                            LifeOSTagBadge(text = "LifeOS Founding Member", color = LifeOSPrimaryAccent)
                        }
                    }
                }
            }

            // Supabase Cloud Sync Card
            item {
                LifeOSCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderColor = LifeOSPrimaryAccent.copy(alpha = 0.3f),
                    onClick = onNavigateToAuth
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Supabase Cloud Sync", style = LifeOSTypography.titleMedium, color = LifeOSTextPrimary)
                            Text(text = "Connect email auth & real-time Postgres DB", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                        }
                        LifeOSTagBadge(text = "Configure", color = LifeOSPrimaryAccent)
                    }
                }
            }

            // Quick Preferences List
            item {
                Text(text = "Preferences", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
            }

            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Default Currency", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("₹ INR" to "₹", "$ USD" to "$", "€ EUR" to "€").forEach { (label, sym) ->
                            LifeOSFilterChip(
                                selected = state.userProfile.currencySymbol == sym,
                                onClick = { viewModel.updateCurrency(sym, label.substringAfter(" ")) },
                                label = label
                            )
                        }
                    }
                }
            }

            // About LifeOS
            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "About LifeOS", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "LifeOS v1.0.0 • Personal Operating System\nCrafted with Kotlin, Jetpack Compose, Material 3, and Coroutines.",
                        style = LifeOSTypography.bodySmall,
                        color = LifeOSTextSecondary
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
