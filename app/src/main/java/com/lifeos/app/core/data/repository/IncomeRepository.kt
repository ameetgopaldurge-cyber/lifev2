package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface IncomeRepository {
    fun getSummary(): Flow<IncomeSummary>
    fun getTransactions(): Flow<List<Transaction>>
    fun getClients(): Flow<List<FreelanceClient>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun addClient(client: FreelanceClient)
}

class InMemoryIncomeRepository : IncomeRepository {
    private val _summary = MutableStateFlow(IncomeSummary())

    private val _transactions = MutableStateFlow<List<Transaction>>(
        listOf(
            Transaction(id = "tx_01", title = "Client UX Audit Retainer", category = "Freelance", amount = 75000.0, type = TransactionType.INCOME, dateText = "May 25", clientName = "Samantha Johnson"),
            Transaction(id = "tx_02", title = "Figma & GitHub Copilot Subscriptions", category = "Software", amount = 3500.0, type = TransactionType.EXPENSE, dateText = "May 22"),
            Transaction(id = "tx_03", title = "Digital Asset & Icon Pack Royalties", category = "Digital Product", amount = 12000.0, type = TransactionType.INCOME, dateText = "May 20"),
            Transaction(id = "tx_04", title = "Cloud Server Hosting", category = "Infrastructure", amount = 2800.0, type = TransactionType.EXPENSE, dateText = "May 18")
        )
    )

    private val _clients = MutableStateFlow<List<FreelanceClient>>(
        listOf(
            FreelanceClient(id = "cl_01", name = "Samantha Johnson", email = "samantha@lumina.io", projectTitle = "Design System Architecture", stage = ClientPipelineStage.ACTIVE, totalFee = 150000.0, paidAmount = 75000.0, followUpDate = "May 30"),
            FreelanceClient(id = "cl_02", name = "HyperScale Tech", email = "founders@hyperscale.ai", projectTitle = "Android Mobile MVP", stage = ClientPipelineStage.PROPOSAL_SENT, totalFee = 220000.0, paidAmount = 0.0, followUpDate = "June 2")
        )
    )

    override fun getSummary(): Flow<IncomeSummary> = _summary
    override fun getTransactions(): Flow<List<Transaction>> = _transactions
    override fun getClients(): Flow<List<FreelanceClient>> = _clients

    override suspend fun addTransaction(transaction: Transaction) {
        _transactions.value = listOf(transaction) + _transactions.value
        val cur = _summary.value
        if (transaction.type == TransactionType.INCOME) {
            _summary.value = cur.copy(totalEarnings = cur.totalEarnings + transaction.amount)
        } else {
            _summary.value = cur.copy(totalExpenses = cur.totalExpenses + transaction.amount)
        }
    }

    override suspend fun addClient(client: FreelanceClient) {
        _clients.value = listOf(client) + _clients.value
    }
}
