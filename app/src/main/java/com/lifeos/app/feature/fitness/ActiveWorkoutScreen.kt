package com.lifeos.app.feature.fitness

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun ActiveWorkoutScreen(
    viewModel: FitnessViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

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
                    text = "Workout Session",
                    style = LifeOSTypography.titleLarge,
                    color = LifeOSTextPrimary
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
            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Text(text = state.workoutPlan.title, style = LifeOSTypography.displayMedium, color = LifeOSTextPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Target: ${state.workoutPlan.targetCalories} Calories • 4 Exercises", style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                }
            }

            items(state.workoutPlan.exercises) { ex ->
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = ex.isCompleted,
                            onCheckedChange = { viewModel.toggleExercise(ex.id) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = LifeOSPrimaryAccent,
                                uncheckedColor = LifeOSBorder
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = ex.name,
                                style = LifeOSTypography.titleSmall,
                                color = if (ex.isCompleted) LifeOSTextTertiary else LifeOSTextPrimary
                            )
                            Text(
                                text = "${ex.targetSets} sets × ${ex.targetReps} reps • ${ex.weightKg} kg",
                                style = LifeOSTypography.bodySmall,
                                color = LifeOSTextSecondary
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                LifeOSPrimaryButton(
                    text = "Finish Workout",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onNavigateBack
                )
            }
        }
    }
}
