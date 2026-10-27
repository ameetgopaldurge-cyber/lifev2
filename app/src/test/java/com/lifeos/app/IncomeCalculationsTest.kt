package com.lifeos.app

import com.lifeos.app.core.data.repository.InMemoryIncomeRepository
import com.lifeos.app.core.model.Transaction
import com.lifeos.app.core.model.TransactionType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class IncomeCalculationsTest {

    private lateinit var repository: InMemoryIncomeRepository

    @Before
    fun setup() {
        repository = InMemoryIncomeRepository()
    }

    @Test
    fun testAddIncomeTransactionIncreasesEarnings() = runBlocking {
        val initialSummary = repository.getSummary().first()
        val initialEarnings = initialSummary.totalEarnings

        val tx = Transaction(
            title = "App Store Revenue",
            category = "Mobile",
            amount = 25000.0,
            type = TransactionType.INCOME,
            dateText = "Today"
        )
        repository.addTransaction(tx)

        val updatedSummary = repository.getSummary().first()
        assertEquals(initialEarnings + 25000.0, updatedSummary.totalEarnings, 0.001)
    }

    @Test
    fun testAddExpenseTransactionIncreasesExpenses() = runBlocking {
        val initialSummary = repository.getSummary().first()
        val initialExpenses = initialSummary.totalExpenses

        val tx = Transaction(
            title = "Domain Renewals",
            category = "Web",
            amount = 1500.0,
            type = TransactionType.EXPENSE,
            dateText = "Today"
        )
        repository.addTransaction(tx)

        val updatedSummary = repository.getSummary().first()
        assertEquals(initialExpenses + 1500.0, updatedSummary.totalExpenses, 0.001)
    }
}
