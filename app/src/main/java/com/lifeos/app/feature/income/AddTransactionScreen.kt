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
import com.lifeos.app.core.model.TransactionType

@Composable
fun AddTransactionScreen(
    viewModel: IncomeViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Freelance") }
    var amountText by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(TransactionType.INCOME) }

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
                    text = "Record Transaction",
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
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                LifeOSFilterChip(
                    selected = selectedType == TransactionType.INCOME,
                    onClick = { selectedType = TransactionType.INCOME },
                    label = "Income (+)"
                )
                LifeOSFilterChip(
                    selected = selectedType == TransactionType.EXPENSE,
                    onClick = { selectedType = TransactionType.EXPENSE },
                    label = "Expense (-)"
                )
            }

            LifeOSTextField(
                value = title,
                onValueChange = { title = it },
                label = "Description / Entity",
                placeholder = "e.g. Website Milestone 1"
            )

            LifeOSTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = "Amount",
                placeholder = "e.g. 50000"
            )

            LifeOSTextField(
                value = category,
                onValueChange = { category = it },
                label = "Category",
                placeholder = "e.g. Freelance, Software, Hardware"
            )

            Spacer(modifier = Modifier.weight(1f))

            LifeOSPrimaryButton(
                text = "Save Transaction",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val amount = amountText.toDoubleOrNull() ?: 0.0
                    if (title.isNotBlank() && amount > 0) {
                        viewModel.addTransaction(title, category, amount, selectedType)
                        onNavigateBack()
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
