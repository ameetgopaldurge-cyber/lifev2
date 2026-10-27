package com.lifeos.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifeos.app.core.data.repository.GoalRepository
import com.lifeos.app.core.data.repository.TaskRepository
import com.lifeos.app.core.data.repository.UserRepository
import com.lifeos.app.core.model.Goal
import com.lifeos.app.core.model.Task
import com.lifeos.app.core.model.UserProfile
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class HomeUiState(
    val userProfile: UserProfile = UserProfile(),
    val tasks: List<Task> = emptyList(),
    val goals: List<Goal> = emptyList(),
    val completedTasksCount: Int = 0,
    val totalTasksCount: Int = 0,
    val completionPercentage: Float = 0.78f,
    val aiRecommendation: String = "⚡ Review CS101 Lab 2 before 5:00 PM to maintain your 91.7% grade streak.",
    val isLoading: Boolean = false
)

class HomeViewModel(
    private val taskRepository: TaskRepository,
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        userRepository.getUserProfile(),
        taskRepository.getTasks(),
        goalRepository.getGoals()
    ) { profile, tasks, goals ->
        val total = tasks.size
        val completed = tasks.count { it.isCompleted }
        val percentage = if (total > 0) completed.toFloat() / total else 0.78f

        HomeUiState(
            userProfile = profile,
            tasks = tasks,
            goals = goals,
            completedTasksCount = completed,
            totalTasksCount = total,
            completionPercentage = percentage,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState(isLoading = true)
    )

    fun toggleTask(taskId: String) {
        viewModelScope.launch {
            taskRepository.toggleTaskCompletion(taskId)
        }
    }
}
