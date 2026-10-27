package com.lifeos.app.feature.income

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lifeos.app.core.designsystem.component.*
import com.lifeos.app.core.designsystem.theme.*
import com.lifeos.app.core.model.TransactionType

@Composable
fun IncomeFreelanceScreen(
    viewModel: IncomeViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddTransaction: () -> Unit,
    onNavigateToAddClient: () -> Unit
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
                            text = "Income & Freelancing",
                            style = LifeOSTypography.titleLarge,
                            color = LifeOSTextPrimary
                        )
                        Text(
                            text = "Earnings, Pipeline & Expenses",
                            style = LifeOSTypography.bodySmall,
                            color = LifeOSTextSecondary
                        )
                    }
                }
                LifeOSIconButton(
                    icon = Icons.Default.Add,
                    onClick = onNavigateToAddTransaction
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
            // Earnings & Expenses Summary (matches Image 5)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LifeOSCard(modifier = Modifier.weight(1f)) {
                        Text(text = "Total Earnings", style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${state.currencySymbol}${state.summary.totalEarnings.toInt()}",
                            style = LifeOSTypography.titleLarge,
                            color = LifeOSSuccess
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LifeOSTagBadge(text = "+14% MoM", color = LifeOSSuccess)
                    }
                    LifeOSCard(modifier = Modifier.weight(1f)) {
                        Text(text = "Total Expenses", style = LifeOSTypography.bodyMedium, color = LifeOSTextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${state.currencySymbol}${state.summary.totalExpenses.toInt()}",
                            style = LifeOSTypography.titleLarge,
                            color = LifeOSError
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        LifeOSTagBadge(text = "Under Budget", color = LifeOSPrimaryAccent)
                    }
                }
            }

            // Savings / Milestone Goal Card (Image 5)
            item {
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = state.summary.savingsGoalTitle, style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                        Text(
                            text = "${state.currencySymbol}${state.summary.savingsCurrent.toInt()} of ${state.currencySymbol}${state.summary.savingsTarget.toInt()}",
                            style = LifeOSTypography.labelMedium,
                            color = LifeOSPrimaryAccent
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LifeOSProgressBar(
                        progress = (state.summary.savingsCurrent / state.summary.savingsTarget).toFloat(),
                        color = LifeOSSuccess,
                        modifier = Modifier.fillMaxWidth().height(6.dp)
                    )
                }
            }

            // Transaction History Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Recent Transactions", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
                    Text(text = "+ Log", style = LifeOSTypography.labelMedium, color = LifeOSPrimaryAccent)
                }
            }

            items(state.transactions) { tx ->
                val isIncome = tx.type == TransactionType.INCOME
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = tx.title, style = LifeOSTypography.titleSmall, color = LifeOSTextPrimary)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${tx.category} • ${tx.dateText}",
                                style = LifeOSTypography.bodySmall,
                                color = LifeOSTextSecondary
                            )
                        }
                        Text(
                            text = (if (isIncome) "+" else "-") + "${state.currencySymbol}${tx.amount.toInt()}",
                            style = LifeOSTypography.titleMedium,
                            color = if (isIncome) LifeOSSuccess else LifeOSError
                        )
                    }
                }
            }

            // Freelance Client Pipeline
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Client Pipeline", style = LifeOSTypography.titleLarge, color = LifeOSTextPrimary)
                    LifeOSSecondaryButton(
                        text = "+ Client",
                        onClick = onNavigateToAddClient,
                        modifier = Modifier.height(34.dp)
                    )
                }
            }

            items(state.clients) { client ->
                LifeOSCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = client.name, style = LifeOSTypography.titleMedium, color = LifeOSTextPrimary)
                            Text(text = client.projectTitle, style = LifeOSTypography.bodySmall, color = LifeOSTextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Fee: ${state.currencySymbol}${client.totalFee.toInt()} • Paid: ${state.currencySymbol}${client.paidAmount.toInt()}",
                                style = LifeOSTypography.bodySmall,
                                color = LifeOSPrimaryAccent
                            )
                        }
                        LifeOSStatusBadge(status = client.stage.name.replace("_", " "))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
