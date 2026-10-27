package com.lifeos.app.feature.lifeareas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*
import com.lifeos.app.core.model.LifeArea

@Composable
fun LifeAreasHubScreen(
    onNavigateBack: () -> Unit,
    onNavigateToArea: (String) -> Unit
) {
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
                        text = "Life Areas",
                        style = LifeOSTypography.titleLarge,
                        color = LifeOSTextPrimary
                    )
                    Text(
                        text = "Six interconnected domains of your life",
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Select any life area to access its dedicated dashboard, custom trackers, and workflows.",
                    style = LifeOSTypography.bodyMedium,
                    color = LifeOSTextSecondary
                )
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
                    onClick = { onNavigateToArea(area.route) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
