package com.lifeos.app.feature.goals

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
import com.lifeos.app.core.model.LifeArea

@Composable
fun AddGoalScreen(
    viewModel: GoalsViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedArea by remember { mutableStateOf(LifeArea.SKILLS) }
    var targetDate by remember { mutableStateOf("Dec 31") }
    var milestonesText by remember { mutableStateOf("Phase 1 Complete, MVP Released, Production Scale") }

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
                    text = "Create Strategic Goal",
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
            LifeOSTextField(
                value = title,
                onValueChange = { title = it },
                label = "Goal Title",
                placeholder = "e.g. Master Kotlin & Compose Architecture"
            )

            LifeOSTextField(
                value = description,
                onValueChange = { description = it },
                label = "Why this goal matters",
                placeholder = "Target outcome & motivation..."
            )

            Text(text = "Domain Area", style = LifeOSTypography.labelMedium, color = LifeOSTextSecondary)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                LifeArea.values().take(3).forEach { area ->
                    LifeOSFilterChip(
                        selected = selectedArea == area,
                        onClick = { selectedArea = area },
                        label = area.title.substringBefore(" ")
                    )
                }
            }

            LifeOSTextField(
                value = targetDate,
                onValueChange = { targetDate = it },
                label = "Target Completion Date"
            )

            LifeOSTextField(
                value = milestonesText,
                onValueChange = { milestonesText = it },
                label = "Key Milestones (comma separated)"
            )

            Spacer(modifier = Modifier.weight(1f))

            LifeOSPrimaryButton(
                text = "Save Goal",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (title.isNotBlank()) {
                        val milestones = milestonesText.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        viewModel.addGoal(title, description, selectedArea, targetDate, milestones)
                        onNavigateBack()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
