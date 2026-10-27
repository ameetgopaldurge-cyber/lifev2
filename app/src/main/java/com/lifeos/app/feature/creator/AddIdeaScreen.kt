package com.lifeos.app.feature.creator

import androidx.compose.foundation.layout.*
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
fun AddIdeaScreen(
    viewModel: CreatorViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var platform by remember { mutableStateOf("Instagram Reels") }
    var tagText by remember { mutableStateOf("Productivity, Tech") }

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
                    text = "New Content Idea",
                    style = LifeOSTypography.titleLarge,
                    color = LifeOSTextPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LifeOSTextField(
                value = title,
                onValueChange = { title = it },
                label = "Video Concept / Hook",
                placeholder = "e.g. 3 Apps every CS undergrad needs"
            )

            LifeOSTextField(
                value = platform,
                onValueChange = { platform = it },
                label = "Target Platform",
                placeholder = "Instagram Reels / YouTube Shorts"
            )

            LifeOSTextField(
                value = tagText,
                onValueChange = { tagText = it },
                label = "Tags (comma separated)"
            )

            Spacer(modifier = Modifier.weight(1f))

            LifeOSPrimaryButton(
                text = "Save Idea",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (title.isNotBlank()) {
                        val tags = tagText.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        viewModel.addIdea(title, platform, tags)
                        onNavigateBack()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
