package com.lifeos.app.feature.college

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun AddAssignmentScreen(
    viewModel: CollegeViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf("") }
    var selectedSubject by remember { mutableStateOf(state.subjects.firstOrNull()) }
    var dueDate by remember { mutableStateOf("May 28") }
    var dueTime by remember { mutableStateOf("23:59") }
    var categoryBadge by remember { mutableStateOf("Homework") }
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
                    text = "Add Academic Assignment",
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
                    label = "Assignment Title *",
                    placeholder = "e.g. Lab 3: Matrix Transformations",
                    isError = titleError,
                    errorMessage = "Title cannot be empty"
                )
            }

            item {
                Text(text = "Subject", style = LifeOSTypography.labelMedium, color = LifeOSTextSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    state.subjects.take(3).forEach { subject ->
                        LifeOSFilterChip(
                            selected = selectedSubject?.id == subject.id,
                            onClick = { selectedSubject = subject },
                            label = subject.name
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
                Text(text = "Category Badge", style = LifeOSTypography.labelMedium, color = LifeOSTextSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Homework", "Lab Reports", "Presentations", "Reading Checks").forEach { badge ->
                        LifeOSFilterChip(
                            selected = categoryBadge == badge,
                            onClick = { categoryBadge = badge },
                            label = badge
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                LifeOSPrimaryButton(
                    text = "Save Assignment",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (title.isBlank()) {
                            titleError = true
                        } else {
                            viewModel.addAssignment(
                                subjectId = selectedSubject?.id ?: "sub_gen",
                                subjectName = selectedSubject?.name ?: "General",
                                title = title,
                                dueDateText = dueDate,
                                dueTimeText = dueTime,
                                categoryBadge = categoryBadge
                            )
                            onNavigateBack()
                        }
                    }
                )
            }
        }
    }
}
