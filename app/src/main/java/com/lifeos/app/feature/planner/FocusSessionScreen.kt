package com.lifeos.app.feature.planner

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun FocusSessionScreen(
    onNavigateBack: () -> Unit
) {
    var isRunning by remember { mutableStateOf(false) }
    var secondsRemaining by remember { mutableStateOf(25 * 60) }
    var distractionLog by remember { mutableStateOf("") }
    var recordedDistractions by remember { mutableStateOf(listOf<String>()) }

    val minutes = secondsRemaining / 60
    val seconds = secondsRemaining % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)
    val progress = (25 * 60 - secondsRemaining) / (25f * 60f)

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
                    text = "Focus Session",
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Glowing Focus Timer Ring
            LifeOSCircularProgressRing(
                percentage = progress.coerceIn(0f, 1f),
                ringSize = 220.dp,
                strokeWidth = 16.dp,
                label = if (isRunning) "Deep Focus Active" else "Ready to Focus",
                accentColor = LifeOSPrimaryAccent
            )

            Text(
                text = timeFormatted,
                style = LifeOSTypography.displayLarge.copy(fontSize = 44.sp),
                color = LifeOSTextPrimary
            )

            // Timer Controls
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                LifeOSIconButton(
                    icon = Icons.Default.Refresh,
                    onClick = {
                        isRunning = false
                        secondsRemaining = 25 * 60
                    }
                )
                LifeOSPrimaryButton(
                    text = if (isRunning) "Pause" else "Start Session",
                    icon = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    onClick = { isRunning = !isRunning }
                )
            }

            // Distraction Logger
            LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Distraction Log",
                    style = LifeOSTypography.titleSmall,
                    color = LifeOSTextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Notice a thought or impulse? Log it here and return to flow.",
                    style = LifeOSTypography.bodySmall,
                    color = LifeOSTextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    LifeOSTextField(
                        value = distractionLog,
                        onValueChange = { distractionLog = it },
                        placeholder = "e.g. Checked phone, searched news",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    LifeOSPrimaryButton(
                        text = "Log",
                        onClick = {
                            if (distractionLog.isNotBlank()) {
                                recordedDistractions = recordedDistractions + distractionLog
                                distractionLog = ""
                            }
                        }
                    )
                }
                if (recordedDistractions.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    recordedDistractions.forEach { item ->
                        Text(
                            text = "• $item",
                            style = LifeOSTypography.bodySmall,
                            color = LifeOSTextSecondary
                        )
                    }
                }
            }
        }
    }
}
