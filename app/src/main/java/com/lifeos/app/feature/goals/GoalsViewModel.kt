package com.lifeos.app.feature.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.GoalRepository
import com.lifeos.app.core.model.Goal
import com.lifeos.app.core.model.LifeArea
import com.lifeos.app.core.model.Milestone
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class GoalsUiState(
    val goals: List<Goal> = emptyList(),
    val filteredGoals: List<Goal> = emptyList(),
    val selectedArea: LifeArea? = null,
    val isLoading: Boolean = false
)

class GoalsViewModel(
    private val goalRepository: GoalRepository
) : ViewModel() {

    private val _selectedArea = MutableStateFlow<LifeArea?>(null)

    val uiState: StateFlow<GoalsUiState> = combine(
        goalRepository.getGoals(),
        _selectedArea
    ) { goals, area ->
        val filtered = if (area == null) goals else goals.filter { it.area == area }
        GoalsUiState(
            goals = goals,
            filteredGoals = filtered,
            selectedArea = area,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GoalsUiState(isLoading = true)
    )

    fun selectArea(area: LifeArea?) {
        _selectedArea.value = area
    }

    fun toggleMilestone(goalId: String, milestoneId: String) {
        viewModelScope.launch {
            goalRepository.toggleMilestone(goalId, milestoneId)
        }
    }

    fun addGoal(title: String, description: String, area: LifeArea, targetDate: String, milestones: List<String>) {
        viewModelScope.launch {
            val goal = Goal(
                title = title,
                description = description,
                area = area,
                targetDate = targetDate,
                milestones = milestones.map { Milestone(title = it) }
            )
            goalRepository.addGoal(goal)
        }
    }
}
