package com.lifeos.app.feature.creator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
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
fun CreatorStudioScreen(
    viewModel: CreatorViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddIdea: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val tabs = listOf("Insights", "Projects", "Ideas")

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
                            text = "Creator Studio",
                            style = LifeOSTypography.titleLarge,
                            color = LifeOSTextPrimary
                        )
                        Text(
                            text = "Short-form video & content pipeline",
                            style = LifeOSTypography.bodySmall,
                            color = LifeOSTextSecondary
                        )
                    }
                }
                LifeOSIconButton(
                    icon = Icons.Default.Add,
                    onClick = onNavigateToAddIdea
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
            // Tab Selector
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    tabs.forEachIndexed { index, tab ->
                        LifeOSFilterChip(
                            selected = state.selectedTab == index,
                            onClick = { viewModel.selectTab(index) },
                            label = tab
                        )
                    }
                }
            }

            when (state.selectedTab) {
                0 -> {
                    // Insights Tab (matches Image 4)
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            LifeOSCard(modifier = Modifier.weight(1f)) {
                                Text(text = "Reels Views", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "472,206", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
                            }
                            LifeOSCard(modifier = Modifier.weight(1f)) {
                                Text(text = "Reach", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "333,127", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
                            }
                            LifeOSCard(modifier = Modifier.weight(1f)) {
                                Text(text = "Net Follows", style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "+1,520", style = LifeOSTypography.titleLarge, color = LifeOSSuccess)
                            }
                        }
                    }
                    item {
                        LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Weekly Views Trend", style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                            Spacer(modifier = Modifier.height(12.dp))
                            LifeOSBarChart(
                                items = listOf(
                                    BarChartItem("Mon", 45f),
                                    BarChartItem("Tue", 68f),
                                    BarChartItem("Wed", 92f),
                                    BarChartItem("Thu", 85f),
                                    BarChartItem("Fri", 110f, isHighlighted = true),
                                    BarChartItem("Sat", 72f),
                                    BarChartItem("Sun", 64f)
                                )
                            )
                        }
                    }
                }
                1 -> {
                    // Projects Tab (matches Image 4)
                    items(state.projects) { project ->
                        LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = project.title, style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "${project.platform} • ${project.durationText} • ${project.fileSizeText}",
                                        style = LifeOSTypography.bodySmall,
                                        color = LifeOSTextSecondary
                                    )
                                }
                                LifeOSStatusBadge(status = project.stage.name)
                            }
                            if (project.script.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = project.script,
                                    style = LifeOSTypography.bodySmall,
                                    color = LifeOSTextSecondary,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
                2 -> {
                    // Ideas Inbox Tab
                    items(state.ideas) { idea ->
                        LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = idea.title, style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        idea.tags.forEach { tag ->
                                            LifeOSTagBadge(text = tag, color = LifeOSCreatorColor)
                                        }
                                    }
                                }
                                LifeOSTagBadge(text = idea.platform, color = LifeOSPrimaryAccent)
                            }
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
