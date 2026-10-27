package com.lifeos.app.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun SettingsScreen(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var startOfWeek by remember { mutableStateOf("Monday") }

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
                    text = "Application Settings",
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
                Text(text = "Theme Appearance", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "LifeOS is optimized for high-contrast OLED dark theme (#101116).", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
            }

            LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Week Starts On", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Monday", "Sunday").forEach { day ->
                        LifeOSFilterChip(
                            selected = startOfWeek == day,
                            onClick = { startOfWeek = day },
                            label = day
                        )
                    }
                }
            }

            LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Data Export", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Export local tasks, habits, and transactions as JSON.", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                Spacer(modifier = Modifier.height(10.dp))
                LifeOSSecondaryButton(
                    text = "Export Data (JSON)",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
