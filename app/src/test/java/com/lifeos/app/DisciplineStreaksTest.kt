package com.lifeos.app

import com.lifeos.app.core.data.repository.InMemoryDisciplineRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DisciplineStreaksTest {

    private lateinit var repository: InMemoryDisciplineRepository

    @Before
    fun setup() {
        repository = InMemoryDisciplineRepository()
    }

    @Test
    fun testToggleHabitIncrementsStreak() = runBlocking {
        // Find an uncompleted habit
        val habit = repository.getHabits().first().first { !it.isCompletedToday }
        val initStreak = habit.streakDays

        repository.toggleHabit(habit.id)

        val updated = repository.getHabits().first().first { it.id == habit.id }
        assertTrue(updated.isCompletedToday)
        assertEquals(initStreak + 1, updated.streakDays)
    }
}
