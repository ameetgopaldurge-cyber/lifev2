package com.lifeos.app.feature.skills

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
import com.lifeos.app.core.model.CodingProject
import com.lifeos.app.core.model.ProjectStatus

@Composable
fun AddProjectScreen(
    viewModel: SkillsViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var techStackText by remember { mutableStateOf("Kotlin, Compose") }
    var githubUrl by remember { mutableStateOf("") }

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
                    text = "Add Coding Project",
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
                label = "Project Name",
                placeholder = "e.g. LifeOS Android Client"
            )

            LifeOSTextField(
                value = description,
                onValueChange = { description = it },
                label = "Project Scope",
                placeholder = "What problem does this project solve?"
            )

            LifeOSTextField(
                value = techStackText,
                onValueChange = { techStackText = it },
                label = "Tech Stack (comma separated)"
            )

            LifeOSTextField(
                value = githubUrl,
                onValueChange = { githubUrl = it },
                label = "GitHub Repository Link",
                placeholder = "https://github.com/username/repo"
            )

            Spacer(modifier = Modifier.weight(1f))

            LifeOSPrimaryButton(
                text = "Save Project",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (title.isNotBlank()) {
                        val stack = techStackText.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        viewModel.addProject(
                            CodingProject(
                                title = title,
                                description = description,
                                techStack = stack,
                                githubUrl = githubUrl,
                                status = ProjectStatus.IN_PROGRESS
                            )
                        )
                        onNavigateBack()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
