package com.lifeos.app.core.data.repository

import com.lifeos.app.core.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface DisciplineRepository {
    fun getHabits(): Flow<List<Habit>>
    fun getRoutines(): Flow<List<RoutineItem>>
    fun getFocusSessions(): Flow<List<FocusSessionRecord>>
    fun getReflections(): Flow<List<ReflectionEntry>>
    suspend fun toggleHabit(id: String)
    suspend fun toggleRoutine(id: String)
    suspend fun recordFocusSession(session: FocusSessionRecord)
    suspend fun addReflection(entry: ReflectionEntry)
}

class InMemoryDisciplineRepository : DisciplineRepository {
    private val _habits = MutableStateFlow<List<Habit>>(
        listOf(
            Habit(id = "h_01", name = "Morning Meditation (15m)", streakDays = 14, isCompletedToday = true, bestStreak = 21),
            Habit(id = "h_02", name = "Deep Work Reading (30m)", streakDays = 22, isCompletedToday = false, bestStreak = 30),
            Habit(id = "h_03", name = "No Refined Sugar", streakDays = 7, isCompletedToday = true, bestStreak = 14),
            Habit(id = "h_04", name = "Evening Code Review & Commit", streakDays = 12, isCompletedToday = false, bestStreak = 18)
        )
    )

    private val _routines = MutableStateFlow<List<RoutineItem>>(
        listOf(
            RoutineItem(id = "r_01", title = "500ml water immediately on waking", isMorning = true, isCompleted = true),
            RoutineItem(id = "r_02", title = "10m mobility stretching & posture check", isMorning = true, isCompleted = true),
            RoutineItem(id = "r_03", title = "Identify #1 single priority for the day", isMorning = true, isCompleted = true),
            RoutineItem(id = "r_04", title = "Clear browser tabs & review daily accomplishments", isMorning = false, isCompleted = false),
            RoutineItem(id = "r_05", title = "Phone placed on charger outside bedroom", isMorning = false, isCompleted = false)
        )
    )

    private val _focusSessions = MutableStateFlow<List<FocusSessionRecord>>(
        listOf(
            FocusSessionRecord(taskTitle = "Compose Layout Polish", durationMinutes = 50, dateText = "Today", distractionCount = 1),
            FocusSessionRecord(taskTitle = "Algorithm Problem Solving", durationMinutes = 45, dateText = "Yesterday", distractionCount = 0)
        )
    )

    private val _reflections = MutableStateFlow<List<ReflectionEntry>>(
        listOf(
            ReflectionEntry(dateText = "Yesterday", highlight = "Shipped the complete design system components on time.", frictionPoint = "Spent 25 mins scrolling before evening workout.", tomorrowPriority = "Complete all domain repositories & navigation.")
        )
    )

    override fun getHabits(): Flow<List<Habit>> = _habits
    override fun getRoutines(): Flow<List<RoutineItem>> = _routines
    override fun getFocusSessions(): Flow<List<FocusSessionRecord>> = _focusSessions
    override fun getReflections(): Flow<List<ReflectionEntry>> = _reflections

    override suspend fun toggleHabit(id: String) {
        _habits.value = _habits.value.map {
            if (it.id == id) {
                val newStatus = !it.isCompletedToday
                val newStreak = if (newStatus) it.streakDays + 1 else (it.streakDays - 1).coerceAtLeast(0)
                it.copy(isCompletedToday = newStatus, streakDays = newStreak)
            } else it
        }
    }

    override suspend fun toggleRoutine(id: String) {
        _routines.value = _routines.value.map {
            if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it
        }
    }

    override suspend fun recordFocusSession(session: FocusSessionRecord) {
        _focusSessions.value = listOf(session) + _focusSessions.value
    }

    override suspend fun addReflection(entry: ReflectionEntry) {
        _reflections.value = listOf(entry) + _reflections.value
    }
}
