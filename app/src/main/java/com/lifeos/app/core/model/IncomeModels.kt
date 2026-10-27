package com.lifeos.app.core.model

import java.util.UUID

enum class TransactionType {
    INCOME, EXPENSE
}

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val category: String,
    val amount: Double,
    val type: TransactionType,
    val dateText: String,
    val clientName: String? = null
)

enum class ClientPipelineStage {
    LEAD, PROPOSAL_SENT, ACTIVE, COMPLETED
}

data class FreelanceClient(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val email: String,
    val projectTitle: String,
    val stage: ClientPipelineStage,
    val totalFee: Double,
    val paidAmount: Double,
    val followUpDate: String
)

data class IncomeSummary(
    val totalEarnings: Double = 405000.0, // Default in INR ₹
    val totalExpenses: Double = 82000.0,
    val totalInvoicesSentCount: Int = 10,
    val totalInvoicesAmount: Double = 500000.0,
    val savingsGoalTitle: String = "Save for a trip to Japan",
    val savingsCurrent: Double = 50000.0,
    val savingsTarget: Double = 200000.0
)
