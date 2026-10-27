package com.lifeos.app.feature.planner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*
import com.lifeos.app.core.model.LifeArea
import com.lifeos.app.core.model.TaskPriority

@Composable
fun AddEditTaskScreen(
    viewModel: PlannerViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(TaskPriority.HIGH) }
    var dueDate by remember { mutableStateOf("Due Today") }
    var dueTime by remember { mutableStateOf("18:00") }
    var selectedArea by remember { mutableStateOf<LifeArea?>(LifeArea.COLLEGE) }
    var recurringRule by remember { mutableStateOf("None") }
    var titleError by remember { mutableStateOf(false) }

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
                    text = "Create New Task",
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
                LifeOSTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        if (it.isNotBlank()) titleError = false
                    },
                    label = "Task Title *",
                    placeholder = "e.g. Complete Algebra Lab Report",
                    isError = titleError,
                    errorMessage = "Title cannot be empty"
                )
            }

            item {
                LifeOSTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Description & Notes",
                    placeholder = "Add key details, links, or notes...",
                    singleLine = false,
                    maxLines = 4
                )
            }

            item {
                Text(text = "Priority Level", style = LifeOSTypography.labelMedium, color = LifeOSTextSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    TaskPriority.values().forEach { priority ->
                        LifeOSFilterChip(
                            selected = selectedPriority == priority,
                            onClick = { selectedPriority = priority },
                            label = priority.name
                        )
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LifeOSTextField(
                        value = dueDate,
                        onValueChange = { dueDate = it },
                        label = "Due Date",
                        modifier = Modifier.weight(1f)
                    )
                    LifeOSTextField(
                        value = dueTime,
                        onValueChange = { dueTime = it },
                        label = "Due Time",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Text(text = "Life Area", style = LifeOSTypography.labelMedium, color = LifeOSTextSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    LifeArea.values().forEach { area ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(LifeOSShapes.small)
                                .background(if (selectedArea == area) LifeOSSecondarySurface else LifeOSCardBackground)
                                .clickable { selectedArea = area }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(area.color)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = area.title,
                                style = LifeOSTypography.titleSmall,
                                color = if (selectedArea == area) area.color else LifeOSTextPrimary
                            )
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                LifeOSPrimaryButton(
                    text = "Save Task",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (title.isBlank()) {
                            titleError = true
                        } else {
                            viewModel.addTask(
                                title = title,
                                description = description,
                                priority = selectedPriority,
                                dueDate = dueDate,
                                dueTime = dueTime,
                                linkedArea = selectedArea,
                                linkedGoalId = null,
                                recurringRule = recurringRule
                            )
                            onNavigateBack()
                        }
                    }
                )
            }
        }
    }
}
