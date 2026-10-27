package com.lifeos.app.feature.discipline

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun DisciplineHabitsScreen(
    viewModel: DisciplineViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var showReflectionDialog by remember { mutableStateOf(false) }
    var highlightText by remember { mutableStateOf("") }
    var frictionText by remember { mutableStateOf("") }
    var tomorrowPriorityText by remember { mutableStateOf("") }

    if (showReflectionDialog) {
        AlertDialog(
            onDismissRequest = { showReflectionDialog = false },
            containerColor = LifeOSCardBackground,
            title = { Text(text = "Daily Reflection", style = LifeOSTypography.titleMedium, color = LifeOSTextPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    LifeOSTextField(
                        value = highlightText,
                        onValueChange = { highlightText = it },
                        label = "What went well today?",
                        placeholder = "Key victory or focus moment"
                    )
                    LifeOSTextField(
                        value = frictionText,
                        onValueChange = { frictionText = it },
                        label = "What caused friction?",
                        placeholder = "Distraction or obstacle"
                    )
                    LifeOSTextField(
                        value = tomorrowPriorityText,
                        onValueChange = { tomorrowPriorityText = it },
                        label = "#1 Priority for Tomorrow",
                        placeholder = "Single focus task"
                    )
                }
            },
            confirmButton = {
                LifeOSPrimaryButton(
                    text = "Save Reflection",
                    onClick = {
                        if (highlightText.isNotBlank()) {
                            viewModel.addReflection(highlightText, frictionText, tomorrowPriorityText)
                            highlightText = ""
                            frictionText = ""
                            tomorrowPriorityText = ""
                            showReflectionDialog = false
                        }
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { showReflectionDialog = false }) {
                    Text(text = "Cancel", color = LifeOSTextSecondary)
                }
            }
        )
    }

    Scaffold(
        containerColor = LifeOSBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LifeOSIconButton(
                        icon = Icons.Default.ArrowBack,
                        onClick = onNavigateBack
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Discipline & Habits",
                            style = LifeOSTypography.titleLarge,
                            color = LifeOSTextPrimary
                        )
                        Text(
                            text = "Habits, Streaks & Daily Routine",
                            style = LifeOSTypography.bodySmall,
                            color = LifeOSTextSecondary
                        )
                    }
                }
                LifeOSPrimaryButton(
                    text = "Reflect",
                    onClick = { showReflectionDialog = true },
                    modifier = Modifier.height(36.dp)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Habit Tracker with Streaks & Checkmark
            item {
                Text(text = "Active Habit Streaks", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
            }

            items(state.habits) { habit ->
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = habit.isCompletedToday,
                            onCheckedChange = { viewModel.toggleHabit(habit.id) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = LifeOSPrimaryAccent,
                                uncheckedColor = LifeOSBorder
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = habit.name, style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                            Text(
                                text = "🔥 ${habit.streakDays} day streak • Best: ${habit.bestStreak} days",
                                style = LifeOSTypography.bodySmall,
                                color = LifeOSWarning
                            )
                        }
                        LifeOSTagBadge(
                            text = if (habit.isCompletedToday) "Done" else "Pending",
                            color = if (habit.isCompletedToday) LifeOSSuccess else LifeOSSecondaryAccent
                        )
                    }
                }
            }

            // Daily Routine Checklist
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Daily Routine", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
            }

            items(state.routines) { routine ->
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = routine.isCompleted,
                            onCheckedChange = { viewModel.toggleRoutine(routine.id) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = LifeOSPrimaryAccent,
                                uncheckedColor = LifeOSBorder
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = routine.title,
                            style = LifeOSTypography.bodyMedium,
                            color = if (routine.isCompleted) LifeOSTextTertiary else LifeOSTextPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        LifeOSTagBadge(
                            text = if (routine.isMorning) "Morning" else "Evening",
                            color = if (routine.isMorning) LifeOSWarning else LifeOSCollegeColor
                        )
                    }
                }
            }

            // Reflection Journal History
            if (state.reflections.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Reflection Journal", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
                }

                items(state.reflections) { ref ->
                    LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Date: ${ref.dateText}", style = LifeOSTypography.labelSmall, color = LifeOSPrimaryAccent)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Highlight: ${ref.highlight}", style = LifeOSTypography.bodyMedium, color = LifeOSTextPrimary)
                        if (ref.frictionPoint.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Friction: ${ref.frictionPoint}", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
