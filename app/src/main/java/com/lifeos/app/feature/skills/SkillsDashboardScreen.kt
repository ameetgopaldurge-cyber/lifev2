package com.lifeos.app.feature.skills

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
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun SkillsDashboardScreen(
    viewModel: SkillsViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddProject: () -> Unit
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
                            text = "Skills & Projects",
                            style = LifeOSTypography.titleLarge,
                            color = LifeOSTextPrimary
                        )
                        Text(
                            text = "Roadmaps, Modules & Portfolio",
                            style = LifeOSTypography.bodySmall,
                            color = LifeOSTextSecondary
                        )
                    }
                }
                LifeOSIconButton(
                    icon = Icons.Default.Add,
                    onClick = onNavigateToAddProject
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
            // Search Bar (matches Image 6: "Search your saved courses")
            item {
                LifeOSTextField(
                    value = state.searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = "Search your saved courses",
                    leadingIcon = Icons.Outlined.Search
                )
            }

            // My Courses 2-Column Grid (matches Image 6)
            item {
                Text(
                    text = "My Courses",
                    style = LifeOSTypography.titleLarge,
                    color = LifeOSTextPrimary
                )
            }

            items(state.courses.chunked(2)) { rowCourses ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowCourses.forEach { course ->
                        LifeOSCard(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = course.title,
                                style = LifeOSTypography.titleMedium,
                                color = LifeOSTextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (course.isCompleted) "Completed" else "${course.completionPercentage}% completed",
                                style = LifeOSTypography.bodySmall,
                                color = if (course.isCompleted) LifeOSSuccess else LifeOSWarning
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${course.completedModules}/${course.totalModules} modules",
                                style = LifeOSTypography.bodySmall,
                                color = LifeOSTextTertiary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LifeOSPrimaryButton(
                                text = if (course.isCompleted) "Review" else "Resume",
                                onClick = { viewModel.advanceCourse(course.id) },
                                modifier = Modifier.fillMaxWidth().height(36.dp)
                            )
                        }
                    }
                    if (rowCourses.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            // Practical Projects Tracker & GitHub links
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Coding Projects & Portfolio",
                    style = LifeOSTypography.titleLarge,
                    color = LifeOSTextPrimary
                )
            }

            items(state.projects) { project ->
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = project.title, style = LifeOSTypography.titleMedium, color = LifeOSTextPrimary)
                        LifeOSStatusBadge(status = project.status.name.replace("_", " "))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = project.description, style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        project.techStack.forEach { tech ->
                            LifeOSTagBadge(text = tech, color = LifeOSSkillsColor)
                        }
                    }
                    if (project.githubUrl.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "GitHub: ${project.githubUrl}",
                            style = LifeOSTypography.bodySmall,
                            color = LifeOSPrimaryAccent
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
