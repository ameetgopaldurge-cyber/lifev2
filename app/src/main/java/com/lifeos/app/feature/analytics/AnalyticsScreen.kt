package com.lifeos.app.feature.analytics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(containerColor = LifeOSBackground) { paddingValues ->
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
                    text = "Progress & Analytics",
                    style = LifeOSTypography.displayMedium,
                    color = LifeOSTextPrimary
                )
                Text(
                    text = "Data-driven insight into your life performance",
                    style = LifeOSTypography.bodyMedium,
                    color = LifeOSTextSecondary
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    LifeOSCard(modifier = Modifier.weight(1f)) {
                        Text(text = "Task Completion", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${state.completedTasks}/${state.totalTasks}", style = LifeOSTypography.titleLarge, color = LifeOSPrimaryAccent)
                    }
                    LifeOSCard(modifier = Modifier.weight(1f)) {
                        Text(text = "Attendance", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = String.format("%.1f%%", state.collegeAttendanceAvg), style = LifeOSTypography.titleLarge, color = LifeOSSuccess)
                    }
                    LifeOSCard(modifier = Modifier.weight(1f)) {
                        Text(text = "Best Streak", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${state.activeHabitStreakMax}d", style = LifeOSTypography.titleLarge, color = LifeOSWarning)
                    }
                }
            }

            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Weekly Deep Work & Activity Hours", style = LifeOSTypography.titleMedium, color = LifeOSTextPrimary)
                    Spacer(modifier = Modifier.height(14.dp))
                    LifeOSBarChart(
                        items = listOf(
                            BarChartItem("Mon", 6.5f),
                            BarChartItem("Tue", 7.2f),
                            BarChartItem("Wed", 8.5f, isHighlighted = true),
                            BarChartItem("Thu", 7.8f),
                            BarChartItem("Fri", 9.0f, isHighlighted = true),
                            BarChartItem("Sat", 4.5f),
                            BarChartItem("Sun", 5.0f)
                        )
                    )
                }
            }

            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Life Area Consistency Scorecard", style = LifeOSTypography.titleMedium, color = LifeOSTextPrimary)
                    Spacer(modifier = Modifier.height(12.dp))
                    listOf(
                        "College & Academics" to 0.88f,
                        "Health & Fitness" to 0.65f,
                        "Creator Studio" to 0.75f,
                        "Skills & Projects" to 0.50f,
                        "Income & Freelance" to 0.82f,
                        "Personal Discipline" to 0.70f
                    ).forEach { (area, score) ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = area, style = LifeOSTypography.bodyMedium, color = LifeOSTextPrimary)
                            Text(text = "${(score * 100).toInt()}%", style = LifeOSTypography.labelMedium, color = LifeOSPrimaryAccent)
                        }
                        LifeOSProgressBar(progress = score, modifier = Modifier.fillMaxWidth().height(4.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
