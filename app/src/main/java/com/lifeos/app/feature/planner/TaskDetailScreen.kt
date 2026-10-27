package com.lifeos.app.feature.planner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun TaskDetailScreen(
    taskId: String,
    viewModel: PlannerViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val task = state.tasks.find { it.id == taskId }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    if (showDeleteConfirm) {
        LifeOSConfirmDialog(
            title = "Delete Task?",
            message = "Are you sure you want to delete this task? This action cannot be undone.",
            onConfirm = {
                showDeleteConfirm = false
                viewModel.deleteTask(taskId)
                onNavigateBack()
            },
            onDismiss = { showDeleteConfirm = false }
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
                LifeOSIconButton(
                    icon = Icons.Default.ArrowBack,
                    onClick = onNavigateBack
                )
                LifeOSIconButton(
                    icon = Icons.Outlined.Delete,
                    tint = LifeOSError,
                    onClick = { showDeleteConfirm = true }
                )
            }
        }
    ) { paddingValues ->
        if (task == null) {
            LifeOSEmptyState(
                title = "Task not found",
                description = "This task may have been removed.",
                actionButtonText = "Back to Planner",
                onActionClick = onNavigateBack
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    LifeOSPriorityBadge(priority = task.priority.name)
                    if (task.linkedArea != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        LifeOSTagBadge(text = task.linkedArea.title, color = task.linkedArea.color)
                    }
                }

                Text(
                    text = task.title,
                    style = LifeOSTypography.displayMedium,
                    color = LifeOSTextPrimary
                )

                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Schedule", style = LifeOSTypography.labelMedium, color = LifeOSTextSecondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${task.dueDate} at ${task.dueTime}",
                        style = LifeOSTypography.titleSmall,
                        color = LifeOSTextPrimary
                    )
                }

                if (task.description.isNotBlank()) {
                    LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Notes & Context", style = LifeOSTypography.labelMedium, color = LifeOSTextSecondary)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = task.description,
                            style = LifeOSTypography.bodyLarge,
                            color = LifeOSTextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                LifeOSPrimaryButton(
                    text = if (task.isCompleted) "Mark Incomplete" else "Mark Complete",
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = if (task.isCompleted) LifeOSSecondarySurface else LifeOSPrimaryAccent,
                    contentColor = if (task.isCompleted) LifeOSTextPrimary else LifeOSBackground,
                    onClick = {
                        viewModel.toggleTask(task.id)
                        onNavigateBack()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
