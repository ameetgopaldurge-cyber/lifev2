package com.lifeos.app.feature.planner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun PlannerScreen(
    viewModel: PlannerViewModel,
    onNavigateToAddTask: () -> Unit,
    onNavigateToTaskDetail: (String) -> Unit,
    onNavigateToFocusSession: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val filterTabs = listOf("All", "High", "Medium", "Low", "Overdue", "Completed")
    val daysOfWeek = listOf("Mon 23", "Tue 24", "Wed 25", "Thu 26", "Fri 27", "Sat 28", "Sun 29")

    Scaffold(
        containerColor = LifeOSBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddTask,
                containerColor = LifeOSPrimaryAccent,
                contentColor = LifeOSBackground,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "New Task")
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Smart Planner",
                            style = LifeOSTypography.displayMedium,
                            color = LifeOSTextPrimary
                        )
                        Text(
                            text = "Turn ambition into daily actions",
                            style = LifeOSTypography.bodyMedium,
                            color = LifeOSTextSecondary
                        )
                    }
                    LifeOSPrimaryButton(
                        text = "Focus",
                        icon = Icons.Outlined.Timer,
                        onClick = onNavigateToFocusSession
                    )
                }
            }

            // Weekly Day Strip
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(daysOfWeek) { day ->
                        val isSelected = day.contains("26") || day.contains("Thu")
                        Box(
                            modifier = Modifier
                                .clip(LifeOSShapes.small)
                                .background(if (isSelected) LifeOSPrimaryAccent else LifeOSSecondarySurface)
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day,
                                style = LifeOSTypography.labelMedium,
                                color = if (isSelected) LifeOSBackground else LifeOSTextPrimary
                            )
                        }
                    }
                }
            }

            // Search Bar
            item {
                LifeOSTextField(
                    value = state.searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = "Search tasks...",
                    leadingIcon = Icons.Outlined.Search
                )
            }

            // Priority & Status Filters
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filterTabs) { tab ->
                        LifeOSFilterChip(
                            selected = state.selectedFilter == tab,
                            onClick = { viewModel.setFilter(tab) },
                            label = tab
                        )
                    }
                }
            }

            // Task List
            if (state.filteredTasks.isEmpty()) {
                item {
                    LifeOSEmptyState(
                        title = "No tasks found",
                        description = "You're all clear! Add a task to stay consistent.",
                        actionButtonText = "Create Task",
                        onActionClick = onNavigateToAddTask
                    )
                }
            } else {
                items(state.filteredTasks) { task ->
                    LifeOSCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onNavigateToTaskDetail(task.id) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = task.isCompleted,
                                onCheckedChange = { viewModel.toggleTask(task.id) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = LifeOSPrimaryAccent,
                                    uncheckedColor = LifeOSBorder,
                                    checkmarkColor = LifeOSBackground
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = task.title,
                                    style = LifeOSTypography.titleSmall,
                                    color = if (task.isCompleted) LifeOSTextTertiary else LifeOSTextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${task.dueDate} • ${task.dueTime}",
                                        style = LifeOSTypography.bodySmall,
                                        color = if (task.isOverdue) LifeOSError else LifeOSTextSecondary
                                    )
                                    if (task.linkedArea != null) {
                                        Text(text = " • ", style = LifeOSTypography.bodySmall, color = LifeOSTextTertiary)
                                        Text(
                                            text = task.linkedArea.title.substringBefore(" "),
                                            style = LifeOSTypography.labelSmall,
                                            color = task.linkedArea.color
                                        )
                                    }
                                }
                            }
                            LifeOSPriorityBadge(priority = task.priority.name)
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
