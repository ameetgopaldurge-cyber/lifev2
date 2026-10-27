package com.lifeos.app.feature.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.*
import kotlinx.coroutines.flow.*

data class AnalyticsUiState(
    val completedTasks: Int = 0,
    val totalTasks: Int = 0,
    val collegeAttendanceAvg: Double = 87.5,
    val totalEarnings: Double = 405000.0,
    val activeHabitStreakMax: Int = 22,
    val workoutConsistency: Int = 94,
    val isLoading: Boolean = false
)

class AnalyticsViewModel(
    taskRepository: TaskRepository,
    collegeRepository: CollegeRepository,
    incomeRepository: IncomeRepository,
    disciplineRepository: DisciplineRepository
) : ViewModel() {

    val uiState: StateFlow<AnalyticsUiState> = combine(
        taskRepository.getTasks(),
        collegeRepository.getSubjects(),
        incomeRepository.getSummary(),
        disciplineRepository.getHabits()
    ) { tasks, subjects, income, habits ->
        val totalAttended = subjects.sumOf { it.attendedClasses }
        val totalClasses = subjects.sumOf { it.totalClasses }
        val attendance = if (totalClasses > 0) (totalAttended.toDouble() / totalClasses) * 100.0 else 0.0

        AnalyticsUiState(
            completedTasks = tasks.count { it.isCompleted },
            totalTasks = tasks.size,
            collegeAttendanceAvg = attendance,
            totalEarnings = income.totalEarnings,
            activeHabitStreakMax = habits.maxOfOrNull { it.streakDays } ?: 0,
            workoutConsistency = 94,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AnalyticsUiState(isLoading = true)
    )
}
