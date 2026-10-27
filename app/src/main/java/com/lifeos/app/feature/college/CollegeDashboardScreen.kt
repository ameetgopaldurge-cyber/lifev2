package com.lifeos.app.feature.college

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*
import com.lifeos.app.core.model.AssignmentStatus

@Composable
fun CollegeDashboardScreen(
    viewModel: CollegeViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddAssignment: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

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
                            text = "College & Studies",
                            style = LifeOSTypography.titleLarge,
                            color = LifeOSTextPrimary
                        )
                        Text(
                            text = "Academics, Attendance & Exams",
                            style = LifeOSTypography.bodySmall,
                            color = LifeOSTextSecondary
                        )
                    }
                }
                LifeOSIconButton(
                    icon = Icons.Default.Add,
                    onClick = onNavigateToAddAssignment
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Overview Attendance Card
            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Overall Attendance", style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = String.format("%.1f%%", state.averageAttendance),
                                style = LifeOSTypography.displayMedium,
                                color = if (state.averageAttendance >= 75.0) LifeOSSuccess else LifeOSWarning
                            )
                        }
                        LifeOSTagBadge(
                            text = if (state.averageAttendance >= 75.0) "Good Standing" else "Attendance Risk",
                            color = if (state.averageAttendance >= 75.0) LifeOSSuccess else LifeOSWarning
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    LifeOSProgressBar(
                        progress = (state.averageAttendance / 100.0).toFloat(),
                        color = if (state.averageAttendance >= 75.0) LifeOSSuccess else LifeOSWarning,
                        modifier = Modifier.fillMaxWidth().height(6.dp)
                    )
                }
            }

            // Grades & Subject Grid (matches mockup 2-column grid in Image 3)
            item {
                Text(
                    text = "Subjects & Attendance",
                    style = LifeOSTypography.titleLarge,
                    color = LifeOSTextPrimary
                )
            }

            items(state.subjects) { subject ->
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(LifeOSSecondarySurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = subject.badgeLetter,
                                style = LifeOSTypography.labelLarge,
                                color = LifeOSPrimaryAccent
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = subject.name,
                                style = LifeOSTypography.titleSmall,
                                color = LifeOSTextPrimary
                            )
                            Text(
                                text = "${subject.attendedClasses}/${subject.totalClasses} classes • ${subject.professor}",
                                style = LifeOSTypography.bodySmall,
                                color = LifeOSTextSecondary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = String.format("%.1f%%", subject.attendancePercentage),
                                style = LifeOSTypography.labelLarge,
                                color = if (subject.isBelowThreshold) LifeOSError else LifeOSSuccess
                            )
                            Row(modifier = Modifier.padding(top = 4.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(LifeOSSuccess.copy(alpha = 0.2f))
                                        .clickable { viewModel.recordAttendance(subject.id, true) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = "Attended", tint = LifeOSSuccess, modifier = Modifier.size(14.dp))
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(LifeOSError.copy(alpha = 0.2f))
                                        .clickable { viewModel.recordAttendance(subject.id, false) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Missed", tint = LifeOSError, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Past Due Assignments (Image 3)
            val pastDue = state.assignments.filter { it.status == AssignmentStatus.PAST_DUE }
            if (pastDue.isNotEmpty()) {
                item {
                    Text(
                        text = "Past Due",
                        style = LifeOSTypography.titleMedium,
                        color = LifeOSError
                    )
                }
                items(pastDue) { assignment ->
                    LifeOSCard(
                        modifier = Modifier.fillMaxWidth(),
                        borderColor = LifeOSError.copy(alpha = 0.3f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = assignment.title,
                                    style = LifeOSTypography.titleSmall,
                                    color = LifeOSTextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    LifeOSTagBadge(text = assignment.categoryBadge, color = LifeOSCollegeColor)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "${assignment.dueDateText} • ${assignment.dueTimeText}",
                                        style = LifeOSTypography.bodySmall,
                                        color = LifeOSError
                                    )
                                }
                            }
                            Checkbox(
                                checked = assignment.isSubmitted,
                                onCheckedChange = { viewModel.toggleAssignmentSubmission(assignment.id) },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = LifeOSPrimaryAccent,
                                    uncheckedColor = LifeOSBorder
                                )
                            )
                        }
                    }
                }
            }

            // Today / Upcoming Assignments (Image 3)
            val upcoming = state.assignments.filter { it.status != AssignmentStatus.PAST_DUE }
            item {
                Text(
                    text = "Upcoming Deadlines",
                    style = LifeOSTypography.titleMedium,
                    color = LifeOSTextPrimary
                )
            }
            items(upcoming) { assignment ->
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = assignment.title,
                                style = LifeOSTypography.titleSmall,
                                color = LifeOSTextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                LifeOSTagBadge(text = assignment.categoryBadge, color = LifeOSCollegeColor)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${assignment.dueDateText} • ${assignment.dueTimeText}",
                                    style = LifeOSTypography.bodySmall,
                                    color = LifeOSTextSecondary
                                )
                            }
                        }
                        Checkbox(
                            checked = assignment.isSubmitted,
                            onCheckedChange = { viewModel.toggleAssignmentSubmission(assignment.id) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = LifeOSPrimaryAccent,
                                uncheckedColor = LifeOSBorder
                            )
                        )
                    }
                }
            }

            // Exams Countdown
            if (state.exams.isNotEmpty()) {
                item {
                    Text(
                        text = "Exams Countdown",
                        style = LifeOSTypography.titleMedium,
                        color = LifeOSTextPrimary
                    )
                }
                items(state.exams) { exam ->
                    LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = exam.examTitle, style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                                Text(text = "${exam.subjectName} • ${exam.examDate}", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                            }
                            LifeOSTagBadge(
                                text = "${exam.daysRemaining} days left",
                                color = if (exam.daysRemaining <= 7) LifeOSWarning else LifeOSPrimaryAccent
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
