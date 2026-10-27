package com.lifeos.app.feature.income

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.IncomeRepository
import com.lifeos.app.core.data.repository.UserRepository
import com.lifeos.app.core.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class IncomeUiState(
    val summary: IncomeSummary = IncomeSummary(),
    val transactions: List<Transaction> = emptyList(),
    val clients: List<FreelanceClient> = emptyList(),
    val currencySymbol: String = "₹",
    val isLoading: Boolean = false
)

class IncomeViewModel(
    private val incomeRepository: IncomeRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val uiState: StateFlow<IncomeUiState> = combine(
        incomeRepository.getSummary(),
        incomeRepository.getTransactions(),
        incomeRepository.getClients(),
        userRepository.getUserProfile()
    ) { summary, txs, clients, profile ->
        IncomeUiState(
            summary = summary,
            transactions = txs,
            clients = clients,
            currencySymbol = profile.currencySymbol,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = IncomeUiState(isLoading = true)
    )

    fun addTransaction(title: String, category: String, amount: Double, type: TransactionType) {
        viewModelScope.launch {
            val tx = Transaction(
                title = title,
                category = category,
                amount = amount,
                type = type,
                dateText = "Today"
            )
            incomeRepository.addTransaction(tx)
        }
    }

    fun addClient(name: String, email: String, project: String, fee: Double) {
        viewModelScope.launch {
            val client = FreelanceClient(
                name = name,
                email = email,
                projectTitle = project,
                stage = ClientPipelineStage.ACTIVE,
                totalFee = fee,
                paidAmount = 0.0,
                followUpDate = "Next Week"
            )
            incomeRepository.addClient(client)
        }
    }
}
