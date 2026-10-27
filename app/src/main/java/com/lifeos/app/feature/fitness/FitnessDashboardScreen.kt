package com.lifeos.app.feature.fitness

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*

@Composable
fun FitnessDashboardScreen(
    viewModel: FitnessViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToActiveWorkout: () -> Unit
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
                Column {
                    Text(
                        text = "Health & Fitness",
                        style = LifeOSTypography.titleLarge,
                        color = LifeOSTextPrimary
                    )
                    Text(
                        text = "Workouts, Nutrition & Consistency",
                        style = LifeOSTypography.bodySmall,
                        color = LifeOSTextSecondary
                    )
                }
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
            // Large Purple Workout Card (matches Image 2: "Start your workout today!")
            item {
                LifeOSCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFF6B4EE8) // Rich violet matching mockup
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Start your\nworkout today!",
                            style = LifeOSTypography.displayMedium.copy(fontSize = 22.sp),
                            color = Color.White
                        )
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .clickable { onNavigateToActiveWorkout() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Start Workout",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "${state.dayLog.burnedCalories} cal / ${state.dayLog.targetCalories} cal target",
                        style = LifeOSTypography.labelLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Exercise summary list from Image 2
                    state.workoutPlan.exercises.take(4).forEach { ex ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "• ${ex.name}", style = LifeOSTypography.bodyMedium, color = Color.White)
                            Text(
                                text = if (ex.weightKg > 0) "${ex.weightKg.toInt()} kg" else "${ex.durationMinutes} mins",
                                style = LifeOSTypography.bodyMedium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Overall Status: Weight & Calories circular stats (Image 2)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Overall Status", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
                    Text(text = "See All", style = LifeOSTypography.labelMedium, color = LifeOSPrimaryAccent)
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LifeOSCard(modifier = Modifier.weight(1f)) {
                        Text(text = "Weight", style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(text = "${state.dayLog.currentWeightKg}", style = LifeOSTypography.displayMedium, color = LifeOSTextPrimary)
                            Text(text = " kg", style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        LifeOSTagBadge(text = "-1.2% this week", color = LifeOSSuccess)
                    }
                    LifeOSCard(modifier = Modifier.weight(1f)) {
                        Text(text = "Calories Burned", style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${state.dayLog.burnedCalories}", style = LifeOSTypography.displayMedium, color = LifeOSTextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        LifeOSTagBadge(text = "+0.8% vs avg", color = LifeOSPrimaryAccent)
                    }
                }
            }

            // Steps & Water Trackers (Image 2)
            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Daily Steps", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                        Text(
                            text = "${state.dayLog.currentSteps} / ${state.dayLog.targetSteps} steps",
                            style = LifeOSTypography.labelMedium,
                            color = LifeOSPrimaryAccent
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LifeOSProgressBar(
                        progress = state.dayLog.currentSteps.toFloat() / state.dayLog.targetSteps,
                        color = LifeOSPrimaryAccent,
                        modifier = Modifier.fillMaxWidth().height(6.dp)
                    )
                }
            }

            // Water Intake Tracker with quick-add buttons
            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Water Intake", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                        Text(
                            text = "${state.dayLog.waterIntakeMl} / ${state.dayLog.targetWaterMl} ml",
                            style = LifeOSTypography.labelMedium,
                            color = LifeOSInfo
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LifeOSProgressBar(
                        progress = state.dayLog.waterIntakeMl.toFloat() / state.dayLog.targetWaterMl,
                        color = LifeOSInfo,
                        modifier = Modifier.fillMaxWidth().height(6.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        LifeOSSecondaryButton(
                            text = "+250 ml",
                            onClick = { viewModel.addWater(250) },
                            modifier = Modifier.weight(1f)
                        )
                        LifeOSSecondaryButton(
                            text = "+500 ml",
                            onClick = { viewModel.addWater(500) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Weight History Bar Chart (Image 2 style)
            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Weekly Activity Distribution", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                    Spacer(modifier = Modifier.height(12.dp))
                    LifeOSBarChart(
                        items = listOf(
                            BarChartItem("23", 650f),
                            BarChartItem("24", 820f),
                            BarChartItem("25", 918f, isHighlighted = true),
                            BarChartItem("26", 750f),
                            BarChartItem("27", 500f),
                            BarChartItem("28", 890f)
                        )
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
