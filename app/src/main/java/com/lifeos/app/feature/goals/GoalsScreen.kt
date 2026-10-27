package com.lifeos.app.feature.goals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*
import com.lifeos.app.core.model.LifeArea

@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel,
    onNavigateToAddGoal: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = LifeOSBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddGoal,
                containerColor = LifeOSPrimaryAccent,
                contentColor = LifeOSBackground,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Goal")
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Long-Term Goals",
                    style = LifeOSTypography.displayMedium,
                    color = LifeOSTextPrimary
                )
                Text(
                    text = "Track ambitions across all 6 life areas",
                    style = LifeOSTypography.bodyMedium,
                    color = LifeOSTextSecondary
                )
            }

            // Area Filter Strip
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        LifeOSFilterChip(
                            selected = state.selectedArea == null,
                            onClick = { viewModel.selectArea(null) },
                            label = "All Areas"
                        )
                    }
                    items(LifeArea.values()) { area ->
                        LifeOSFilterChip(
                            selected = state.selectedArea == area,
                            onClick = { viewModel.selectArea(area) },
                            label = area.title.substringBefore(" ")
                        )
                    }
                }
            }

            items(state.filteredGoals) { goal ->
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = goal.title, style = LifeOSTypography.titleMedium, color = LifeOSTextPrimary)
                        LifeOSTagBadge(text = goal.area.title.substringBefore(" "), color = goal.area.color)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = goal.description, style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Target: ${goal.targetDate}", style = LifeOSTypography.bodySmall, color = LifeOSTextTertiary)
                        Text(text = "${goal.progressPercentage}%", style = LifeOSTypography.labelMedium, color = LifeOSPrimaryAccent)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LifeOSProgressBar(
                        progress = goal.progressPercentage / 100f,
                        color = goal.area.color,
                        modifier = Modifier.fillMaxWidth().height(6.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Milestones checklist
                    Text(text = "Milestones:", style = LifeOSTypography.labelSmall, color = LifeOSTextSecondary)
                    goal.milestones.forEach { milestone ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = milestone.isCompleted,
                                onCheckedChange = { viewModel.toggleMilestone(goal.id, milestone.id) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = LifeOSPrimaryAccent,
                                    uncheckedColor = LifeOSBorder
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = milestone.title,
                                style = LifeOSTypography.bodySmall,
                                color = if (milestone.isCompleted) LifeOSTextTertiary else LifeOSTextPrimary
                            )
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
