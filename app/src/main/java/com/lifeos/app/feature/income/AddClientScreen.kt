package com.lifeos.app.feature.income

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
fun AddClientScreen(
    viewModel: IncomeViewModel,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var project by remember { mutableStateOf("") }
    var feeText by remember { mutableStateOf("") }

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
                    text = "Add Freelance Client",
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
                value = name,
                onValueChange = { name = it },
                label = "Client / Company Name",
                placeholder = "e.g. Lumina Labs"
            )

            LifeOSTextField(
                value = email,
                onValueChange = { email = it },
                label = "Contact Email",
                placeholder = "e.g. contact@lumina.io"
            )

            LifeOSTextField(
                value = project,
                onValueChange = { project = it },
                label = "Project Title",
                placeholder = "e.g. Design System Audit"
            )

            LifeOSTextField(
                value = feeText,
                onValueChange = { feeText = it },
                label = "Total Project Fee",
                placeholder = "e.g. 150000"
            )

            Spacer(modifier = Modifier.weight(1f))

            LifeOSPrimaryButton(
                text = "Save Client",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val fee = feeText.toDoubleOrNull() ?: 0.0
                    if (name.isNotBlank()) {
                        viewModel.addClient(name, email, project, fee)
                        onNavigateBack()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
