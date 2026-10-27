package com.lifeos.app.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import com.lifeos.app.core.model.LifeArea

@Composable
fun HomeDashboardScreen(
    viewModel: HomeViewModel,
    onNavigateToLifeAreas: () -> Unit,
    onNavigateToLifeArea: (String) -> Unit,
    onNavigateToAddTask: () -> Unit,
    onNavigateToTaskDetail: (String) -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAnalytics: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = LifeOSBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddTask,
                containerColor = LifeOSPrimaryAccent,
                contentColor = LifeOSBackground,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Task")
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
            item {
                Spacer(modifier = Modifier.height(8.dp))
                // 1. Header with greeting and profile shortcut (matches reference mockup)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Good morning,",
                            style = LifeOSTypography.bodyMedium,
                            color = LifeOSTextSecondary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${state.userProfile.fullName.substringBefore(" ")} 👋",
                                style = LifeOSTypography.displayMedium,
                                color = LifeOSTextPrimary
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LifeOSIconButton(
                            icon = Icons.Outlined.Notifications,
                            onClick = { },
                            contentDescription = "Notifications"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(LifeOSSecondarySurface)
                                .clickable { onNavigateToProfile() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Profile",
                                tint = LifeOSPrimaryAccent
                            )
                        }
                    }
                }
            }

            // 2. Large Circular Progress Ring (Matches reference mockup 78% Project Completion)
            item {
                LifeOSCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = LifeOSCardBackground
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LifeOSCircularProgressRing(
                            percentage = state.completionPercentage,
                            ringSize = 160.dp,
                            strokeWidth = 14.dp,
                            label = "Daily Completion",
                            accentColor = LifeOSPrimaryAccent,
                            secondaryColor = LifeOSPrimaryAccentDark
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "${state.completedTasksCount} of ${state.totalTasksCount} tasks completed today",
                            style = LifeOSTypography.bodyMedium,
                            color = LifeOSTextSecondary
                        )
                    }
                }
            }

            // 3. 3 Quick Metrics Cards (Active Projects, Consistency, Pending Reviews)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    LifeOSMetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Active Projects",
                        value = "12",
                        icon = Icons.Outlined.Folder,
                        iconTint = LifeOSPrimaryAccent
                    )
                    LifeOSMetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Consistency",
                        value = "94%",
                        icon = Icons.Outlined.Star,
                        iconTint = LifeOSSuccess,
                        badgeColor = LifeOSSuccess
                    )
                    LifeOSMetricCard(
                        modifier = Modifier.weight(1f),
                        title = "Pending Reviews",
                        value = "4",
                        icon = Icons.Outlined.WarningAmber,
                        iconTint = LifeOSWarning
                    )
                }
            }

            // 4. AI Recommendation Card
            item {
                LifeOSCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = LifeOSSecondarySurface,
                    borderColor = LifeOSPrimaryAccent.copy(alpha = 0.3f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "AI Next Action",
                            style = LifeOSTypography.labelSmall,
                            color = LifeOSPrimaryAccent
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = state.aiRecommendation,
                        style = LifeOSTypography.bodyMedium,
                        color = LifeOSTextPrimary
                    )
                }
            }

            // 5. Today's Priorities / My Tasks (matches mockup list)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Tasks",
                        style = LifeOSTypography.titleLarge,
                        color = LifeOSTextPrimary
                    )
                    Text(
                        text = "See All",
                        style = LifeOSTypography.labelMedium,
                        color = LifeOSPrimaryAccent,
                        modifier = Modifier.clickable { onNavigateToLifeAreas() }
                    )
                }
            }

            items(state.tasks.take(4)) { task ->
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
                            Text(
                                text = "${task.dueDate} • ${task.dueTime}",
                                style = LifeOSTypography.bodySmall,
                                color = LifeOSTextSecondary
                            )
                        }
                        LifeOSPriorityBadge(priority = task.priority.name)
                    }
                }
            }

            // 6. Life Areas Grid Header & Shortcuts
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Life Areas",
                        style = LifeOSTypography.titleLarge,
                        color = LifeOSTextPrimary
                    )
                    Text(
                        text = "All 6 Areas",
                        style = LifeOSTypography.labelMedium,
                        color = LifeOSPrimaryAccent,
                        modifier = Modifier.clickable { onNavigateToLifeAreas() }
                    )
                }
            }

            items(LifeArea.values()) { area ->
                LifeOSLifeAreaCard(
                    name = area.title,
                    description = area.subtitle,
                    icon = when (area) {
                        LifeArea.COLLEGE -> Icons.Outlined.School
                        LifeArea.FITNESS -> Icons.Outlined.FitnessCenter
                        LifeArea.CREATOR -> Icons.Outlined.VideoLibrary
                        LifeArea.SKILLS -> Icons.Outlined.Code
                        LifeArea.INCOME -> Icons.Outlined.CurrencyRupee
                        LifeArea.DISCIPLINE -> Icons.Outlined.SelfImprovement
                    },
                    accentColor = area.color,
                    activeItemsCount = when (area) {
                        LifeArea.COLLEGE -> 5
                        LifeArea.FITNESS -> 4
                        LifeArea.CREATOR -> 4
                        LifeArea.SKILLS -> 6
                        LifeArea.INCOME -> 4
                        LifeArea.DISCIPLINE -> 4
                    },
                    completionPercentage = when (area) {
                        LifeArea.COLLEGE -> 88
                        LifeArea.FITNESS -> 65
                        LifeArea.CREATOR -> 75
                        LifeArea.SKILLS -> 50
                        LifeArea.INCOME -> 82
                        LifeArea.DISCIPLINE -> 70
                    },
                    onClick = { onNavigateToLifeArea(area.route) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
